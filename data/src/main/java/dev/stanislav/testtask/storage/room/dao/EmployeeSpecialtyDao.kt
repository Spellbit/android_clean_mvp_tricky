package dev.stanislav.testtask.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.stanislav.testtask.storage.room.entity.SpecialtyWithEmployee
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty

@Dao
interface EmployeeSpecialtyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employeesSpecialties: List<StorageEmployeeSpecialty>)

    @Query("SELECT * FROM StorageEmployee JOIN StorageEmployeeSpecialty ON first_name = employee_first_name AND last_name = employee_last_name JOIN StorageSpecialty ON id = specialty_id")
    fun getSpecialtiesWithEmployees(): List<SpecialtyWithEmployee>

    @Query("SELECT * FROM StorageEmployee JOIN StorageEmployeeSpecialty ON first_name = employee_first_name AND last_name = employee_last_name JOIN StorageSpecialty ON id = specialty_id WHERE first_name = :firstName AND last_name = :lastName ")
    fun getSpecialtiesWithEmployeesForEmployee(firstName: String, lastName: String): List<SpecialtyWithEmployee>
}