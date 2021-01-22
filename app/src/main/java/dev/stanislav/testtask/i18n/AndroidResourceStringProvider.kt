package dev.stanislav.testtask.i18n

import dev.stanislav.testtask.App

class AndroidResourceStringProvider(val app: App) : IStringProvider {
    override fun getStringResourceByName(name: String): String? {
        val packageName = app.packageName
        val resId = app.resources.getIdentifier(name, "string", packageName)
        if (resId == 0) {
            return null
        }
        return app.resources.getString(resId)
    }
}
