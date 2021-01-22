package dev.stanislav.testtask.storage.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty

@Dao
interface SpecialtyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(specialty: StorageSpecialty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg specialties: StorageSpecialty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(specialties: List<StorageSpecialty>)

    @Update
    fun update(specialty: StorageSpecialty)

    @Update
    fun update(vararg specialties: StorageSpecialty)

    @Update
    fun update(specialties: List<StorageSpecialty>)

    @Delete
    fun delete(specialty: StorageSpecialty)

    @Delete
    fun delete(vararg specialties: StorageSpecialty)

    @Delete
    fun delete(specialties: List<StorageSpecialty>)

    @Query("SELECT * FROM StorageSpecialty")
    fun getAll(): List<StorageSpecialty>

    @Query("SELECT * FROM StorageSpecialty WHERE id = :id LIMIT 1")
    fun findById(id: String): StorageSpecialty?

    @Query("SELECT * FROM StorageSpecialty WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): List<StorageSpecialty>
}