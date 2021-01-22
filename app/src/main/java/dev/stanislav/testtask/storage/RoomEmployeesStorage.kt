package dev.stanislav.testtask.storage

import dev.stanislav.testtask.storage.error.NotFoundException
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RoomEmployeesStorage(val db: Database) : IEmployeesStorage {

    override fun putEmployees(employees: List<StorageEmployee>) = Completable.fromAction {
        db.employeeDao.insert(employees)
    }.subscribeOn(Schedulers.io())

    override fun getEmployee(firstName: String, lastName: String) = Single.fromCallable {
        db.employeeDao.getByName(firstName, lastName) ?: throw NotFoundException()
    }

    override fun getEmployees(specialtyId: String) = Single.fromCallable {
        db.employeeSpecialtyDao.getSpecialtiesWithEmployees()
            .filter { it.specialty.id == specialtyId }
            .map { it.employee }
    }.subscribeOn(Schedulers.io())

}