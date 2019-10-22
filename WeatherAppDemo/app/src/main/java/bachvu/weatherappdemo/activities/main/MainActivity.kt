package bachvu.weatherappdemo.activities.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.models.WeatherByDate
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Contract.View {

    @Inject
    lateinit var presenter: Contract.Presenter

    private lateinit var adapter: WeatherListAdapter
    private lateinit var errorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        loadData()
    }

    override fun onDestroy() {
        presenter.destroy()
        if (::errorDialog.isInitialized) {
            errorDialog.dismiss()
        }
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reload) {
            loadData()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        presenter.load5DaysWeatherData(524901, getString(R.string.app_id))
    }

    override fun showWeatherList(weatherList: List<WeatherByDate>) {
        adapter = WeatherListAdapter(weatherList)
        rv_weather_by_date_list.adapter = adapter
        rv_weather_by_date_list.layoutManager = LinearLayoutManager(this)
    }

    override fun handleError() {
        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.load_weathers_failed_message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}
