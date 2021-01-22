package dev.stanislav.testtask.storage.room.mapper

import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class RoomEmployeesMapper(val format: DateTimeFormatter) : IStorageMapper<Employee, StorageEmployee> {

    override fun mapTo(entity: Employee): StorageEmployee? {
        val birthDay = entity.birthday?.let { format.format(it) }

        return StorageEmployee(
            entity.firstName,
            entity.lastName,
            birthDay,
        )
    }

    override fun mapFrom(entity: StorageEmployee): Employee {
        val birthDay = entity.birthday?.let { LocalDate.parse(it, format) }
        return Employee(
            entity.firstName,
            entity.lastName,
            birthDay
        )
    }

}