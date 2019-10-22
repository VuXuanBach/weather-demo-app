package bachvu.weatherappdemo.activities.main

import bachvu.weatherappdemo.BaseContract
import bachvu.weatherappdemo.models.WeatherByDate

interface Contract {

    interface View: BaseContract.BaseView {
        fun showWeatherList(weatherList: List<WeatherByDate>)
        fun handleError()
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter: BaseContract.BasePresenter<View> {
        fun load5DaysWeatherData(id: Int, appId: String)
    }
}
