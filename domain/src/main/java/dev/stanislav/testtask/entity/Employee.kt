package dev.stanislav.testtask.entity

import org.threeten.bp.LocalDate

data class Employee(
    val firstName: String,
    val lastName: String,
    val birthday: LocalDate?,
    var specialtyIds: List<String> = listOf()
)