package bachvu.weatherappdemo.di

import bachvu.weatherappdemo.activities.main.MainActivity
import bachvu.weatherappdemo.activities.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}
