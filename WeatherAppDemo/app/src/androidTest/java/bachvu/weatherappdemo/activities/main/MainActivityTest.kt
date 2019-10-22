package bachvu.weatherappdemo.activities.main

import MockSchedulerProvider
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import bachvu.weatherappdemo.DaggerActivityTestRule
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.RecyclerViewAssertions.atPositionOnRecyclerView
import bachvu.weatherappdemo.activities.detail.WeatherDetailActivity
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import com.nhaarman.mockitokotlin2.mock
import generator.WeatherGenerator.generateWeatherByDateList
import mock.mockExecuteFailed
import mock.mockExecuteSuccess
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule =
        object : DaggerActivityTestRule<MainActivity>(MainActivity::class.java, true, false) {
            override fun setupMocks(activity: MainActivity) {
                activity.presenter = presenter
            }
        }

    private val load5DaysWeatherUseCase: Load5DaysWeatherUseCase = mock()
    private val presenter = MainPresenter(load5DaysWeatherUseCase, MockSchedulerProvider())

    @Test
    fun test_error_dialog_appears_when_loading_weather_list_failed() {
        load5DaysWeatherUseCase.mockExecuteFailed()
        launchActivity()

        onView(withText(R.string.load_weathers_failed_message)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(doesNotExist())
    }

    @Test
    fun test_weather_list_data_are_shown_when_loading_success() {
        val input = generateWeatherByDateList()
        load5DaysWeatherUseCase.mockExecuteSuccess(input)
        launchActivity()

        onView(atPositionOnRecyclerView(R.id.rv_weather_by_date_list, 0, R.id.tv_title))
            .check(matches(withText("2019-10-22")))
        onView(atPositionOnRecyclerView(R.id.rv_weather_by_date_list, 0, R.id.weather_icon))
            .check(matches(isDisplayed()))
        onView(atPositionOnRecyclerView(R.id.rv_weather_by_date_list, 0, R.id.tv_weather_status))
            .check(matches(withText("CLEAR")))
        onView(atPositionOnRecyclerView(R.id.rv_weather_by_date_list, 0, R.id.tv_weather_time))
            .check(matches(withText("12:00:00")))

        onView(atPositionOnRecyclerView(R.id.rv_time_weather, 0, R.id.tv_child_weather_time))
            .check(matches(withText("12:00:00")))
        onView(atPositionOnRecyclerView(R.id.rv_time_weather, 0, R.id.child_weather_icon))
            .check(matches(isDisplayed()))
        onView(atPositionOnRecyclerView(R.id.rv_time_weather, 1, R.id.tv_child_weather_time))
            .check(matches(withText("15:00:00")))
        onView(atPositionOnRecyclerView(R.id.rv_time_weather, 1, R.id.child_weather_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_can_go_to_detail_screen() {
        Intents.init()
        val input = generateWeatherByDateList()
        load5DaysWeatherUseCase.mockExecuteSuccess(input)
        launchActivity()
        Intents.intending(IntentMatchers.hasComponent(WeatherDetailActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null))

        onView(atPositionOnRecyclerView(R.id.rv_weather_by_date_list, 0, R.id.tv_title))
            .perform(click())

        Intents.intended(IntentMatchers.hasComponent(WeatherDetailActivity::class.java.name))
        Intents.release()
    }

    private fun launchActivity() {
        val intent = Intent()
        rule.launchActivity(intent)
    }
}
