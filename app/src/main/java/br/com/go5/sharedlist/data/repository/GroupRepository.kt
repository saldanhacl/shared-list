package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.network.RetrofitInit
//import br.com.go5.sharedlist.persistence.dao.GroupDao
import br.com.go5.sharedlist.utils.Utils
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class GroupRepository: KoinComponent {

    private val retrofit: RetrofitInit by inject()
    private val utils: Utils by inject()

    fun get(id: Long): LiveData<Group> {
        val data = MutableLiveData<Group>()
        if (utils.isConnectedToInternet()) {
            retrofit.groupService().get(groupId = id).enqueue(
                object: Callback<Group> {
                    override fun onFailure(call: Call<Group>, t: Throwable) {
                        data.value = null
                    }
                    override fun onResponse(call: Call<Group>, response: Response<Group>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun createInServer(groupName: String, creator: Long): MutableLiveData<Group> {
        val data = MutableLiveData<Group>()
        if (utils.isConnectedToInternet()) {
            retrofit.groupService().create(groupName, creator).enqueue(
                object : Callback<Group> {
                    override fun onFailure(call: Call<Group>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<Group>, response: Response<Group>) {
                       data.value = response.body()
                    }

                }
            )
        }
        return data
    }

    fun delete(group: Group): LiveData<Group> {
        val data = MutableLiveData<Group>()
        if (utils.isConnectedToInternet()) {
            retrofit.groupService().delete(groupId = group.id).enqueue(
                object: Callback<Group> {
                    override fun onFailure(call: Call<Group>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<Group>, response: Response<Group>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun findAllByUser(userId: Long): LiveData<List<Group>>? {
        val data = MutableLiveData<List<Group>>()
        if (utils.isConnectedToInternet()) {
            retrofit.userService().getGroups(userId).enqueue(
                object: Callback<List<Group>> {
                    override fun onResponse(call: Call<List<Group>>, response: Response<List<Group>>) {
                        data.value = response.body()
                    }
                    override fun onFailure(call: Call<List<Group>>, t: Throwable) {
                        data.value = null
                    }
                }
            )
        }
        return data
    }
}