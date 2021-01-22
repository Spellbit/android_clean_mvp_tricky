package dev.stanislav.testtask.interactor

import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.repo.IEmployeesRepo
import dev.stanislav.testtask.repo.ISpecialtiesRepo

class GetEmployees(private val specialtiesRepo: ISpecialtiesRepo, private val employeesRepo: IEmployeesRepo) {
    fun exec(specialtyId: String) = specialtiesRepo.getSpecialtyLocal(specialtyId)
        .flatMap {
            employeesRepo.getEmployeesLocal(it)
        }

    fun exec(specialty: Specialty) = employeesRepo.getEmployeesLocal(specialty)
}