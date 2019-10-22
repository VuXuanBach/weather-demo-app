package bachvu.weatherappdemo.api

import bachvu.weatherappdemo.api.responsemodels.PrimaryDataResponseBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("/data/2.5/forecast")
    fun fetch5DaysWeatherData(
        @Query("id") id: Int,
        @Query("appid") appId: String
    ): Single<PrimaryDataResponseBody>
}
