package bachvu.weatherappdemo.models

data class WeatherByDate(
    val date: String,
    val weatherByTimeList: MutableList<Weather>
)
