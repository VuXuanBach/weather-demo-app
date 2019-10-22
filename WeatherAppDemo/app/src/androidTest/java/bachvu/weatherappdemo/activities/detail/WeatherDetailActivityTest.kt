package bachvu.weatherappdemo.activities.detail

import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.models.Weather
import bachvu.weatherappdemo.utils.Constants.Companion.EXTRA_WEATHER
import kotlinx.android.synthetic.main.activity_weather_detail.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDetailActivityTest {

    @get:Rule
    val rule = ActivityTestRule(WeatherDetailActivity::class.java, true, false)

    @Test
    fun test_display_data_correctly() {
        val intent = Intent()
        intent.putExtra(
            EXTRA_WEATHER, Weather(
                "id1", 28.0, 25.0, 30.0,
                "Clear", "abc", 15.0, "2019-10-22", "15:00:00"
            )
        )
        rule.launchActivity(intent)

        onView(withId(R.id.tv_date_time)).check(matches(withText("15:00:00 2019-10-22")))
        onView(withId(R.id.tv_status)).check(matches(withText("Clear")))
        onView(withId(R.id.tv_wind)).check(matches(withText("Wind: 15.0")))
        onView(withId(R.id.tv_current)).check(matches(withText("Current: 28.00 °C")))
        onView(withId(R.id.tv_max)).check(matches(withText("Max: 30.00 °C")))
        onView(withId(R.id.tv_min)).check(matches(withText("Min: 25.00 °C")))
        onView(withId(R.id.icon_weather)).check(matches(isDisplayed()))
    }
}
