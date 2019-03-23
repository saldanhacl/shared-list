package br.com.go5.sharedlist.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.persistence.dao.ProductDao

@Database(entities = [(Product::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao() : ProductDao
}