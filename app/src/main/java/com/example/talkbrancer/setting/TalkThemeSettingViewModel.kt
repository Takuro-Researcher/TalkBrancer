package com.example.talkbrancer.setting

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TalkTheme(
    val user_name: String,
    val want_to_talk: Pair<Int, String>,
    val want_you_to_talk: Pair<Int, String>
) : Parcelable

class TalkThemeSettingViewModel(user: List<User>): ViewModel() {
    private var nowIndex: Int = 0
    private val userRaw = user
    var usersSettingData: MutableList<TalkTheme> = mutableListOf()
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

    // フォームから
    fun nextUser() {
        setSetting()
        initTheme()
        // 次のユーザーに切り替える
        nowIndex += 1
        userSetting()
    }

    // フォームからデータを抜き取り、設定を保存する
    fun setSetting() {
        // 入力フォームからデータを取る
        val talkMode = if (shareTalkMode.value!!) 0 else 1
        val hereMode = if (shareHereMode.value!!) 0 else 1
        val wantToTalkRaw = wantToTalk.value ?: ""
        val wantYouToTalkRaw = wantYouToTalk.value ?: ""
        val theme = TalkTheme(
            userRaw[nowIndex].name.value!!,
            Pair(talkMode, wantToTalkRaw),
            Pair(hereMode, wantYouToTalkRaw)
        )
        usersSettingData.add(theme)
    }
}