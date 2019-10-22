package bachvu.weatherappdemo

import android.util.Log
import bachvu.weatherappdemo.api.NetworkApi
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface ApiProvider {
    fun provideGetDataService(): NetworkApi
}

class ApiProviderImpl @Inject constructor() : ApiProvider {

    private fun retrofitBuilder(): Retrofit.Builder {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client())
    }

    private fun client(): OkHttpClient {
        val apiHttpLogger = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .loggable(true)
            .request("Request")
            .response("Response")
            .logger { _, _, message -> Log.i("bachvu", message) }
            .build()

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(apiHttpLogger)
        }

        return clientBuilder.build()
    }

    private inline fun <reified T> createApi(clazz: Class<T>): T {
        return retrofitBuilder()
            .baseUrl(BASE_URL)
            .build()
            .create(clazz)
    }

    override fun provideGetDataService(): NetworkApi {
        return createApi(NetworkApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://samples.openweathermap.org"
    }
}
