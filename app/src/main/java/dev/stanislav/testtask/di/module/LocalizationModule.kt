package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.App
import dev.stanislav.testtask.i18n.ILocalization
import dev.stanislav.testtask.i18n.IStringProvider
import io.reactivex.Scheduler
import dev.stanislav.testtask.i18n.AndroidResourceLocalization
import dev.stanislav.testtask.i18n.AndroidResourceStringProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
class LocalizationModule {

    @Singleton
    @Provides
    fun stringProvider(app: App): IStringProvider = AndroidResourceStringProvider(app)

    @Singleton
    @Provides
    fun localization(@Named("ui") scheduler: Scheduler, stringProvider: IStringProvider): ILocalization =
        AndroidResourceLocalization(scheduler, stringProvider)
}