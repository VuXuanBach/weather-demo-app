package generator

import bachvu.weatherappdemo.api.responsemodels.*

object ResponseBodyGenerator {

    fun generatePrimaryDataResponseBody(code: Int = 200): PrimaryDataResponseBody {
        val listResponseBody = mutableListOf<ListResponseBody>()
        repeat(2) { i ->
            repeat(5) { index ->
                val mainResponseBody = MainResponseBody(index * 200.0, index * 100.0, index * 300.0)
                val windResponseBody = WindResponseBody(index * 3.0)
                val weatherList = listOf(
                    WeatherResponseBody(index, "main-$index", "description-$index", "icon-$index")
                )
                listResponseBody.add(
                    ListResponseBody(
                        i * 100L,
                        mainResponseBody,
                        weatherList,
                        windResponseBody,
                        "dateTime $i"
                    )
                )
            }
        }

        return PrimaryDataResponseBody(code, listResponseBody)
    }
}
