package com.example.talkbrancer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_how_to.*
import kotlinx.android.synthetic.main.fragment_title.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HowToFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_how_to, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_howto_to_title_button.setOnClickListener {
            findNavController().navigate(R.id.action_HowToFragment_to_TitleFragment)
        }
    }
}