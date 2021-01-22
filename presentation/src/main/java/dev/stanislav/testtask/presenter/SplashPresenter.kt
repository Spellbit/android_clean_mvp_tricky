package dev.stanislav.testtask.presenter

import com.github.terrakok.cicerone.Router
import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.i18n.ILocalization
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.view.SplashView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class SplashPresenter() : MvpPresenter<SplashView>() {

    @Inject @field:Named("ui") lateinit var uiScheduler: Scheduler
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var localization: ILocalization

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        localization.sync()
            .observeOn(uiScheduler)
            .subscribe({
                viewState.proceed()
            }, {
                Arbor.e(it)
            }).addTo(compositeDisposable)
    }

    fun backClick() {}
}