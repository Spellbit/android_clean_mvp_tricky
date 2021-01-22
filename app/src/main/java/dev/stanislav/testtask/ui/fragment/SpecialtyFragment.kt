package dev.stanislav.testtask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import dev.stanislav.testtask.App
import dev.stanislav.testtask.R
import dev.stanislav.testtask.databinding.FragmentSpecialtyBinding
import dev.stanislav.testtask.presenter.SpecialtyPresenter
import dev.stanislav.testtask.ui.BackButtonListener
import dev.stanislav.testtask.ui.adapter.EmployeesRVAdapter
import dev.stanislav.testtask.view.SpecialtyView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SpecialtyFragment : MvpAppCompatFragment(), SpecialtyView, BackButtonListener {

    var skeleton: Skeleton? = null

    companion object {
        private const val SPECIALTY_KEY = "specialty"

        fun newInstance(specialtyId: String) = SpecialtyFragment().apply {
            arguments = Bundle().apply {
                putString(SPECIALTY_KEY, specialtyId)
            }
        }
    }

    val presenter by moxyPresenter {
        val specialtyId = arguments!!.getString(SPECIALTY_KEY)!!
        SpecialtyPresenter(specialtyId).apply {
            App.component.inject(this)
        }
    }

    var vb: FragmentSpecialtyBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentSpecialtyBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> presenter.backClick()
        else -> {
            true
        }
    }

    override fun init() {
        (activity as? AppCompatActivity)?.setSupportActionBar(vb?.toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb?.rvEmployees?.layoutManager = LinearLayoutManager(activity)
        vb?.rvEmployees?.adapter = EmployeesRVAdapter(presenter.employeesListPresenter)
        vb?.rvEmployees?.setHasFixedSize(true)
        skeleton = vb?.rvEmployees?.applySkeleton(R.layout.item_employee)
    }

    override fun setTitle(text: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = text
    }

    override fun updateList() {
        vb?.rvEmployees?.adapter?.notifyDataSetChanged()
    }

    override fun showLoading() {
        skeleton?.showSkeleton()
    }

    override fun hideLoading() {
        skeleton?.showOriginal()
    }

    var retrySnackbar: Snackbar? = null
    override fun showRetry(message: String, actionText: String) {
        view?.let {
            retrySnackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction(actionText) {
                    presenter.retryClick()
                }
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        presenter.retryClosed(event == DISMISS_EVENT_TIMEOUT)
                    }
                })
            retrySnackbar?.show()
        }
    }

    override fun hideRetry() {
        retrySnackbar?.dismiss()
        retrySnackbar = null
    }

    override fun backClick(): Boolean =  presenter.backClick()
}