package dev.stanislav.testtask.storage.room.entity

import androidx.room.Embedded

data class SpecialtyWithEmployee(
    @Embedded val specialty: StorageSpecialty,
    @Embedded val employee: StorageEmployee
)