package com.example.talkbrancer.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.talkbrancer.R
import com.example.talkbrancer.databinding.FragmentPeopleSettingBinding
import com.example.talkbrancer.databinding.UserItemBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.ViewHolder
import kotlinx.android.synthetic.main.fragment_people_setting.*


class PeopleSettingFragment : Fragment() {
    private val viewModel: SettingViewModel by activityViewModels()
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
        binding.data = viewModel
        val groupAdapter = GroupAdapter<ViewHolder<*>>()
        binding.recyclerView.adapter = groupAdapter
        viewModel.users.observe(viewLifecycleOwner, Observer {
            groupAdapter.update(it.toList().map { UserItem(it) })
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_people_setting_to_title_button.setOnClickListener {
            findNavController().navigate(R.id.action_PeopleSettingFragment_to_TitleFragment)
        }
        go_user_button.setOnClickListener {
            //TODO 画面遷移
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