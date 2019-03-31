package br.com.go5.sharedlist.di

import androidx.room.Room
import br.com.go5.sharedlist.data.repository.ProductRepository
import br.com.go5.sharedlist.data.viewmodel.ProductViewModel
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.AppDatabase
import br.com.go5.sharedlist.utils.Utils
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object AppModule {

    fun getModule(): Module = module {
        single { SharedListApplication() }
        single { Utils(get()) }
        single { ProductRepository() }
        single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "shared-list-database")
            .build() }
        single { get<AppDatabase>().productDao() }
        viewModel { ProductViewModel() }
        single { RetrofitInit() }
    }
}