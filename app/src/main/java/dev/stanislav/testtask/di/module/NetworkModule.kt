package dev.stanislav.testtask.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.toxicbakery.logging.Arbor
import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.App
import dev.stanislav.testtask.BuildConfig
import dev.stanislav.testtask.api.IDataSource
import dev.stanislav.testtask.network.AndroidNetworkStatus
import dev.stanislav.testtask.network.INetworkStatus
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Named("baseUrl")
    @Singleton
    @Provides
    fun baseUrl() = BuildConfig.API_BASE_URL

    @Singleton
    @Provides
    fun api(@Named("baseUrl") baseUrl: String, client: OkHttpClient, moshi: Moshi): IDataSource {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(IDataSource::class.java)
    }

    @Singleton
    @Provides
    fun okHttpClient(
        @Named("logging") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Named("logging")
    @Singleton
    @Provides
    fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor {
        Arbor.d(it)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)

}