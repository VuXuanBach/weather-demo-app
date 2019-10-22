package bachvu.weatherappdemo.activities.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.models.Weather
import bachvu.weatherappdemo.utils.Constants.Companion.EXTRA_WEATHER
import bachvu.weatherappdemo.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_weather_detail.*

class WeatherDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)
        setSupportActionBar(toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val nonNullActionBar = supportActionBar
        if (nonNullActionBar != null) {
            nonNullActionBar.setDisplayHomeAsUpEnabled(true)
            nonNullActionBar.setDisplayShowHomeEnabled(true)
        }

        val weather = intent.getParcelableExtra<Weather>(EXTRA_WEATHER)
        tv_date_time.text = "${weather.time} ${weather.date}"
        tv_status.text = weather.main
        tv_wind.text = getString(R.string.wind, weather.speed.toString())
        tv_current.text = getString(R.string.current_temp, weather.temp)
        tv_max.text = getString(R.string.max_temp, weather.tempMax)
        tv_min.text = getString(R.string.min_temp, weather.tempMin)

        ImageUtil.loadWeatherIcon(icon_weather, weather.icon)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
