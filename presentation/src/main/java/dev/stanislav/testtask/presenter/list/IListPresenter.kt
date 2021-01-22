package dev.stanislav.testtask.presenter.list

import dev.stanislav.testtask.view.list.ItemView
import io.reactivex.subjects.PublishSubject

interface IListPresenter<V: ItemView> {
    val itemClickSubject: PublishSubject<V>
    fun bindView(view: V)
    fun getCount(): Int
    fun getItemViewType(position: Int): Int = 0
    fun getSpanSize(position: Int, maxSize: Int): Int = 1
}