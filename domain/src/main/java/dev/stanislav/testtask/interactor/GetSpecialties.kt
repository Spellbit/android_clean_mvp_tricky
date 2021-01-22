package dev.stanislav.testtask.interactor

import dev.stanislav.testtask.repo.ISpecialtiesRepo

class GetSpecialties(private val repo: ISpecialtiesRepo) {
    fun exec() = repo.getSpecialties()
    fun exec(ids: List<String>) = repo.getSpecialtiesLocal(ids)
}