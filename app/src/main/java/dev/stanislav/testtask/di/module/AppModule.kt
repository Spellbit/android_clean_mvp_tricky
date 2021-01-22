package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.ActivityHolder
import dev.stanislav.testtask.App
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    fun app() = app

    @Named("ui")
    @Singleton
    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun activityHolder(): ActivityHolder = ActivityHolder()

}