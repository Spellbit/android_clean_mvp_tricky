package dev.stanislav.testtask.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = [
        "first_name",
        "last_name",
    ]
)
class StorageEmployee(
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val birthday: String?,
)