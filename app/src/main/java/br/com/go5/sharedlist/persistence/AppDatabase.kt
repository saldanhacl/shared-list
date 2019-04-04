package br.com.go5.sharedlist.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.persistence.dao.GroupDao
import br.com.go5.sharedlist.persistence.dao.ProductDao
import br.com.go5.sharedlist.persistence.dao.ShoppingListDao

@Database(entities = [Product::class, Group::class, ShoppingList::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao() : ProductDao
    abstract fun groupDao() : GroupDao
    abstract fun shoppingListDao() : ShoppingListDao
}