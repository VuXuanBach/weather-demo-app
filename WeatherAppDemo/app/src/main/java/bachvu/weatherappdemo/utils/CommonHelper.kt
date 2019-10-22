package bachvu.weatherappdemo.utils

object CommonHelper {

    fun convertFahrenheitToCelcius(fahrenheit: Double): Double {
        return ((fahrenheit - 32) * 5 / 9)
    }
}
