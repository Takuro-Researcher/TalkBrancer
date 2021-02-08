package com.example.talkbrancer.setting

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
        usersRaw.add(User(id, MutableLiveData("")))
        _users.value = usersRaw
        peopleCount.value = peopleCount.value?.plus(1)
    }

    fun deleteLast() {
        usersRaw.removeLast()
        _users.value = usersRaw
        peopleCount.value = peopleCount.value?.minus(1)
    }

    fun isCanGoPage(): Pair<Boolean, String> {
        val nameList = usersRaw.map { it.name.value ?: "" }
        // 空の文字列が含まれている
        if (nameList.contains("")) {
            return Pair(false, "CONTAIN_NULL")
        }
        // 重複した文字が存在する
        if (nameList.distinct().size != nameList.size) {
            return Pair(false, "DUPLICATE_USER")
        }
        return Pair(true, "")
    }
}