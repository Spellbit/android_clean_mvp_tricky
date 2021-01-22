package dev.stanislav.testtask.interactor

import dev.stanislav.testtask.repo.IEmployeesRepo

class GetEmployee(private val employeesRepo: IEmployeesRepo) {
    fun exec(firstName: String, lastName: String) = employeesRepo.getEmployeeLocal(firstName, lastName)
}