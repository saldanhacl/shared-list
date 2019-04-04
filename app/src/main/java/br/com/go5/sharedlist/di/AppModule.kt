package br.com.go5.sharedlist.di

import androidx.room.Room
import br.com.go5.sharedlist.data.repository.GroupRepository
import br.com.go5.sharedlist.data.repository.ProductRepository
import br.com.go5.sharedlist.data.repository.ShoppingListRepository
import br.com.go5.sharedlist.data.viewmodel.GroupViewModel
import br.com.go5.sharedlist.data.viewmodel.ProductViewModel
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.AppDatabase
import br.com.go5.sharedlist.utils.Utils
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule: Module = module {
    single { SharedListApplication() }
    single { RetrofitInit() }
    single { Utils(get()) }
    viewModel { ProductViewModel() }
    viewModel { GroupViewModel() }
    viewModel { ShoppingListViewModel() }
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "shared-list-database")
        .build() }

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().groupDao() }
    single { get<AppDatabase>().shoppingListDao() }
    single { ProductRepository() }
    single { GroupRepository() }
    single { ShoppingListRepository() }
}

val viewModelModule: Module = module {
    viewModel { ProductViewModel() }
}

val databaseModule: Module = module {
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "shared-list-database")
        .build() }

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().groupDao() }
    single { ProductRepository() }
    single { GroupRepository() }
}
