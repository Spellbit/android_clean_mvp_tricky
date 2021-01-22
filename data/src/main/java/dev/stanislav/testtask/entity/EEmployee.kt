package dev.stanislav.testtask.entity

data class EEmployee(
    val f_name: String,
    val l_name: String,
    val birthday: String?,
    val avatr_url: String?,
    val specialty: List<ESpecialty>
)