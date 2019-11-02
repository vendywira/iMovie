package app.learn.made.network

import app.learn.made.BuildConfig
import app.learn.made.model.constant.Constant
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", Constant.LANGUAGE)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}