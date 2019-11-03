package app.learn.made.koin

import app.learn.made.feature.discovery.detail.DetailMoviePresenter
import app.learn.made.feature.discovery.list.DiscoveryPresenter
import app.learn.made.feature.discovery.review.ReviewPresenter
import com.google.gson.Gson
import org.koin.dsl.module.module

val appModule = module {
    single { Gson() }
    factory { DiscoveryPresenter(get()) }
    factory { DetailMoviePresenter(get()) }
    factory { ReviewPresenter(get()) }
}