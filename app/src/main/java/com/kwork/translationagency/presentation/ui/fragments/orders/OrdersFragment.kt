package com.kwork.translationagency.presentation.ui.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.BottomSheetSearchBinding
import com.kwork.translationagency.databinding.FragmentOrdersBinding
import com.kwork.translationagency.domain.model.OrderModel
import com.kwork.translationagency.presentation.ui.fragments.orders.adapter.OrdersFragmentAdapter
import com.kwork.translationagency.presentation.ui.fragments.search.SearchDialogFragment

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val sampleOrders = listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_calendarFragment)
        }
        binding.newOrderBtn.setOnClickListener{
            findNavController().navigate(R.id.action_ordersFragment_to_newOrderFragment)
        }
        val ordersAdapter = OrdersFragmentAdapter(sampleOrders)
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = ordersAdapter
        binding.searchView.setOnClickListener {
            val dialog = SearchDialogFragment()
            dialog.show(parentFragmentManager, "SearchDialog")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}