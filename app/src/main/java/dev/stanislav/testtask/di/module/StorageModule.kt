package dev.stanislav.testtask.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.App
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.storage.Database
import dev.stanislav.testtask.storage.IEmployeesStorage
import dev.stanislav.testtask.storage.ISpecialtiesStorage
import dev.stanislav.testtask.storage.RoomEmployeesStorage
import dev.stanislav.testtask.storage.RoomSpecialtiesStorage
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty
import dev.stanislav.testtask.storage.room.mapper.IStorageMapper
import dev.stanislav.testtask.storage.room.mapper.RoomEmployeesMapper
import dev.stanislav.testtask.storage.room.mapper.RoomSpecialtiesMapper
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun specialtiesMapper(): IStorageMapper<Specialty, StorageSpecialty> = RoomSpecialtiesMapper()

    @Singleton
    @Provides
    fun employeesMapper(format: DateTimeFormatter): IStorageMapper<Employee, StorageEmployee> = RoomEmployeesMapper(format)

    @Singleton
    @Provides
    fun specialtiesStorage(db: Database): ISpecialtiesStorage = RoomSpecialtiesStorage(db)

    @Singleton
    @Provides
    fun employeesStorage(db: Database): IEmployeesStorage = RoomEmployeesStorage(db)

}