package dev.stanislav.testtask

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.LogCatSeedling
import dev.stanislav.testtask.ui.activity.MainActivity
import dev.stanislav.testtask.ui.activity.SplashActivity
import dev.stanislav.testtask.di.AppComponent
import dev.stanislav.testtask.di.DaggerAppComponent
import dev.stanislav.testtask.di.module.AppModule
import dev.stanislav.testtask.logging.ProductionSeedling
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
        val component
            get() = instance.component
    }

    val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityDestroyed(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activity?.let {
                activityHolder.registerActivity(it)
            }
        }

        override fun onActivityPaused(activity: Activity) {
            activity?.let {
                activityHolder.unregisterActivity()
            }
        }

        override fun onActivityResumed(activity: Activity) {
            activity?.let {
                activityHolder.registerActivity(it as AppCompatActivity)
            }
        }

        override fun onActivityStarted(activity: Activity) {
            when (activity) {
                is SplashActivity -> initedCorrectly = true
                is MainActivity -> {
                    if (!initedCorrectly) {
                        SplashActivity.start(this@App)
                    }
                }
            }
        }
    }

    lateinit var component: AppComponent
    @Inject lateinit var activityHolder: ActivityHolder
    private var initedCorrectly: Boolean = false


    override fun onCreate() {
        super.onCreate()
        instance = this

        registerDefaultUncaughtExceptionHandler()

        if (BuildConfig.DEBUG) {
            Arbor.sow(LogCatSeedling())
        } else {
            val logPath = getExternalFilesDir(null) ?: filesDir
            Arbor.sow(ProductionSeedling(LogCatSeedling(), logPath.toString()))
        }
        AndroidThreeTen.init(this)

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        component.inject(this)
        //registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private fun registerDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Arbor.e(throwable)
        }
    }
}