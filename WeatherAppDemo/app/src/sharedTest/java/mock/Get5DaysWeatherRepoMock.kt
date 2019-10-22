package mock

import bachvu.weatherappdemo.models.WeatherByDate
import bachvu.weatherappdemo.repos.Get5DaysWeatherRepo
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import java.lang.Exception

fun Get5DaysWeatherRepo.mockExecuteSuccess(weatherByDateList: List<WeatherByDate>) {
    whenever(execute(any(), any())).thenReturn(Single.just(weatherByDateList))
}
