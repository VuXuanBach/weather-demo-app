package bachvu.weatherappdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import bachvu.weatherappdemo.App
import javax.inject.Inject

interface NetworkUtil {
    fun isNetworkAvailable(): Boolean
}

class NetworkUtilImpl @Inject constructor() : NetworkUtil {

    override fun isNetworkAvailable(): Boolean {
        val cm =
            App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}
