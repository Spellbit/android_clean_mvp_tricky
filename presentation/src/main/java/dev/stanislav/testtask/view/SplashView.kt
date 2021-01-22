package dev.stanislav.testtask.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface SplashView: MvpView {
    fun proceed()
}