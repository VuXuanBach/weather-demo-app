package generator

import bachvu.weatherappdemo.models.Weather
import bachvu.weatherappdemo.models.WeatherByDate

object WeatherGenerator {

    fun generateWeatherList(): List<Weather> {
        return listOf(
            Weather("1", 10.0, 1.0, 20.0, "Clear", "1", 20.0, "2019-10-22", "12:00:00"),
            Weather("2", 30.0, 3.0, 40.0, "Snow", "2", 40.0, "2019-10-22", "15:00:00"),
            Weather("3", 20.0, 2.0, 30.0, "Cloud", "3", 30.0, "2019-10-22", "18:00:00"),
            Weather("4", 10.0, 1.0, 20.0, "Rain", "1", 20.0, "2019-10-22", "00:00:00"),
            Weather("5", 30.0, 3.0, 40.0, "Clear", "2", 40.0, "2019-10-22", "03:00:00"),
            Weather("6", 20.0, 2.0, 30.0, "Snow", "3", 30.0, "2019-10-22", "06:00:00")
        )
    }

    fun generateWeatherByDateList(): List<WeatherByDate> {
        val weatherByDateList = mutableListOf<WeatherByDate>()
        generateWeatherList().forEach { weather ->
            weatherByDateList.find { weatherByDate -> weather.date == weatherByDate.date }
                .apply {
                    if (this == null) {
                        weatherByDateList.add(
                            WeatherByDate(
                                weather.date, mutableListOf(weather)
                            )
                        )
                    } else {
                        this.weatherByTimeList.add(weather)
                    }
                }
        }
        return weatherByDateList
    }
}
