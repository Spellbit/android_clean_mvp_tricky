package dev.stanislav.testtask.navigation

import com.github.terrakok.cicerone.Screen
import dev.stanislav.testtask.entity.Specialty

interface IScreens {
    fun specialties() : Screen
    fun specialty(specialtyId: String) : Screen
    fun employee(firstName: String, lastName: String) : Screen
}