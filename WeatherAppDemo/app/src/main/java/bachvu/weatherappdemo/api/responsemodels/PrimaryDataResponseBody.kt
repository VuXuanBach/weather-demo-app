package bachvu.weatherappdemo.api.responsemodels

import com.google.gson.annotations.SerializedName

data class PrimaryDataResponseBody(
    @SerializedName("cod")
    val code: Int,

    @SerializedName("list")
    val list: List<ListResponseBody>
)
