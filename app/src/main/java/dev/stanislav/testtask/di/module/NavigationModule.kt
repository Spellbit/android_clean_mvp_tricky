package dev.stanislav.testtask.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.navigation.Screens
import javax.inject.Singleton

@Module
class NavigationModule {

    var cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun cicerone(): Cicerone<Router> {
        return cicerone
    }

    @Singleton
    @Provides
    fun navigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Singleton
    @Provides
    fun router(): Router {
        return cicerone.router
    }

    @Singleton
    @Provides
    fun screens(): IScreens = Screens()

}