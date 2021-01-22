package dev.stanislav.testtask.storage.room.mapper

interface IStorageMapper<Entity, Stored> {
    fun mapTo(entity: Entity): Stored?
    fun mapFrom(entity: Stored): Entity
}