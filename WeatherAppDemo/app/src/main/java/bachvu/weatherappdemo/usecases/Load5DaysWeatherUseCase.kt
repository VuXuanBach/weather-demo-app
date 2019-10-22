package bachvu.weatherappdemo.usecases

import bachvu.weatherappdemo.models.WeatherByDate
import bachvu.weatherappdemo.repos.Get5DaysWeatherRepo
import io.reactivex.Single
import javax.inject.Inject

interface Load5DaysWeatherUseCase {

    fun execute(id: Int, appId: String): Single<List<WeatherByDate>>
}

class Load5DaysWeatherUseCaseImpl @Inject constructor(
    private val get5DaysWeatherRepo: Get5DaysWeatherRepo
) : Load5DaysWeatherUseCase {

    override fun execute(id: Int, appId: String): Single<List<WeatherByDate>> {
        return get5DaysWeatherRepo.execute(id, appId)
    }
}

