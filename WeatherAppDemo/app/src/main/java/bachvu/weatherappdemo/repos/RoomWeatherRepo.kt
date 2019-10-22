package bachvu.weatherappdemo.repos

import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.models.Weather
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface RoomWeatherRepo {
    fun getWeatherList(): Single<List<Weather>>
    fun upsertWeather(weather: Weather): Completable
}

class RoomWeatherRepoImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : RoomWeatherRepo {

    override fun getWeatherList(): Single<List<Weather>> {
        return Single.fromCallable {
            weatherDao.getWeatherList()
                .map { it.toObject() }
        }
    }

    override fun upsertWeather(weather: Weather): Completable {
        return Completable.fromCallable {
            weatherDao.upsertWeather(weather.toEntity())
        }
    }
}
