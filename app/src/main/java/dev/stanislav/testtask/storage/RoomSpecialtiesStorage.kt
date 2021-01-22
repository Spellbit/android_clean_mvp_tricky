package dev.stanislav.testtask.storage

import dev.stanislav.testtask.storage.error.NotFoundException
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RoomSpecialtiesStorage(val db: Database) : ISpecialtiesStorage {

    override fun putEmployeesSpecialties(employeesSpecialties: List<StorageEmployeeSpecialty>) = Completable.fromAction {
        db.employeeSpecialtyDao.insert(employeesSpecialties)
    }.subscribeOn(Schedulers.io())

    override fun getEmployeesSpecialtiesForEmployee(firstName: String, lastName: String) = Single.fromCallable {
        db.employeeSpecialtyDao.getSpecialtiesWithEmployeesForEmployee(firstName, lastName)
    }.subscribeOn(Schedulers.io())

    override fun putSpecialties(specialties: List<StorageSpecialty>) = Completable.fromAction {
        db.specialtyDao.insert(specialties)
    }.subscribeOn(Schedulers.io())

    override fun getSpecialties(ids: List<String>) = Single.fromCallable {
        if (ids.isEmpty()) {
            return@fromCallable db.specialtyDao.getAll()
        }

        return@fromCallable db.specialtyDao.getByIds(ids)

    }.subscribeOn(Schedulers.io())

    override fun getSpecialty(id: String) = Single.fromCallable {
        db.specialtyDao.findById(id) ?: throw NotFoundException()
    }.subscribeOn(Schedulers.io())


}