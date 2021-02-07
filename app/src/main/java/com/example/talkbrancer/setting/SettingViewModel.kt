package com.example.talkbrancer.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

data class User(
    val name: MutableLiveData<String> = MutableLiveData("")
) {

}

class SettingViewModel(application: Application): AndroidViewModel(application) {
    var peopleCount: MutableLiveData<Int> = MutableLiveData(0)
    private var _users:MutableLiveData<MutableList<User>> = MutableLiveData()
    private val usersRaw = LinkedList<User>()
    val users: LiveData<MutableList<User>> = _users
    init {
        addUsers()
    }

    fun addUsers(){
        usersRaw.add(User(MutableLiveData("")))
        _users.value = usersRaw
        peopleCount.value = peopleCount.value?.plus(1)
    }
    fun deleteLast(){
        usersRaw.removeLast()
        _users.value =  usersRaw
        peopleCount.value = peopleCount.value?.minus(1)
    }
    fun deletePostion(){

    }
}