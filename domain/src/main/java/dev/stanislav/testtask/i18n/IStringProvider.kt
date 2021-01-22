package dev.stanislav.testtask.i18n

interface IStringProvider {
    fun getStringResourceByName(name: String): String?
}