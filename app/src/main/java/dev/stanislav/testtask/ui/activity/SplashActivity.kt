package dev.stanislav.testtask.ui.activity

import android.content.Context
import android.content.Intent
import dev.stanislav.testtask.App
import dev.stanislav.testtask.presenter.SplashPresenter
import dev.stanislav.testtask.view.SplashView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class SplashActivity : MvpAppCompatActivity(), SplashView {
    companion object {
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun start(context: Context) = context.startActivity(getIntent(context))
    }

    val presenter by moxyPresenter {
        SplashPresenter().apply {
            App.component.inject(this)
        }
    }

    override fun proceed() {
        MainActivity.start(this)
    }
}