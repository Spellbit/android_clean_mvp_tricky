package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.api.IDataSource
import dev.stanislav.testtask.entity.EEmployee
import dev.stanislav.testtask.entity.ESpecialty
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.mapper.IMapper
import dev.stanislav.testtask.network.INetworkStatus
import dev.stanislav.testtask.repo.EmployeesRepo
import dev.stanislav.testtask.repo.IEmployeesRepo
import dev.stanislav.testtask.repo.ISpecialtiesRepo
import dev.stanislav.testtask.repo.SpecialtiesRepo
import dev.stanislav.testtask.storage.IEmployeesStorage
import dev.stanislav.testtask.storage.ISpecialtiesStorage
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty
import dev.stanislav.testtask.storage.room.mapper.IStorageMapper
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun specialtiesRepo(
        networkStatus: INetworkStatus,
        api: IDataSource,
        specialtiesMapper: IMapper<ESpecialty, Specialty>,
        employeesMapper: IMapper<EEmployee, Employee>,
        specialtiesStorage: ISpecialtiesStorage,
        employeesStorage: IEmployeesStorage,
        specialtiesStorageMapper: IStorageMapper<Specialty, StorageSpecialty>,
        employeesStorageMapper: IStorageMapper<Employee, StorageEmployee>
    ): ISpecialtiesRepo = SpecialtiesRepo(
        networkStatus,
        api,
        specialtiesMapper,
        employeesMapper,
        specialtiesStorage,
        employeesStorage,
        specialtiesStorageMapper,
        employeesStorageMapper
    )

    @Singleton
    @Provides
    fun employeesRepo(
        storage: IEmployeesStorage,
        specialtiesStorage: ISpecialtiesStorage,
        storageMapper: IStorageMapper<Employee, StorageEmployee>
    ): IEmployeesRepo = EmployeesRepo(storage, specialtiesStorage, storageMapper)

}