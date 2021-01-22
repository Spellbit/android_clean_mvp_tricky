package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.interactor.GetEmployee
import dev.stanislav.testtask.interactor.GetEmployees
import dev.stanislav.testtask.interactor.GetSpecialties
import dev.stanislav.testtask.interactor.GetSpecialty
import dev.stanislav.testtask.repo.IEmployeesRepo
import dev.stanislav.testtask.repo.ISpecialtiesRepo
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @Provides
    fun getSpecialties(specialtiesRepo: ISpecialtiesRepo) = GetSpecialties(specialtiesRepo)

    @Singleton
    @Provides
    fun getSpecialty(specialtiesRepo: ISpecialtiesRepo) = GetSpecialty(specialtiesRepo)

    @Singleton
    @Provides
    fun getEmployees(specialtiesRepo: ISpecialtiesRepo, employeesRepo: IEmployeesRepo) = GetEmployees(specialtiesRepo, employeesRepo)

    @Singleton
    @Provides
    fun getEmployee(specialtiesRepo: ISpecialtiesRepo, employeesRepo: IEmployeesRepo) = GetEmployee(employeesRepo)

}