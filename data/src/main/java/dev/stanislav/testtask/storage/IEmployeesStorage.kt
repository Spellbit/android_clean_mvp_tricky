package dev.stanislav.testtask.storage

import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import io.reactivex.Completable
import io.reactivex.Single

interface IEmployeesStorage {
    fun getEmployees(specialtyId: String): Single<List<StorageEmployee>>
    fun putEmployees(employees: List<StorageEmployee>): Completable
    fun getEmployee(firstName: String, lastName: String): Single<StorageEmployee>
}