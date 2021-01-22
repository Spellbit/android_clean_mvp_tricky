package dev.stanislav.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import dev.stanislav.testtask.databinding.ItemEmployeeBinding
import dev.stanislav.testtask.presenter.list.IEmployeesListPresenter
import dev.stanislav.testtask.view.list.EmployeeItemView

class EmployeesRVAdapter(val presenter: IEmployeesListPresenter) : RecyclerView.Adapter<EmployeesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            vb.root.clicks().map { this }.subscribe(presenter.itemClickSubject)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(val vb: ItemEmployeeBinding) : RecyclerView.ViewHolder(vb.root), EmployeeItemView {
        override var pos = 0
        override fun setName(text: String) {
            vb.tvName.text = text
        }

        override fun setLastName(text: String) {
            vb.tvLastName.text = text
        }

        override fun setAge(text: String) {
            vb.tvAge.text = text
        }
    }
}


