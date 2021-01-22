package dev.stanislav.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import dev.stanislav.testtask.databinding.ItemSpecialtyBinding
import dev.stanislav.testtask.presenter.list.ISpecialtiesListPresenter
import dev.stanislav.testtask.view.list.SpecialtyItemView

class SpecialtiesRVAdapter(val presenter: ISpecialtiesListPresenter) : RecyclerView.Adapter<SpecialtiesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemSpecialtyBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            vb.root.clicks().map { this }.subscribe(presenter.itemClickSubject)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(val vb: ItemSpecialtyBinding) : RecyclerView.ViewHolder(vb.root), SpecialtyItemView {
        override var pos = 0
        override fun setTitle(text: String) {
            vb.tvTitle.text = text
        }
    }
}


