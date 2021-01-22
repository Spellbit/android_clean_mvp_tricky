package dev.stanislav.testtask.view.list

interface EmployeeItemView : ItemView {
    fun setName(text: String)
    fun setLastName(text: String)
    fun setAge(text: String)
}