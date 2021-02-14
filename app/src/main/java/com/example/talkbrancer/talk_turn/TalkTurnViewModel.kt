package com.example.talkbrancer.talk_turn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.talkbrancer.setting.TalkTheme

// who_flag:0 = 話したい。　who_flag:1 = 聞きたい
data class TalkSessionSettingData(
    val name: String,
    val talkTheme: Pair<Int, String>,
    val who_flag: Int
) {}

data class TalkSession(
    val sessionMainPerson: String = "",
    val talkThemeString: String,
    val talkFormat: Int = 0
)

class TalkTurnViewModel(talkThemeSettingData: ArrayList<TalkTheme>) : ViewModel() {
    val talkThemeList: MutableList<TalkSessionSettingData> = mutableListOf()
    val isEndAction: MutableLiveData<Unit> = MutableLiveData()
    val nowTalkSession: MutableLiveData<TalkSession> = MutableLiveData()
    private val alreadyTalkTheme: MutableList<TalkSession> = mutableListOf()
    private val userNameList: MutableList<String> = mutableListOf()

    // xmlに反映させるLiveData
    val mainPersonText: MutableLiveData<String> = MutableLiveData()
    val talkThemeText: MutableLiveData<String> = MutableLiveData()
    val talkSubText: MutableLiveData<String> = MutableLiveData()

    init {
        val isContinue = initTalkThemeSetting(talkThemeSettingData)
        if (isContinue) {
            setTalkSessionMain()
        } else {
            isEndAction.value = Unit
        }
    }

    private fun initTalkThemeSetting(settingData: ArrayList<TalkTheme>): Boolean {
        settingData.forEach {
            userNameList.add(it.user_name)
            if (it.want_to_talk.second.isNotEmpty()) {
                talkThemeList.add(it.let {
                    TalkSessionSettingData(
                        it.user_name,
                        it.want_to_talk,
                        0
                    )
                })
            }
            if (it.want_you_to_talk.second.isNotEmpty()) {
                talkThemeList.add(it.let {
                    TalkSessionSettingData(
                        it.user_name,
                        it.want_you_to_talk,
                        1
                    )
                })
            }
        }
        // このまま設定を続けるべきか
        if (talkThemeList.size == 0) {
            return false
        }
        return true
    }

    fun uiTalkSession() {
        val tmp = nowTalkSession.value
        tmp?.apply {
            mainPersonText.value = this.sessionMainPerson
            talkThemeText.value = this.talkThemeString
            talkSubText.value = getTalkFormatSubText(this.talkFormat)
        }
    }

    fun setTalkSessionMain() {
        var talkSesion = TalkSession("", "")
        // 最大3回計算を行う
        for (i in 0..3) {
            talkSesion = decideTalkSession()
            if (!alreadyTalkTheme.contains(talkSesion)) {
                break
            }
        }
        alreadyTalkTheme.add(talkSesion)
        nowTalkSession.value = talkSesion
    }

    // LiveDataに表示するデータをアルゴリズムに従い返す。
    // TODO 計算部分は別メソッドに書くべきか。。
    private fun decideTalkSession(): TalkSession {

        val talkingTheme = talkThemeList.random()
        var talkSession = TalkSession("", "")

        if (talkingTheme.who_flag == 0) {
            //話したい　1 ならみんなで　2なら一人に向かって
            val talkFormat = if (talkingTheme.talkTheme.first == 0) 1 else 2
            talkSession = talkingTheme.let { TalkSession(it.name, it.talkTheme.second, talkFormat) }
        } else {
            var talkingPerson = ""
            val talkFormat: Int
            if (talkingTheme.talkTheme.first == 0) {
                // 誰かの話
                talkingPerson = userNameList.random()
                talkFormat = 3
            } else {
                // みんなの話
                talkFormat = 4
            }
            talkSession =
                talkingTheme.let { TalkSession(talkingPerson, it.talkTheme.second, talkFormat) }
        }
        return talkSession
    }

    private fun getTalkFormatSubText(talkFormatNum: Int): String {
        when (talkFormatNum) {
            1 -> return "について\nみんなで語り合いたい"
            2 -> return "について語りたい"
            3 -> return "について話す"
            4 -> return "みんなの\n話が聞きたい"
        }
        return ""
    }
}