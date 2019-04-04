package br.com.go5.sharedlist.di

import android.app.Application
import android.content.res.Configuration
import org.koin.android.ext.android.startKoin
import androidx.appcompat.app.AppCompatDelegate
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_MASK



class SharedListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        simulateDayNight(0)
    }

    fun simulateDayNight(currentSetting: Int) {
        val DAY = 0
        val NIGHT = 1
        val FOLLOW_SYSTEM = 3

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        }
    }

}