package bachvu.weatherappdemo

import android.app.Activity
import bachvu.di.DaggerTestAppComponent
import dagger.android.DispatchingAndroidInjector

class TestApp : App() {

    lateinit var originalActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        originalActivityInjector = activityInjector
    }

    override fun setUpDagger() {
        DaggerTestAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}
