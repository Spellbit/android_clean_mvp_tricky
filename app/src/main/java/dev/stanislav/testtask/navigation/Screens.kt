package dev.stanislav.testtask.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.stanislav.testtask.ui.fragment.EmployeeFragment
import dev.stanislav.testtask.ui.fragment.SpecialtiesFragment
import dev.stanislav.testtask.ui.fragment.SpecialtyFragment

class Screens : IScreens {

    override fun specialties() = FragmentScreen { SpecialtiesFragment.newInstance() }
    override fun specialty(specialtyId: String) = FragmentScreen { SpecialtyFragment.newInstance(specialtyId) }
    override fun employee(firstName: String, lastName: String) = FragmentScreen { EmployeeFragment.newInstance(firstName, lastName) }

}