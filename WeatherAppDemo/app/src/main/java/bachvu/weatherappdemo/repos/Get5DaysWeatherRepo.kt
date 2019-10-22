package bachvu.weatherappdemo.repos

import bachvu.weatherappdemo.api.NetworkApi
import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.models.Weather
import bachvu.weatherappdemo.models.WeatherByDate
import bachvu.weatherappdemo.utils.CommonHelper
import bachvu.weatherappdemo.utils.NetworkUtil
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

interface Get5DaysWeatherRepo {
    fun execute(id: Int, appId: String): Single<List<WeatherByDate>>
}

class Get5DaysWeatherRepoImpl @Inject constructor(
    private val networkApi: NetworkApi,
    private val weatherDao: WeatherDao,
    private val networkUtil: NetworkUtil
) : Get5DaysWeatherRepo {

    override fun execute(id: Int, appId: String): Single<List<WeatherByDate>> {
        return if (networkUtil.isNetworkAvailable()) {
            networkApi.fetch5DaysWeatherData(id, appId)
                .map { primaryData ->
                    if (primaryData.code == SUCCESS_CODE) {
                        primaryData.list
                            .filter { it.weatherList.isNotEmpty() }
                            .flatMap { body ->
                                val tk = StringTokenizer(body.dateTime)
                                val date = tk.nextToken()
                                val time = tk.nextToken()
                                body.weatherList.map {
                                    Weather(
                                        "${body.dt}-${it.id}" ,
                                        CommonHelper.convertFahrenheitToCelcius(body.main.temp),
                                        CommonHelper.convertFahrenheitToCelcius(body.main.tempMin),
                                        CommonHelper.convertFahrenheitToCelcius(body.main.tempMax),
                                        it.main,
                                        it.icon,
                                        body.wind.speed,
                                        date,
                                        time
                                    )
                                }
                            }
                            .map {
                                weatherDao.upsertWeather(it.toEntity())
                                it
                            }
                    } else {
                        query5DaysWeatherDataFromDb()
                    }
                }
        } else {
            Single.fromCallable {
                query5DaysWeatherDataFromDb()
            }
        }
            .map {
                val weatherByDateList = mutableListOf<WeatherByDate>()
                it.sortedBy { w -> w.id }.forEach { weather ->
                    weatherByDateList.find { weatherByDate -> weather.date == weatherByDate.date }
                        .apply {
                            if (this == null) {
                                weatherByDateList.add(
                                    WeatherByDate(
                                        weather.date, mutableListOf(weather)
                                    )
                                )
                            } else {
                                this.weatherByTimeList.add(weather)
                            }
                        }
                }
                weatherByDateList
            }
    }

    private fun query5DaysWeatherDataFromDb(): List<Weather> {
        return weatherDao.getWeatherList().map {
            it.toObject()
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
