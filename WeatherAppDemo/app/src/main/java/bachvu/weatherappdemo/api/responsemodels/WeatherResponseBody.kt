package bachvu.weatherappdemo.api.responsemodels

import com.google.gson.annotations.SerializedName

data class WeatherResponseBody(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)
