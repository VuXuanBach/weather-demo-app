package mock

import bachvu.weatherappdemo.api.NetworkApi
import bachvu.weatherappdemo.api.responsemodels.PrimaryDataResponseBody
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single

fun NetworkApi.mockExecuteSuccess(body: PrimaryDataResponseBody) {
    whenever(fetch5DaysWeatherData(any(), any())).thenReturn(Single.just(body))
}

fun NetworkApi.mockExecuteUnSuccess() {
    whenever(fetch5DaysWeatherData(any(), any())).thenReturn(Single.just(PrimaryDataResponseBody(500, listOf())))
}
