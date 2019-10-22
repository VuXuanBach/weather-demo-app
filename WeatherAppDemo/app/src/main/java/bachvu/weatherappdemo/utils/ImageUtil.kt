package bachvu.weatherappdemo.utils

import android.widget.ImageView
import bachvu.weatherappdemo.R
import com.bumptech.glide.Glide

class ImageUtil {

    companion object {
        private const val ICON_URL = "https://openweathermap.org/img/wn/%s@2x.png"

        fun loadWeatherIcon(imageView: ImageView, icon: String) {
            Glide
                .with(imageView)
                .load(String.format(ICON_URL, icon))
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}
