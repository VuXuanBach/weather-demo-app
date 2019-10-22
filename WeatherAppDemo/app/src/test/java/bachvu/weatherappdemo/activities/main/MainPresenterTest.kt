package bachvu.weatherappdemo.activities.main

import DummyData.DUMMY_APP_ID
import DummyData.DUMMY_ID
import MockSchedulerProvider
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import generator.WeatherGenerator.generateWeatherByDateList
import mock.mockExecuteFailed
import mock.mockExecuteSuccess
import org.junit.Before
import org.junit.Test

class MainPresenterTest {

    private val load5DaysWeatherUseCase: Load5DaysWeatherUseCase = mock()
    private val view: Contract.View = mock()
    private val presenter = MainPresenter(load5DaysWeatherUseCase, MockSchedulerProvider())

    @Before
    fun setUp() {
        presenter.attachView(view)
    }

    @Test
    fun `get all 5 days weather success`() {
        val weatherByDateList = generateWeatherByDateList()
        load5DaysWeatherUseCase.mockExecuteSuccess(weatherByDateList)
        presenter.load5DaysWeatherData(DUMMY_ID, DUMMY_APP_ID)

        verify(view).showWeatherList(weatherByDateList)
        verify(view).showProgressBar()
        verify(view).hideProgressBar()
    }

    @Test
    fun `get all 5 days weather failed`() {
        load5DaysWeatherUseCase.mockExecuteFailed()
        presenter.load5DaysWeatherData(DUMMY_ID, DUMMY_APP_ID)

        verify(view).handleError()
        verify(view).showProgressBar()
        verify(view).hideProgressBar()
    }
}
