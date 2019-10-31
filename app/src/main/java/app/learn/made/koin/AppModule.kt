package app.learn.made.koin

import com.google.gson.Gson
import org.koin.dsl.module.module

val appModule = module {
    single { Gson() }
}