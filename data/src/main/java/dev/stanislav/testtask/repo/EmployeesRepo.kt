package dev.stanislav.testtask.repo

import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.storage.IEmployeesStorage
import dev.stanislav.testtask.storage.ISpecialtiesStorage
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import dev.stanislav.testtask.storage.room.mapper.IStorageMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class EmployeesRepo(
    private val employeesStorage: IEmployeesStorage,
    private val specialtiesStorage: ISpecialtiesStorage,
    private val storageMapper: IStorageMapper<Employee, StorageEmployee>
) : IEmployeesRepo {

    override fun getEmployeesLocal(specialty: Specialty) = specialty.id?.let { specialtyId ->
        employeesStorage.getEmployees(specialtyId).map {
            it.map {
                storageMapper.mapFrom(it)
            }
        }.subscribeOn(Schedulers.io())
    } ?: Single.just<List<Employee>>(listOf()).subscribeOn(Schedulers.io())

    override fun getEmployeeLocal(firstName: String, lastName: String) = specialtiesStorage.getEmployeesSpecialtiesForEmployee(firstName, lastName).map {
        val specialtyIds = it.map { it.specialty.id }
        storageMapper.mapFrom(it.first().employee).apply {
            this.specialtyIds = specialtyIds
        }
    }.subscribeOn(Schedulers.io())
}