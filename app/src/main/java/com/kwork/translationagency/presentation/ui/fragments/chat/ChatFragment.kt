package com.kwork.translationagency.presentation.ui.fragments.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.FragmentChatBinding
import com.kwork.translationagency.presentation.ui.fragments.chat.adapter.ChatAdapter
import com.kwork.translationagency.presentation.viewmodel.chat.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment: Fragment(R.layout.fragment_chat) {
    private val vm: ChatViewModel by viewModels()
    private val b by lazy {
        FragmentChatBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.backBtn.setOnClickListener { findNavController().popBackStack() }

        val adapter = ChatAdapter(FirebaseAuth.getInstance().uid!!)
        b.rvChat.apply {
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            this.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.messages.collect { list ->
                adapter.submitList(list)
                b.rvChat.scrollToPosition(list.lastIndex)
            }
        }

        b.btnSearchAction.setOnClickListener {
            b.searchView.text.toString().trim().let { txt ->
                if (txt.isNotEmpty()) {
                    vm.send(txt)
                    b.searchView.setText("")
                }
            }
        }
    }


}
