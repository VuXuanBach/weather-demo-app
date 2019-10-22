package bachvu.weatherappdemo.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CommonHelperTest {

    @Test
    fun `convert Fahrenheit to Celcius correctly`() {
        assertEquals(0.0, CommonHelper.convertFahrenheitToCelcius(32.0), 0.0)
        assertEquals(-35.55555555555556, CommonHelper.convertFahrenheitToCelcius(-32.0), 0.0)
        assertEquals(37.77777777777778, CommonHelper.convertFahrenheitToCelcius(100.0), 0.0)
        assertEquals(-17.77777777777778, CommonHelper.convertFahrenheitToCelcius(0.0), 0.0)
    }
}
