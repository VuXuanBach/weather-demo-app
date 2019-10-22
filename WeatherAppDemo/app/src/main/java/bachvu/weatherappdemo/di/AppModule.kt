package bachvu.weatherappdemo.di

import bachvu.weatherappdemo.ApiProvider
import bachvu.weatherappdemo.ApiProviderImpl
import bachvu.weatherappdemo.SchedulerProvider
import bachvu.weatherappdemo.SchedulerProviderImpl
import bachvu.weatherappdemo.api.NetworkApi
import bachvu.weatherappdemo.utils.NetworkUtil
import bachvu.weatherappdemo.utils.NetworkUtilImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun apiProvider(): ApiProvider {
        return ApiProviderImpl()
    }

    @Singleton
    @Provides
    fun getDataService(api: ApiProvider): NetworkApi {
        return api.provideGetDataService()
    }

    @Provides
    @Singleton
    fun schedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    @Singleton
    fun networkUtil(): NetworkUtil {
        return NetworkUtilImpl()
    }
}
