package dev.stanislav.testtask.di

import dagger.Component
import dev.stanislav.testtask.App
import dev.stanislav.testtask.di.module.NetworkModule
import dev.stanislav.testtask.di.module.AppModule
import dev.stanislav.testtask.di.module.DateModule
import dev.stanislav.testtask.di.module.InteractorModule
import dev.stanislav.testtask.di.module.LocalizationModule
import dev.stanislav.testtask.di.module.MapperModule
import dev.stanislav.testtask.di.module.NavigationModule
import dev.stanislav.testtask.di.module.RepoModule
import dev.stanislav.testtask.di.module.StorageModule
import dev.stanislav.testtask.presenter.EmployeePresenter
import dev.stanislav.testtask.presenter.MainPresenter
import dev.stanislav.testtask.presenter.SpecialtiesPresenter
import dev.stanislav.testtask.presenter.SpecialtyPresenter
import dev.stanislav.testtask.presenter.SplashPresenter
import dev.stanislav.testtask.ui.activity.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DateModule::class,
        MapperModule::class,
        StorageModule::class,
        RepoModule::class,
        LocalizationModule::class,
        InteractorModule::class,
        NavigationModule::class,
    ]
)
interface AppComponent {
    fun inject(app: App)

    fun inject(splashPresenter: SplashPresenter)

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    fun inject(specialtiesPresenter: SpecialtiesPresenter)
    fun inject(specialtyPresenter: SpecialtyPresenter)

    fun inject(employeePresenter: EmployeePresenter)

}