package mock

import bachvu.weatherappdemo.models.WeatherByDate
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import java.lang.Exception

fun Load5DaysWeatherUseCase.mockExecuteSuccess(weatherByDateList: List<WeatherByDate>) {
    whenever(execute(any(), any())).thenReturn(Single.just(weatherByDateList))
}

fun Load5DaysWeatherUseCase.mockExecuteFailed() {
    whenever(execute(any(), any())).thenReturn(Single.error(Exception()))
}
