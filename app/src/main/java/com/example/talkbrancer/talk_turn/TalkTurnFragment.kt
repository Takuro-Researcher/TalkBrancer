package com.example.talkbrancer.talk_turn

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.talkbrancer.R
import com.example.talkbrancer.databinding.FragmentTalkTurnBinding
import com.example.talkbrancer.setting.TalkTheme
import kotlinx.android.synthetic.main.fragment_talk_turn.*

class TalkTurnFragment : Fragment() {
    private val viewModelTalkTurn: TalkTurnViewModel by viewModels {
        TalkTurnViewModelFactory(
            talkThemeDatas = requireArguments().getParcelableArrayList<TalkTheme>("SETTING")
        )
    }
    private lateinit var binding: FragmentTalkTurnBinding
    private val startAnimationSet: AnimatorSet = AnimatorSet()


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


        val fadeInSet: Animator =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.animator_fade_in_right)
                .apply {
                    this.setTarget(talk_turn_speaker_name_text)
                }
        val fadeInSet2: Animator =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.animator_fade_in_up).apply {
                this.setTarget(talk_turn_topic_text)
            }

        startAnimationSet.play(fadeInSet).before(fadeInSet2)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder: AlertDialog.Builder = requireActivity().let {
            AlertDialog.Builder(it)
        }

        viewModelTalkTurn.nowTalkSession.observe(viewLifecycleOwner, Observer {
            viewModelTalkTurn.uiTalkSession()
            startAnimationSet.start()
        })

        viewModelTalkTurn.isEndAction.observe(viewLifecycleOwner, Observer {
            builder.setTitle("誰も話したいことないんだって")?.setMessage("参加者設定まで戻ります")
            builder.apply {
                setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { _, _ ->
                        findNavController().navigate(R.id.action_TalkTurnFragment_to_PeopleSettingFragment)
                    })
                setNegativeButton("NO",
                    DialogInterface.OnClickListener { _, _ -> })
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        })
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            builder.setTitle("トークセッション終了")?.setMessage("参加者設定まで戻ります。\nよろしいですか？")
            builder.apply {
                setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { _, _ ->
                        findNavController().navigate(R.id.action_TalkTurnFragment_to_PeopleSettingFragment)
                    })
                setNegativeButton("NO",
                    DialogInterface.OnClickListener { _, _ -> })
            }
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
    }

}

class TalkTurnViewModelFactory(private val talkThemeDatas: ArrayList<TalkTheme>?) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return talkThemeDatas?.let { TalkTurnViewModel(it) } as T
    }
}