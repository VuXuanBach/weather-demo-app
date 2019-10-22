package bachvu.weatherappdemo.database.dao

import androidx.room.*
import bachvu.weatherappdemo.database.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weathers WHERE id = :id")
    fun getWeather(id: String): WeatherEntity

    @Query("SELECT * FROM weathers")
    fun getWeatherList(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveWeather(weather: WeatherEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(weather: WeatherEntity)

    @Transaction
    fun upsertWeather(weather: WeatherEntity) {
        if (saveWeather(weather) == -1L) {
            updateWeather(weather)
        }
    }

    @Query("DELETE FROM weathers WHERE id = :id")
    fun deleteWeather(id: String)
}
