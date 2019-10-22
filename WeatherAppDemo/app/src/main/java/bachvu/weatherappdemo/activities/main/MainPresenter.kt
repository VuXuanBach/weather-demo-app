package bachvu.weatherappdemo.activities.main

import bachvu.weatherappdemo.SchedulerProvider
import bachvu.weatherappdemo.usecases.Load5DaysWeatherUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val load5DaysWeatherUseCase: Load5DaysWeatherUseCase,
    private val schedulerProvider: SchedulerProvider
) : Contract.Presenter {

    private lateinit var view: Contract.View
    private val disposables = CompositeDisposable()

    override fun attachView(view: Contract.View) {
        this.view = view
    }

    override fun destroy() {
        disposables.dispose()
    }

    override fun load5DaysWeatherData(id: Int, appId: String) {
        load5DaysWeatherUseCase.execute(id, appId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe {
                view.showProgressBar()
            }
            .doAfterTerminate {
                view.hideProgressBar()
            }
            .subscribe({
                view.showWeatherList(it)
            }, {
                it.printStackTrace()
                view.handleError()
            })
            .let { disposables.add(it)  }
    }
}
