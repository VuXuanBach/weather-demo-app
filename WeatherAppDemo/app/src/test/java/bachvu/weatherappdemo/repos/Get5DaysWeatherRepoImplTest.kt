package bachvu.weatherappdemo.repos

import DummyData.DUMMY_APP_ID
import DummyData.DUMMY_ID
import bachvu.weatherappdemo.api.NetworkApi
import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.utils.NetworkUtil
import com.nhaarman.mockitokotlin2.*
import generator.ResponseBodyGenerator.generatePrimaryDataResponseBody
import generator.WeatherGenerator.generateWeatherList
import mock.mockExecuteSuccess
import mock.mockExecuteUnSuccess
import mock.mockGetWeatherList
import mock.mockIsNetworkAvailable
import org.junit.Test
import java.text.DecimalFormat
import kotlin.math.abs

class Get5DaysWeatherRepoImplTest {

    private val networkApi: NetworkApi = mock()
    private val weatherDao: WeatherDao = mock()
    private val networkUtil: NetworkUtil = mock()
    private val repo = Get5DaysWeatherRepoImpl(networkApi, weatherDao, networkUtil)

    private val df = DecimalFormat("0.00")

    @Test
    fun `fetch remote data success`() {
        networkUtil.mockIsNetworkAvailable(true)
        val body = generatePrimaryDataResponseBody()
        networkApi.mockExecuteSuccess(body)

        repo.execute(DUMMY_ID, DUMMY_APP_ID)
            .test()
            .assertValue {
                it[0].date == "dateTime"
                        && it[0].weatherByTimeList[1].id == "0-1"
                        && it[0].weatherByTimeList[1].date == "dateTime"
                        && it[0].weatherByTimeList[1].icon == "icon-1"
                        && it[0].weatherByTimeList[1].main == "main-1"
                        && it[0].weatherByTimeList[1].speed == 3.0
                        && df.format(it[0].weatherByTimeList[1].temp) == "93.33"
                        && df.format(it[0].weatherByTimeList[1].tempMin) == "37.78"
                        && df.format(it[0].weatherByTimeList[1].tempMax) == "148.89"
            }
        verify(weatherDao, atLeastOnce()).upsertWeather(any())
    }

    @Test
    fun `load local data success when code is not 200`() {
        networkUtil.mockIsNetworkAvailable(true)
        networkApi.mockExecuteUnSuccess()
        val weatherEntities = generateWeatherList().map { it.toEntity() }
        weatherDao.mockGetWeatherList(weatherEntities)

        repo.execute(DUMMY_ID, DUMMY_APP_ID)
            .test()
            .assertValue {
                it[0].date == "2019-10-22"
                        && it[0].weatherByTimeList[0].id == "1"
                        && it[0].weatherByTimeList[0].date == "2019-10-22"
                        && it[0].weatherByTimeList[0].icon == "1"
                        && it[0].weatherByTimeList[0].main == "Clear"
                        && it[0].weatherByTimeList[0].speed == 20.0
                        && df.format(it[0].weatherByTimeList[0].temp) == "10.00"
                        && df.format(it[0].weatherByTimeList[0].tempMin) == "1.00"
                        && df.format(it[0].weatherByTimeList[0].tempMax) == "20.00"
            }
        verify(weatherDao, never()).upsertWeather(any())
    }

    @Test
    fun `load local data success when there is no internet`() {
        networkUtil.mockIsNetworkAvailable(false)
        val weatherEntities = generateWeatherList().map { it.toEntity() }
        weatherDao.mockGetWeatherList(weatherEntities)

        repo.execute(DUMMY_ID, DUMMY_APP_ID)
            .test()
            .assertValue {
                it[0].date == "2019-10-22"
                        && it[0].weatherByTimeList[0].id == "1"
                        && it[0].weatherByTimeList[0].date == "2019-10-22"
                        && it[0].weatherByTimeList[0].icon == "1"
                        && it[0].weatherByTimeList[0].main == "Clear"
                        && it[0].weatherByTimeList[0].speed == 20.0
                        && df.format(it[0].weatherByTimeList[0].temp) == "10.00"
                        && df.format(it[0].weatherByTimeList[0].tempMin) == "1.00"
                        && df.format(it[0].weatherByTimeList[0].tempMax) == "20.00"
            }
        verify(weatherDao, never()).upsertWeather(any())
    }
}
