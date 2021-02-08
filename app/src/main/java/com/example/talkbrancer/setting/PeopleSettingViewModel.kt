package com.example.talkbrancer.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

data class User(
    val id: Long,
    var name: MutableLiveData<String> = MutableLiveData("")
) {}

class PeopleSettingViewModel(): ViewModel() {
    var peopleCount: MutableLiveData<Int> = MutableLiveData(0)
    private var _users:MutableLiveData<MutableList<User>> = MutableLiveData()
    private val usersRaw = LinkedList<User>()
    val users: LiveData<MutableList<User>> = _users
    var id: Long = 0L
    init {
        addUsers()
    }
    fun addUsers(){
        id += 1
        usersRaw.add(User(id,MutableLiveData("")))
        _users.value = usersRaw
        peopleCount.value = peopleCount.value?.plus(1)
    }
    fun deleteLast(){
        usersRaw.removeLast()
        _users.value =  usersRaw
        peopleCount.value = peopleCount.value?.minus(1)
    }
}