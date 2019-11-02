package app.learn.made.koin

import android.util.Log
import app.learn.made.BuildConfig
import app.learn.made.network.ApiKeyInterceptor
import app.learn.made.network.MovieClientService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createOkHttpClient() }
    factory { apiService<MovieClientService>(get(), BuildConfig.BASE_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val apiKeyInterceptor = ApiKeyInterceptor()
    val httpInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        Log.e("iMovie", it)
    })
    httpInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).addInterceptor(httpInterceptor).build()
}

inline fun <reified T> apiService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    return retrofit.create(T::class.java)
}
