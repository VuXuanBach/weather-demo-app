package bachvu.weatherappdemo.di

import bachvu.weatherappdemo.repos.Get5DaysWeatherRepo
import bachvu.weatherappdemo.repos.Get5DaysWeatherRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {

    @Binds
    abstract fun get5DaysWeatherRepo(repo: Get5DaysWeatherRepoImpl): Get5DaysWeatherRepo
}
