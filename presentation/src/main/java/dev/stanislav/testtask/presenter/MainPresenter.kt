package dev.stanislav.testtask.presenter

import com.github.terrakok.cicerone.Router
import dev.stanislav.testtask.navigation.IScreens
import dev.stanislav.testtask.view.MainView
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter(): MvpPresenter<MainView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        router.replaceScreen(screens.specialties())
    }

    fun backClick() {
        router.exit()
    }

}