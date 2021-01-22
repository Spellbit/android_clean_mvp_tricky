package dev.stanislav.testtask.storage

import dev.stanislav.testtask.storage.room.entity.SpecialtyWithEmployee
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty
import io.reactivex.Completable
import io.reactivex.Single

interface ISpecialtiesStorage {
    fun getEmployeesSpecialtiesForEmployee(firstName: String, lastName: String): Single<List<SpecialtyWithEmployee>>
    fun putEmployeesSpecialties(employeesSpecialties: List<StorageEmployeeSpecialty>): Completable
    fun putSpecialties(specialties: List<StorageSpecialty>): Completable
    fun getSpecialties(ids: List<String> = listOf()): Single<List<StorageSpecialty>>
    fun getSpecialty(id: String): Single<StorageSpecialty>
}