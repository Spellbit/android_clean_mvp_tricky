package dev.stanislav.testtask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.faltenreich.skeletonlayout.Skeleton
import com.google.android.material.snackbar.Snackbar
import dev.stanislav.testtask.App
import dev.stanislav.testtask.databinding.FragmentEmployeeBinding
import dev.stanislav.testtask.presenter.EmployeePresenter
import dev.stanislav.testtask.ui.BackButtonListener
import dev.stanislav.testtask.view.EmployeeView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class EmployeeFragment : MvpAppCompatFragment(), EmployeeView, BackButtonListener {

    var skeleton: Skeleton? = null

    companion object {
        private const val FIRST_NAME_KEY = "firstName"
        private const val LAST_NAME_KEY = "lastName"

        fun newInstance(firstName: String, lastName: String) = EmployeeFragment().apply {
            arguments = Bundle().apply {
                putString(FIRST_NAME_KEY, firstName)
                putString(LAST_NAME_KEY, lastName)
            }
        }
    }

    val presenter by moxyPresenter {
        val firstName = arguments!!.getString(FIRST_NAME_KEY)!!
        val lastName = arguments!!.getString(LAST_NAME_KEY)!!
        EmployeePresenter(firstName, lastName).apply {
            App.component.inject(this)
        }
    }

    var vb: FragmentEmployeeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentEmployeeBinding.inflate(inflater, container, false).also {
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
    }

    override fun setTitle(text: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = text
    }

    override fun setName(text: String) {
        vb?.tvName?.text = text
    }

    override fun setLastName(text: String) {
        vb?.tvLastName?.text = text
    }

    override fun setBirthDay(text: String) {
        vb?.tvBirthday?.text = text
    }

    override fun setAge(text: String) {
        vb?.tvAge?.text = text
    }

    override fun setSpecialty(text: String) {
        vb?.tvSpecialty?.text = text
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

    override fun backClick(): Boolean = presenter.backClick()
}