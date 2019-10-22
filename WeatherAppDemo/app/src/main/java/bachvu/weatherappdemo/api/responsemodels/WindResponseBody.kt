package bachvu.weatherappdemo.api.responsemodels

import com.google.gson.annotations.SerializedName

data class WindResponseBody(
    @SerializedName("speed")
    val speed: Double
)
