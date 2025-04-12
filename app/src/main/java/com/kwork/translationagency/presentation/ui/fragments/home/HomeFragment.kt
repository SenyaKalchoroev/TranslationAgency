package com.kwork.translationagency.presentation.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.databinding.FragmentHomeBinding
import com.kwork.translationagency.domain.model.MessageModel
import com.kwork.translationagency.domain.model.OrderModel
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.MessagesAdapter
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.OrdersAdapter


class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleOrders = listOf(
            OrderModel(
                id = 1,
                userName = "Иванова Т.",
                description = "Пер. с/на: EN - 15 000 р.",
                price = "15 000 р.",
                dateFrom = "12.03.2023",
                dateTo = "20.03.2023"
            ),
            OrderModel(
                id = 2,
                userName = "Иванова Т.",
                description = "Пер. с/на: FR - 10 000 р.",
                price = "10 000 р.",
                dateFrom = "13.03.2023",
                dateTo = "21.03.2023"
            )
        )

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
            )
        )

        val ordersAdapter = OrdersAdapter(sampleOrders)
        val messagesAdapter = MessagesAdapter(sampleMessages)

        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = ordersAdapter

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = messagesAdapter
    }
}
