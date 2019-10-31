package app.learn.made

import android.app.Application
import app.learn.made.koin.appComponent
import org.koin.android.ext.android.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, appComponent)
    }
}