package br.com.go5.sharedlist.persistence.dao

import androidx.room.*

@Dao
interface GenericDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<T>)

    @Update
    fun update(t: T)

    @Update
    fun updateList(list: List<T>)

    @Delete
    fun delete(t: T)

}