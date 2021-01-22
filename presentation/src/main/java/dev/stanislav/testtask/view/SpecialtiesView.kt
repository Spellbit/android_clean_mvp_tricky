package dev.stanislav.testtask.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle


@AddToEndSingle
interface SpecialtiesView : MvpView {
    fun init()
    fun setTitle(text: String)
    fun updateList()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = "loading")
    fun showLoading()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = "loading")
    fun hideLoading()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = "retry")
    fun showRetry(message: String, actionText: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = "retry")
    fun hideRetry()
}