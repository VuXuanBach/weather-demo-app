package bachvu.weatherappdemo.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bachvu.weatherappdemo.App
import bachvu.weatherappdemo.database.RoomDb.VERSION
import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.database.entity.WeatherEntity

object RoomDb {

    @JvmStatic
    val instance by lazy {
        Room.databaseBuilder(App.getContext(), RoomDbImpl::class.java, DATABASE_NAME)
            .addMigrations()
            .build()
    }

    private const val DATABASE_NAME = "weather-demo-db"
    const val VERSION = 1
}

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = VERSION
)
abstract class RoomDbImpl : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
