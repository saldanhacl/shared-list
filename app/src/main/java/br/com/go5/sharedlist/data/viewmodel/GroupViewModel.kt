package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.repository.GroupRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.HttpException

class GroupViewModel: ViewModel(), KoinComponent {

    private val groupRepository: GroupRepository by inject()

    fun get(id: Long): LiveData<Group> {
        return groupRepository.get(id)
    }

    fun delete(group: Group): LiveData<Group> {
        return groupRepository.delete(group)
    }

    fun createInServer(groupName: String, creator: Long): LiveData<Group> {
        return groupRepository.createInServer(groupName, creator)
    }

    fun addUserToGroup(groupId: Long, userEmail: String): LiveData<Group> {
        return groupRepository.addUserToGroup(groupId, userEmail)
    }

    fun findAllByUser(userId: Long): LiveData<List<Group>>? {
        return groupRepository.findAllByUser(userId)
    }


}