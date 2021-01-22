package dev.stanislav.testtask.presenter

import com.github.terrakok.cicerone.Router
import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.functional.composeRetryWhen
import dev.stanislav.testtask.i18n.ILocalization
import dev.stanislav.testtask.interactor.GetEmployee
import dev.stanislav.testtask.interactor.GetSpecialties
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.view.EmployeeView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import moxy.MvpPresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Named

class EmployeePresenter(val firstName: String, val lastName: String) : MvpPresenter<EmployeeView>() {

    @Inject @field:Named("ui") lateinit var uiScheduler: Scheduler
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var getSpecialties: GetSpecialties
    @Inject lateinit var getEmployee: GetEmployee
    @Inject lateinit var localization: ILocalization
    @Inject lateinit var dateFormat: DateTimeFormatter

    private val compositeDisposable = CompositeDisposable()
    private val retrySubject = PublishSubject.create<Boolean>()

    var specialties: List<Specialty>? = null
    var employee: Employee? = null

    override fun onFirstViewAttach() {
        viewState.init()
        getData()
    }

    fun getData() {
        getEmployee.exec(firstName, lastName)
            .flatMap {
                employee = it
                getSpecialties.exec(it.specialtyIds)
            }
            .observeOn(uiScheduler)
            .composeRetryWhen(retrySubject)
            .doOnError {
                localization.UISingle { stringHolder ->
                    viewState.showRetry(stringHolder.specialty_retry_message, stringHolder.specialty_retry_action)
                }.addTo(compositeDisposable)
            }
            .subscribe({
                this.specialties = it
                employee?.let { employee ->
                    localization.UISingle { stringHolder ->
                        val firstName = employee.firstName.toLowerCase().capitalize()
                        val lastName = employee.lastName.toLowerCase().capitalize()

                        viewState.setTitle("$firstName $lastName")
                        viewState.setName(firstName)
                        viewState.setLastName(lastName)

                        employee.birthday?.let {
                            val age = ChronoUnit.YEARS.between(it, LocalDate.now()).toString()
                            viewState.setAge(age)
                            viewState.setBirthDay(dateFormat.format(it))

                        } ?: let {
                            viewState.setAge(stringHolder.employee_age_placeholder)
                            viewState.setBirthDay(stringHolder.employee_birthday_placeholder)
                        }

                        specialties?.let {
                            val string = it.map { it.name }?.joinToString()
                            viewState.setSpecialty(string)
                        }
                    }
                }

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

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}