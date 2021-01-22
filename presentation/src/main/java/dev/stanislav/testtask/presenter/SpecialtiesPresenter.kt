package dev.stanislav.testtask.presenter

import com.github.terrakok.cicerone.Router
import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.functional.composeRetryWhen
import dev.stanislav.testtask.i18n.ILocalization
import dev.stanislav.testtask.interactor.GetSpecialties
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.presenter.list.ISpecialtiesListPresenter
import dev.stanislav.testtask.view.SpecialtiesView
import dev.stanislav.testtask.view.list.SpecialtyItemView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class SpecialtiesPresenter() : MvpPresenter<SpecialtiesView>() {

    inner class SpecialtiesListPresenter : ISpecialtiesListPresenter {
        override val itemClickSubject = PublishSubject.create<SpecialtyItemView>()
        val items = mutableListOf<Specialty>()
        override fun bindView(view: SpecialtyItemView) {
            val specialty = items[view.pos]
            specialty.name?.let { view.setTitle(it) }
        }

        override fun getCount() = items.size
    }

    @Inject @field:Named("ui") lateinit var uiScheduler: Scheduler
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var getSpecialties: GetSpecialties
    @Inject lateinit var localization: ILocalization

    private val compositeDisposable = CompositeDisposable()
    private val retrySubject = PublishSubject.create<Boolean>()

    val specialtiesListPresenter = SpecialtiesListPresenter()

    override fun onFirstViewAttach() {
        viewState.init()
        localization.UI { stringHolder ->
            viewState.setTitle(stringHolder.nav_specialties)
        }

        getSpecialties()

        specialtiesListPresenter.itemClickSubject.subscribe { view ->
            val specialty = specialtiesListPresenter.items[view.pos]
            specialty.id?.let { router.navigateTo(screens.specialty(it)) }

        }.addTo(compositeDisposable)
    }

    fun getSpecialties() {
        getSpecialties.exec()
            .observeOn(uiScheduler)
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .composeRetryWhen(retrySubject)
            .doOnError {
                localization.UISingle { stringHolder ->
                    viewState.showRetry(stringHolder.specialties_retry_message, stringHolder.specialties_retry_action)
                }.addTo(compositeDisposable)
            }
            .subscribe({
                specialtiesListPresenter.items.clear()
                specialtiesListPresenter.items.addAll(it)
                viewState.updateList()
            }, {
                Arbor.e(it)
            })
            .addTo(compositeDisposable)
    }

    fun retryClosed(isTimeout: Boolean) {
        if (isTimeout) {
            retrySubject.onNext(false)
        }
        viewState.hideRetry()
    }

    fun retryClick() {
        retrySubject.onNext(true)
        viewState.hideRetry()
    }

    fun backClick() {
        router.exit()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}