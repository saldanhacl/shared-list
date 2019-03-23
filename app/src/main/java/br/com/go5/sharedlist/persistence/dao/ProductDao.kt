package br.com.go5.sharedlist.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.go5.sharedlist.data.model.Product

@Dao
interface ProductDao: GenericDao<Product> {

    @Query("SELECT * FROM product")
    fun findAll(): LiveData<List<Product>>

    @Query("SELECT * FROM product where id = :id")
    fun findById(id: Long): LiveData<Product>

}