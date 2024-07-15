package kz.android.amphibians

import android.app.Application
import kz.android.amphibians.data.AppContainer
import kz.android.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}