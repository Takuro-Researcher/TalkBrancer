package com.example.talkbrancer.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.talkbrancer.R
import com.example.talkbrancer.databinding.FragmentTalkThemeSettingBinding
import kotlinx.android.synthetic.main.fragment_talk_theme_setting.*

class TalkThemeSettingFragment : Fragment() {
    private val viewModelPeople: PeopleSettingViewModel by activityViewModels()
    private val viewModelTalkTheme: TalkThemeSettingViewModel by viewModels { TalkThemeSettingViewModelFactory(users = viewModelPeople.users.value?.toList()) }

    private lateinit var binding: FragmentTalkThemeSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTalkThemeSettingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.data = viewModelTalkTheme
        viewModelTalkTheme.gameStartAction.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                next_user_button.setOnClickListener {
                    findNavController().navigate(R.id.action_TalkThemeSettingFragment_to_TalkTurnFragment)
                }
            }
        })
    }
}

class TalkThemeSettingViewModelFactory(private val users: List<User>?) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return users?.let { TalkThemeSettingViewModel(it) } as T
    }
}