package com.example.talkbrancer.talk_turn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.talkbrancer.setting.TalkTheme
import kotlin.random.Random
import kotlin.random.nextInt

// who_flag:0 = 話したい。　who_flag:1 = 聞きたい
data class TalkSessionData(val name: String, val talkTheme: Pair<Int, String>, val who_flag: Int) {}

class TalkTurnViewModel(talkThemeSettingData: ArrayList<TalkTheme>) : ViewModel() {
    val talkThemeList: MutableList<TalkSessionData> = mutableListOf()
    val isEndAction: MutableLiveData<Unit> = MutableLiveData()
    private val alreadyTalkTheme: MutableList<TalkSessionData> = mutableListOf()

    init {
        emptyRemovingTalkTheme(talkThemeSettingData)
    }

    private fun emptyRemovingTalkTheme(settingData: ArrayList<TalkTheme>) {
        settingData.forEach {
            if (it.want_to_talk.second.isNotEmpty()) {
                talkThemeList.add(it.let { TalkSessionData(it.user_name, it.want_to_talk, 0) })
            }
            if (it.want_you_to_talk.second.isNotEmpty()) {
                talkThemeList.add(it.let { TalkSessionData(it.user_name, it.want_you_to_talk, 1) })
            }
        }
        if (talkThemeList.size == 0) {
            isEndAction.value = Unit
        }
    }

    private fun decideTalkSession() {
        val randomIndex = Random.nextInt(0..10)
    }
}