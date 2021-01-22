package dev.stanislav.testtask.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dev.stanislav.testtask.App
import dev.stanislav.testtask.R
import dev.stanislav.testtask.presenter.MainPresenter
import dev.stanislav.testtask.ui.BackButtonListener
import dev.stanislav.testtask.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    val navigator = AppNavigator(this, R.id.content)

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun start(context: Context) = context.startActivity(getIntent(context))
    }

    @Inject lateinit var navigatorHolder: NavigatorHolder

    val presenter by moxyPresenter {
        MainPresenter().apply {
            App.component.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.content)
        if (fragment != null && fragment is BackButtonListener && (fragment as BackButtonListener).backClick()) {
            return
        } else {
            presenter.backClick()
        }
    }
}