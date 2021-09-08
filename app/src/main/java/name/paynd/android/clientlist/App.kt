package name.paynd.android.clientlist

import android.app.Application
import name.paynd.android.clientlist.di.AppComponent
import name.paynd.android.clientlist.di.DaggerAppComponent

class App : Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}