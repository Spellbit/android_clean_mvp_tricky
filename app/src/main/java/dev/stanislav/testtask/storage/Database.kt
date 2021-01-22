package dev.stanislav.testtask.storage

import androidx.room.RoomDatabase
import dev.stanislav.testtask.storage.room.dao.EmployeeDao
import dev.stanislav.testtask.storage.room.dao.EmployeeSpecialtyDao
import dev.stanislav.testtask.storage.room.dao.SpecialtyDao
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty

@androidx.room.Database(entities = [StorageSpecialty::class, StorageEmployee::class, StorageEmployeeSpecialty::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val specialtyDao: SpecialtyDao
    abstract val employeeDao: EmployeeDao
    abstract val employeeSpecialtyDao: EmployeeSpecialtyDao

    companion object {
        const val DB_NAME = "database.db"
    }
}