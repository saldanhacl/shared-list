package br.com.go5.sharedlist.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.go5.sharedlist.data.model.ShoppingList

@Dao
interface ShoppingListDao: GenericDao<ShoppingList> {

    @Query("SELECT * FROM shoppinglist")
    fun findAll(): LiveData<List<ShoppingList>>

    @Query("SELECT * FROM shoppinglist where id = :id")
    fun findById(id: Long): LiveData<ShoppingList>

}