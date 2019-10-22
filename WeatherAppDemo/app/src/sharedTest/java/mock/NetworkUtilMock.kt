package mock

import bachvu.weatherappdemo.utils.NetworkUtil
import com.nhaarman.mockitokotlin2.whenever

fun NetworkUtil.mockIsNetworkAvailable(isAvailable: Boolean) {
    whenever(isNetworkAvailable()).thenReturn(isAvailable)
}
