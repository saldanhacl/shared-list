package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.go5.sharedlist.data.model.*
import br.com.go5.sharedlist.data.repository.GroupRepository
import br.com.go5.sharedlist.data.repository.ShoppingListRepository
import br.com.go5.sharedlist.di.SharedListApplication
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.HttpException

class ShoppingListViewModel: ViewModel(), KoinComponent {

    private val shoppingListRepository: ShoppingListRepository by inject()
    private val groupRepository: GroupRepository by inject()

    init {
    }

    fun getGroupById(id: Long): LiveData<Group> {
        return groupRepository.get(id)
    }

    fun delete(shoppingList: ShoppingList): LiveData<ShoppingList> {
        return shoppingListRepository.delete(shoppingList)
    }

    fun insertProductToList(shoppingList: ShoppingList, product: Product): LiveData<ShoppingList> {
        return shoppingListRepository.insertProductToList(shoppingList, product)
    }

    fun deleteProductFromList(shoppingList: ShoppingList, product: Product): LiveData<ShoppingList> {
        return shoppingListRepository.deleteProductFromList(shoppingList, product)
    }

    fun getProducts(shoppingList: ShoppingList): LiveData<MutableList<Product>> {
        return shoppingListRepository.getProducts(shoppingList)
    }

    fun getComments(shoppingList: ShoppingList): LiveData<MutableList<CommentResponse>> {
        return shoppingListRepository.getComments(shoppingList)
    }

    fun createInServer(shoppingListName: String, groupId: Long): LiveData<ShoppingList> {
        return shoppingListRepository.createInServer(shoppingListName, groupId)
    }

    fun createComment(shoppingList: ShoppingList, comment: String, userId: Long): LiveData<MutableList<CommentResponse>> {
        return shoppingListRepository.createComment(shoppingList, comment, userId)
    }

}