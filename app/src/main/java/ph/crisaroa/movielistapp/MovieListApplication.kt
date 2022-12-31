package ph.crisaroa.movielistapp

import android.app.Application
import com.google.android.material.color.DynamicColors

class MovieListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}