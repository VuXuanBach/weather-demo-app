package bachvu.weatherappdemo.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import bachvu.weatherappdemo.database.RoomDbImpl
import bachvu.weatherappdemo.database.dao.WeatherDao
import bachvu.weatherappdemo.models.Weather
import generator.WeatherGenerator.generateWeatherList
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {
    private lateinit var database: RoomDbImpl
    private lateinit var dao: WeatherDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RoomDbImpl::class.java)
            .build()
        dao = database.weatherDao()

        val weatherList = generateWeatherList()
        weatherList.forEach {
            dao.upsertWeather(it.toEntity())
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun get_weather() {
        val weather = dao.getWeather("1")

        Assert.assertNotNull(weather)
        assertEquals("1", weather.id)
    }

    @Test
    fun get_weather_list() {
        val weatherList = dao.getWeatherList()

        assertEquals("1", weatherList[0].id)
    }

    @Test
    fun save_new_weather() {
        val weather = Weather("10", 2.0, 2.0, 3.0, "main", "icon", 1.0, "1", "2")
        dao.saveWeather(weather.toEntity())

        assertEquals(2.0, dao.getWeather("10").temp, 0.0)
    }

    @Test
    fun update_weather() {
        val weather = dao.getWeather("1")
        dao.updateWeather(weather.copy(main = "test3"))

        assertEquals("test3", dao.getWeather("1").main)
    }

    @Test
    fun upsert_new_weather() {
        val weather = Weather("10", 2.0, 2.0, 3.0, "main", "icon", 1.0, "1", "2")
        dao.upsertWeather(weather.toEntity())

        assertEquals(2.0, dao.getWeather("10").temp, 0.0)
    }

    @Test
    fun upsert_existing_weather() {
        val weather = Weather("1", 2.0, 2.0, 3.0, "main", "icon", 1.0, "1", "2")
        dao.upsertWeather(weather.toEntity())

        assertEquals("1", dao.getWeather("1").id)
        assertEquals(2.0, dao.getWeather("1").temp, 0.0)
        assertEquals("main", dao.getWeather("1").main)
        assertEquals("icon", dao.getWeather("1").icon)
        assertEquals(1.0, dao.getWeather("1").speed, 0.0)
        assertEquals("1", dao.getWeather("1").date)
        assertEquals("2", dao.getWeather("1").time)
    }

    @Test
    fun delete_weather() {
        dao.deleteWeather("1")

        assertNull(dao.getWeather("1"))
    }
}
