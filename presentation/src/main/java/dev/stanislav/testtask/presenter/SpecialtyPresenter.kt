package dev.stanislav.testtask.presenter

import com.github.terrakok.cicerone.Router
import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.functional.composeRetryWhen
import dev.stanislav.testtask.i18n.ILocalization
import dev.stanislav.testtask.interactor.GetEmployees
import dev.stanislav.testtask.interactor.GetSpecialty
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.presenter.list.IEmployeesListPresenter
import dev.stanislav.testtask.view.SpecialtyView
import dev.stanislav.testtask.view.list.EmployeeItemView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import moxy.MvpPresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Named

class SpecialtyPresenter(val specialtyId: String) : MvpPresenter<SpecialtyView>() {

    class EmployeesListPresenter : IEmployeesListPresenter {
        class EmployeeView(
            val firstName: String,
            val lastName: String,
            val age: String
        )

        override val itemClickSubject = PublishSubject.create<EmployeeItemView>()
        val items = mutableListOf<EmployeeView>()
        override fun bindView(view: EmployeeItemView) {
            val employee = items[view.pos]
            view.setName(employee.firstName)
            view.setLastName(employee.lastName)
            view.setAge(employee.age)
        }

        override fun getCount() = items.size
    }

    @Inject @field:Named("ui") lateinit var uiScheduler: Scheduler
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var getSpecialty: GetSpecialty
    @Inject lateinit var getEmployees: GetEmployees
    @Inject lateinit var localization: ILocalization

    private val compositeDisposable = CompositeDisposable()
    private val retrySubject = PublishSubject.create<Boolean>()

    val employeesListPresenter = EmployeesListPresenter()

    var specialty: Specialty? = null
    var employees: List<Employee>? = null

    override fun onFirstViewAttach() {
        viewState.init()

        getEmployees()

        employeesListPresenter.itemClickSubject.subscribe { view ->
            employees?.get(view.pos)?.let {
                router.navigateTo(screens.employee(it.firstName, it.lastName))
            }
        }.addTo(compositeDisposable)
    }

    fun getEmployees() {
        getSpecialty.exec(specialtyId)
            .observeOn(uiScheduler)
            .flatMap {
                specialty = it
                it.name?.let { viewState.setTitle(it) }
                getEmployees.exec(it)
            }
            .observeOn(uiScheduler)
            .composeRetryWhen(retrySubject)
            .doOnError {
                localization.UISingle { stringHolder ->
                    viewState.showRetry(stringHolder.specialty_retry_message, stringHolder.specialty_retry_action)
                }.addTo(compositeDisposable)
            }
            .subscribe({ employees ->
                localization.UISingle { stringHolder ->
                    employeesListPresenter.items.clear()
                    this.employees = employees
                    val views = employees.map { employee ->
                        val firstName = employee.firstName.toLowerCase().capitalize()
                        val lastName = employee.lastName.toLowerCase().capitalize()

                        val age = employee.birthday?.let {
                            ChronoUnit.YEARS.between(it, LocalDate.now())
                        } ?: stringHolder.specialty_employee_age_placeholder
                        EmployeesListPresenter.EmployeeView(firstName, lastName, age.toString())
                    }
                    employeesListPresenter.items.addAll(views)
                    viewState.updateList()
                }.addTo(compositeDisposable)
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