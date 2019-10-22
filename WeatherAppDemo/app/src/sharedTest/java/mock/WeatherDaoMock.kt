package mock

import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.database.entity.WeatherEntity
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single

fun WeatherDao.mockGetWeatherList(weatherEntityList: List<WeatherEntity>) {
    whenever(getWeatherList()).thenReturn(weatherEntityList)
}
