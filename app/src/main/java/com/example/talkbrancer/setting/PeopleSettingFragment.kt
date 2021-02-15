package com.example.talkbrancer.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.talkbrancer.R
import com.example.talkbrancer.databinding.FragmentPeopleSettingBinding
import com.example.talkbrancer.databinding.UserItemBinding
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.ViewHolder
import kotlinx.android.synthetic.main.fragment_people_setting.*


class PeopleSettingFragment : Fragment() {
    private val viewModelPeople: PeopleSettingViewModel by activityViewModels()
    private lateinit var binding: FragmentPeopleSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleSettingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.data = viewModelPeople
        val groupAdapter = GroupAdapter<ViewHolder<*>>()
        binding.recyclerView.adapter = groupAdapter
        viewModelPeople.users.observe(viewLifecycleOwner, Observer {
            groupAdapter.update(it.toList().map { UserItem(it) })
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_people_setting_to_title_button.setOnClickListener {
            findNavController().navigate(R.id.action_PeopleSettingFragment_to_TitleFragment)
        }
        go_user_button.setOnClickListener {
            val result = viewModelPeople.isCanGoPage()
            val canGo = result.first
            val errorName = result.second
            if (canGo) {
                findNavController().navigate(R.id.action_PeopleSettingFragment_to_TalkThemeSettingFragment)
            } else {
                if (errorName == "CONTAIN_NULL") {
                    Snackbar.make(view, "名前の入力されていない人がいます", Snackbar.LENGTH_SHORT).show()
                } else if (errorName == "DUPLICATE_USER") {
                    Snackbar.make(view, "名前は重複しないようにお願いします", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_PeopleSettingFragment_to_TitleFragment)
        }
    }

}

data class UserItem(private val item: User):BindableItem<UserItemBinding>(item.hashCode().toLong()){
    override fun getLayout() = R.layout.user_item
    override fun bind(viewBinding: UserItemBinding, position: Int) {
        viewBinding.item = item
        viewBinding.postion = position
    }
}