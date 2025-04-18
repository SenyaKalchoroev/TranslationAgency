package com.kwork.translationagency.presentation.ui.fragments.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.FragmentHomeBinding
import com.kwork.translationagency.databinding.FragmentNotificationBinding
import com.kwork.translationagency.domain.model.MessageModel
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.MessagesAdapter
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.OrdersAdapter

class NotificationFragment : Fragment() {
    private val binding by lazy {
        FragmentNotificationBinding.inflate(layoutInflater)
    }

    val sampleMessages = listOf(
        MessageModel(
            id = 1,
            userName = "Лиза В.",
            messageText = "Лиза В. приняла заказ...",
            date = "Сегодня, 12:15"
        ),
        MessageModel(
            id = 2,
            userName = "Павел Ф.",
            messageText = "Детали заказа уточнены...",
            date = "Сегодня, 11:00"
        ),
        MessageModel(
            id = 3,
            userName = "Людмила П.",
            messageText = "Проверка выполненной задачи",
            date = "Вчера, 09:30"
        ),MessageModel(
            id = 4,
            userName = "Лиза В.",
            messageText = "Лиза В. приняла заказ...",
            date = "Сегодня, 12:15"
        ),
        MessageModel(
            id = 5,
            userName = "Павел Ф.",
            messageText = "Детали заказа уточнены...",
            date = "Сегодня, 11:00"
        ),
        MessageModel(
            id = 6,
            userName = "Людмила П.",
            messageText = "Проверка выполненной задачи",
            date = "Вчера, 09:30"
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messagesAdapter = MessagesAdapter(sampleMessages)

        binding.rvMessagesYesterday.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessagesYesterday.adapter = messagesAdapter

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = messagesAdapter
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}