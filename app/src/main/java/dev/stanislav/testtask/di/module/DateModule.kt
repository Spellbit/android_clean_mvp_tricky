package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
class DateModule {

    @Provides
    @Singleton
    fun correctFormat() = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    @Provides
    @Singleton
    fun additionalFormats(dateTimeFormatter: DateTimeFormatter) = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
    ).plus(dateTimeFormatter)

}