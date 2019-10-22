package bachvu.weatherappdemo.repos

import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.models.Weather
import com.nhaarman.mockitokotlin2.mock
import generator.WeatherGenerator.generateWeatherList
import mock.mockGetWeatherList
import org.junit.Test

class RoomWeatherRepoImplTest {

    private val weatherDao: WeatherDao = mock()
    private val repo = RoomWeatherRepoImpl(weatherDao)

    @Test
    fun `get weather list success`() {
        val weatherEntityList = generateWeatherList()
        weatherDao.mockGetWeatherList(weatherEntityList.map { it.toEntity() })
        repo.getWeatherList()
            .test()
            .assertValue {
                it == weatherEntityList
            }
    }

    @Test
    fun `upsert weather success`() {
        repo.upsertWeather(Weather("1", 1.0, 1.0, 1.0, "", "", 1.0, "", ""))
            .test()
            .assertComplete()
    }
}
