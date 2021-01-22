package dev.stanislav.testtask.repo

import dev.stanislav.testtask.entity.Specialty
import io.reactivex.Single

interface ISpecialtiesRepo {
    fun getSpecialties(): Single<List<Specialty>>
    fun getSpecialtiesLocal(ids: List<String> = listOf()): Single<List<Specialty>>
    fun getSpecialtyLocal(id: String): Single<Specialty>
}