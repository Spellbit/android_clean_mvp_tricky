package dev.stanislav.testtask.storage.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.stanislav.testtask.storage.room.entity.StorageEmployee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employye: StorageEmployee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg employyes: StorageEmployee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employyes: List<StorageEmployee>)

    @Update
    fun update(employye: StorageEmployee)

    @Update
    fun update(vararg employyes: StorageEmployee)

    @Update
    fun update(employyes: List<StorageEmployee>)

    @Delete
    fun delete(employye: StorageEmployee)

    @Delete
    fun delete(vararg employyes: StorageEmployee)

    @Delete
    fun delete(employyes: List<StorageEmployee>)

    @Query("SELECT * FROM StorageEmployee")
    fun getAll(): List<StorageEmployee>

    @Query("SELECT * FROM StorageEmployee WHERE first_name = :firstName AND last_name = :lastName")
    fun getByName(firstName: String, lastName: String): StorageEmployee?
}