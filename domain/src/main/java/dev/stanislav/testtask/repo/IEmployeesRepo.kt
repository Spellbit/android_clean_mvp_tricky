package dev.stanislav.testtask.repo

import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import io.reactivex.Single

interface IEmployeesRepo {
    fun getEmployeesLocal(specialty: Specialty): Single<List<Employee>>
    fun getEmployeeLocal(firstName: String, lastName: String): Single<Employee>
}