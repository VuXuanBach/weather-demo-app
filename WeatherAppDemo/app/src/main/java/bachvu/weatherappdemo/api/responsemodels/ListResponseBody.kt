package bachvu.weatherappdemo.api.responsemodels

import com.google.gson.annotations.SerializedName

data class ListResponseBody(
    @SerializedName("dt")
    val dt: Long,

    @SerializedName("main")
    val main: MainResponseBody,

    @SerializedName("weather")
    val weatherList: List<WeatherResponseBody>,

    @SerializedName("wind")
    val wind: WindResponseBody,

    @SerializedName("dt_txt")
    val dateTime: String
)
