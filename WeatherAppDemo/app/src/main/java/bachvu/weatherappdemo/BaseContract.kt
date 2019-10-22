package bachvu.weatherappdemo

interface BaseContract {

    interface BaseView

    interface BasePresenter<T : BaseView> {
        fun attachView(view: T)
        fun destroy()
    }

}
