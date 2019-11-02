package app.learn.made.koin

import app.learn.made.feature.discovery.list.DiscoveryPresenter
import com.google.gson.Gson
import org.koin.dsl.module.module

val appModule = module {
    single { Gson() }
    factory { DiscoveryPresenter(get()) }
}