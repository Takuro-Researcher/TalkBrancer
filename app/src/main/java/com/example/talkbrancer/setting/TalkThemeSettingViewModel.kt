package com.example.talkbrancer.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class TalkTheme(
    val user: User,
    val want_to_talk:Pair<Int, String>,
    val want_you_to_talk: Pair<Int, String>
)

class TalkThemeSettingViewModel(user: List<User>): ViewModel() {
    private var nowIndex: Int = 0
    private val userRaw = user
    private var usersSettingData: MutableList<TalkTheme> = mutableListOf()
    var userName: MutableLiveData<String> = MutableLiveData()

    // 誰かと話したい
    val wantYouToTalk: MutableLiveData<String> = MutableLiveData()

    // 誰かに話したい
    val wantToTalk: MutableLiveData<String> = MutableLiveData()

    //　EditText
    val shareTalkMode: MutableLiveData<Boolean> = MutableLiveData(true)
    val shareHereMode: MutableLiveData<Boolean> = MutableLiveData(true)

    // 全員の設定が終了した際Trueにする。
    val gameStartAction: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        userSetting()
    }

    fun userSetting() {
        // 次のユーザーで最後の場合、実行するアクションを変える
        if (nowIndex + 1 == userRaw.size) {
            gameStartAction.postValue(true)
        }
        userName.postValue(userRaw[nowIndex].name.value)
    }
    fun initTheme(){
        wantToTalk.value = ""
        wantYouToTalk.value = ""
        shareHereMode.value = true
        shareTalkMode.value = true
    }

    fun nextUser(){
        // 入力フォームからデータを取る
        val talkMode = if(shareTalkMode.value!!) 0 else 1
        val hereMode = if(shareHereMode.value!!) 0 else 1
        val wantToTalkRaw = wantToTalk.value ?: ""
        val wantYouToTalkRaw = wantYouToTalk.value ?: ""
        val theme = TalkTheme(
            userRaw[nowIndex],
            Pair(talkMode, wantToTalkRaw),
            Pair(hereMode, wantYouToTalkRaw)
        )
        usersSettingData.add(theme)
        // 次のユーザー用にデータを削除する
        initTheme()
        // 次のユーザーに切り替える
        nowIndex += 1
        userSetting()
    }
}