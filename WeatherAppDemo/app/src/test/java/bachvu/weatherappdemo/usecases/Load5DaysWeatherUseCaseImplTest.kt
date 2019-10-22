package bachvu.weatherappdemo.usecases

import DummyData.DUMMY_APP_ID
import DummyData.DUMMY_ID
import bachvu.weatherappdemo.repos.Get5DaysWeatherRepo
import com.nhaarman.mockitokotlin2.mock
import generator.WeatherGenerator
import mock.mockExecuteSuccess
import org.junit.Test

class Load5DaysWeatherUseCaseImplTest {

    private val get5DaysWeatherRepo: Get5DaysWeatherRepo = mock()
    private val useCase = Load5DaysWeatherUseCaseImpl(get5DaysWeatherRepo)

    @Test
    fun `load 5 days weather success`() {
        val weatherByDateList = WeatherGenerator.generateWeatherByDateList()
        get5DaysWeatherRepo.mockExecuteSuccess(weatherByDateList)
        useCase.execute(DUMMY_ID, DUMMY_APP_ID)
            .test()
            .assertValue {
                it == weatherByDateList
            }
    }
}
