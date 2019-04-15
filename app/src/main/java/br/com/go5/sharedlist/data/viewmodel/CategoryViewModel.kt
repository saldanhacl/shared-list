package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.repository.CategoryRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CategoryViewModel: ViewModel(), KoinComponent {

    private val categoryRepository: CategoryRepository by inject()
    fun get(): LiveData<List<Category>> {
        return categoryRepository.getAll()
    }

}