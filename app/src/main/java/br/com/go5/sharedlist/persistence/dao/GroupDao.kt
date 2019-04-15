//package br.com.go5.sharedlist.persistence.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Query
//
//@Dao
//interface GroupDao: GenericDao<Group> {
//
//    @Query("SELECT * FROM [group]")
//    fun findAll(): LiveData<List<Group>>
//
//    @Query("SELECT * FROM [group] where id = :id")
//    fun findById(id: Long): LiveData<Group>
//
//}