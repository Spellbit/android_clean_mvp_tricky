package dev.stanislav.testtask.storage.room.mapper

import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty

class RoomSpecialtiesMapper : IStorageMapper<Specialty, StorageSpecialty> {
    override fun mapTo(entity: Specialty): StorageSpecialty? {
        return StorageSpecialty(
            entity.id ?: return null,
            entity.name
        )
    }

    override fun mapFrom(entity: StorageSpecialty): Specialty = Specialty(entity.id, entity.name)
}