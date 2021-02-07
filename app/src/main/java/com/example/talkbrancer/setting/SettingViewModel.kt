package com.example.talkbrancer.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingViewModel(application: Application): AndroidViewModel(application) {
    var peopleCount: MutableLiveData<Int> = MutableLiveData()

    init {
        peopleCount.value = 3
    }

}