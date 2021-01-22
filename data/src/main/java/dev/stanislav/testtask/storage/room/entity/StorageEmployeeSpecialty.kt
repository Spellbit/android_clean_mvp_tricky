package dev.stanislav.testtask.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["specialty_id", "employee_first_name", "employee_last_name"],
    foreignKeys = [
        ForeignKey(
            entity = StorageSpecialty::class,
            parentColumns = ["id"],
            childColumns = ["specialty_id"]
        ),
        ForeignKey(
            entity = StorageEmployee::class,
            parentColumns = ["first_name", "last_name"],
            childColumns = ["employee_first_name", "employee_last_name"],
        )
    ]
)
class StorageEmployeeSpecialty(
    @ColumnInfo(name = "specialty_id")
    val specialtyId: String,
    @ColumnInfo(name = "employee_first_name", index = true)
    val employeeFirstName: String,
    @ColumnInfo(name = "employee_last_name", index = true)
    val employeeLasttName: String,
)