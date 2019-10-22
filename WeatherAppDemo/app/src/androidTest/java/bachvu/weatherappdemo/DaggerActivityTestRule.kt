package bachvu.weatherappdemo

import android.app.Activity
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

abstract class DaggerActivityTestRule<T : Activity>(
        private val activityClass: Class<T>,
        initialTouchMode: Boolean,
        launchActivity: Boolean
) : ActivityTestRule<T>(activityClass, initialTouchMode, launchActivity) {

    @Suppress("DEPRECATION")
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
        app.activityInjector = createFakeActivityInjector(activityClass) {
            setupMocks(it)
        }
    }

    abstract fun setupMocks(activity: T)

    @Suppress("UNCHECKED_CAST")
    private fun createFakeActivityInjector(clazz: Class<T>, setupMocks: (T) -> Unit): DispatchingAndroidInjector<Activity> {
        val injector = AndroidInjector<Activity> { instance ->
            if (instance.javaClass == clazz) {
                setupMocks(instance as T)
            }
        }
        val factory = AndroidInjector.Factory<Activity> { injector }
        val map = mapOf(Pair<Class<*>, Provider<AndroidInjector.Factory<*>>>(clazz, Provider { factory }))
        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map, emptyMap())
    }

}
