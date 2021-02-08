package com.example.talkbrancer.talk_turn

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.talkbrancer.R
import com.example.talkbrancer.databinding.FragmentTalkTurnBinding

class TalkTurnFragment : Fragment() {
    private val viewModelTalkTurn: TalkTurnViewModel by viewModels()
    private lateinit var binding: FragmentTalkTurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTalkTurnBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.data = viewModelTalkTurn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val builder: AlertDialog.Builder? = requireActivity()?.let {
            AlertDialog.Builder(it)
        }
        // addCallbackはktxで提供されている拡張関数
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            builder?.setTitle("トークセッション終了")?.setTitle("参加者設定まで戻ります。\nよろしいですか？")
            builder?.apply {
                setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        findNavController().navigate(R.id.action_TalkTurnFragment_to_PeopleSettingFragment)
                    })
                setNegativeButton("NO")
            }
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
    }

}