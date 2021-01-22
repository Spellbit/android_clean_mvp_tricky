package dev.stanislav.testtask.mapper

interface IMapper<Entity, Data> {
    fun map(entity: Entity): Data
}