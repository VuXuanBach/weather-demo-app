package bachvu.weatherappdemo.activities.main

import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun presenter(presenter: MainPresenter): Contract.Presenter

    @Binds
    abstract fun load5DaysWeatherUseCase(useCaseImpl: Load5DaysWeatherUseCaseImpl): Load5DaysWeatherUseCase
}
