package dev.stanislav.testtask.interactor

import dev.stanislav.testtask.repo.ISpecialtiesRepo

class GetSpecialty(private val repo: ISpecialtiesRepo) {
    fun exec(specialtyId: String) = repo.getSpecialtyLocal(specialtyId)
}