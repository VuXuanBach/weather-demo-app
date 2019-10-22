package bachvu.weatherappdemo.activities.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.activities.detail.WeatherDetailActivity
import bachvu.weatherappdemo.models.WeatherByDate
import bachvu.weatherappdemo.utils.Constants.Companion.CLICK_INTERVAL_TIME
import bachvu.weatherappdemo.utils.Constants.Companion.EXTRA_WEATHER
import bachvu.weatherappdemo.utils.ImageUtil
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class WeatherListAdapter(
    private val weatherByDateList: List<WeatherByDate>
) : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private val disposables = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_by_date_list_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        disposables.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int {
        return weatherByDateList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherByDate = weatherByDateList[position]
        holder.tvTitle.text = weatherByDate.date
        if (weatherByDate.weatherByTimeList.isNotEmpty()) {
            val weatherByTime = weatherByDate.weatherByTimeList[0]

            holder.tvWeatherStatus.text = weatherByTime.main.toUpperCase()
            holder.tvWeatherTime.text = weatherByTime.time
            ImageUtil.loadWeatherIcon(holder.weatherIcon, weatherByTime.icon)

            holder.rvTimeWeather.apply {
                val lm = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = TimeWeatherListAdapter(weatherByDate.weatherByTimeList)
                val dividerItemDecoration = DividerItemDecoration(this.context, lm.orientation)
                addItemDecoration(dividerItemDecoration)
                setRecycledViewPool(viewPool)
            }

            holder.cardView.clicks().throttleFirst(CLICK_INTERVAL_TIME, TimeUnit.MILLISECONDS)
                .subscribe({
                    val intent = Intent(holder.cardView.context, WeatherDetailActivity::class.java)
                    intent.putExtra(EXTRA_WEATHER, weatherByTime)
                    holder.cardView.context.startActivity(intent)
                }, {
                    it.printStackTrace()
                })
                .let { disposables.add(it) }
        }
    }

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_view)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvWeatherStatus: TextView = view.findViewById(R.id.tv_weather_status)
        val tvWeatherTime: TextView = view.findViewById(R.id.tv_weather_time)
        val weatherIcon: ImageView = view.findViewById(R.id.weather_icon)
        val rvTimeWeather: RecyclerView = view.findViewById(R.id.rv_time_weather)
    }
}
