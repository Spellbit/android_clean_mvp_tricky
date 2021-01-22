package dev.stanislav.testtask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import dev.stanislav.testtask.App
import dev.stanislav.testtask.R
import dev.stanislav.testtask.databinding.FragmentSpecialtiesBinding
import dev.stanislav.testtask.presenter.SpecialtiesPresenter
import dev.stanislav.testtask.ui.adapter.SpecialtiesRVAdapter
import dev.stanislav.testtask.view.SpecialtiesView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SpecialtiesFragment : MvpAppCompatFragment(), SpecialtiesView {

    var skeleton: Skeleton? = null

    companion object {
        fun newInstance() = SpecialtiesFragment()
    }

    val presenter by moxyPresenter {
        SpecialtiesPresenter().apply {
            App.component.inject(this)
        }
    }

    var vb: FragmentSpecialtiesBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentSpecialtiesBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun init() {
        (activity as? AppCompatActivity)?.setSupportActionBar(vb?.toolbar)

        vb?.rvSpecialties?.layoutManager = LinearLayoutManager(activity)
        vb?.rvSpecialties?.adapter = SpecialtiesRVAdapter(presenter.specialtiesListPresenter)
        vb?.rvSpecialties?.setHasFixedSize(true)
        skeleton = vb?.rvSpecialties?.applySkeleton(R.layout.item_specialty)
    }

    override fun setTitle(text: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = text
    }

    override fun updateList() {
        vb?.rvSpecialties?.adapter?.notifyDataSetChanged()
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
                .setAction(actionText){
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
}