package bachvu.weatherappdemo.models

import android.os.Parcelable
import bachvu.weatherappdemo.database.entity.WeatherEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val id: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val main: String,
    val icon: String,
    val speed: Double,
    val date: String,
    val time: String
) : Parcelable {

    fun toEntity(): WeatherEntity {
        return WeatherEntity(id, temp, tempMin, tempMax, main, icon, speed, date, time)
    }
}
