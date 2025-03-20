package ru.lemonapes.dungler

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import ru.lemonapes.dungler.network.HttpClientProvider

@HiltAndroidApp
class DunglerApp : Application() {

    override fun onTerminate() {
        super.onTerminate()
        runBlocking {
            HttpClientProvider.close()
        }
    }
}