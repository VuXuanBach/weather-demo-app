package bachvu.weatherappdemo.api.responsemodels

import com.google.gson.annotations.SerializedName

data class MainResponseBody (
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double
)
