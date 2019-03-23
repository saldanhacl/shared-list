package br.com.go5.sharedlist.di

import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import br.com.go5.sharedlist.data.repository.ProductRepository
import br.com.go5.sharedlist.data.viewmodel.ProductViewModelFactory
import br.com.go5.sharedlist.persistence.AppDatabase
import br.com.go5.sharedlist.persistence.dao.ProductDao
import br.com.go5.sharedlist.persistence.dao.ProductDao_Impl
import br.com.go5.sharedlist.utils.Utils
import org.koin.android.ext.android.startKoin

class SharedListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(AppModule.getModule()))
    }

    fun getAppTheme(): Resources.Theme {
        return theme
    }

}