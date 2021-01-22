package dev.stanislav.testtask.storage.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StorageSpecialty (
    @PrimaryKey var id: String,
    var name: String?
)