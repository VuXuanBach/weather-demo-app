package bachvu.weatherappdemo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bachvu.weatherappdemo.models.Weather

@Entity(tableName = "weathers")
data class WeatherEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "temp")
    val temp: Double,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double,

    @ColumnInfo(name = "main")
    val main: String,

    @ColumnInfo(name = "icon")
    val icon: String,

    @ColumnInfo(name = "speed")
    val speed: Double,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "time")
    val time: String
) {
    fun toObject(): Weather {
        return Weather(id, temp, tempMin, tempMax, main, icon, speed, date, time)
    }
}
