package bachvu.weatherappdemo.activities.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bachvu.weatherappdemo.R
import bachvu.weatherappdemo.activities.detail.WeatherDetailActivity
import bachvu.weatherappdemo.models.Weather
import bachvu.weatherappdemo.utils.Constants
import bachvu.weatherappdemo.utils.Constants.Companion.CLICK_INTERVAL_TIME
import bachvu.weatherappdemo.utils.ImageUtil
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class TimeWeatherListAdapter(
    private val weatherList: List<Weather>
) : RecyclerView.Adapter<TimeWeatherListAdapter.TimeWeatherViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val disposables = CompositeDisposable()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        disposables.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeWeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_time_item, parent, false)
        return TimeWeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: TimeWeatherViewHolder, position: Int) {
        holder.childLayout.clicks().throttleFirst(CLICK_INTERVAL_TIME, TimeUnit.MILLISECONDS)
            .subscribe({
                val intent = Intent(holder.childLayout.context, WeatherDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_WEATHER, weatherList[position])
                holder.childLayout.context.startActivity(intent)
            }, {
                it.printStackTrace()
            })
            .let { disposables.add(it) }
        holder.tvWeatherTime.text = weatherList[position].time
        ImageUtil.loadWeatherIcon(holder.weatherIcon, weatherList[position].icon)
    }

    class TimeWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val childLayout: View = view.findViewById(R.id.child_layout)
        val tvWeatherTime: TextView = view.findViewById(R.id.tv_child_weather_time)
        val weatherIcon: ImageView = view.findViewById(R.id.child_weather_icon)
    }
}
