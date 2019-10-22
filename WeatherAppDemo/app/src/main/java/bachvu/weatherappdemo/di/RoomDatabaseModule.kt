package bachvu.weatherappdemo.di

import bachvu.weatherappdemo.database.RoomDb
import bachvu.weatherappdemo.database.RoomDbImpl
import bachvu.weatherappdemo.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun roomDatabse(): RoomDbImpl {
        return RoomDb.instance
    }

    @Provides
    @Singleton
    fun weatherDao(database: RoomDbImpl): WeatherDao {
        return database.weatherDao()
    }
}
