package com.kwork.translationagency.presentation.ui.fragments.orders

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentOrdersBinding
import com.kwork.translationagency.presentation.ui.fragments.orders.adapter.OrdersAdapter
import com.kwork.translationagency.presentation.ui.fragments.search.SearchDialogFragment
import com.kwork.translationagency.presentation.viewmodel.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding, OrdersViewModel>(
    R.layout.fragment_orders
) {

    override val binding by viewBinding(FragmentOrdersBinding::bind)
    override val viewModel by viewModels<OrdersViewModel>()
    private val adapter by lazy { OrdersAdapter() }

    override fun init() = with(binding) {
        rvOrders.layoutManager = LinearLayoutManager(requireContext())
        rvOrders.adapter = adapter
        progressBar.visibility = View.GONE
    }

    override fun launchObserver() {
        viewModel.loadOrders()
        viewModel.ordersState.observeUIState(
            loading = { binding.progressBar.visibility = View.VISIBLE },
            success = { list ->
                binding.progressBar.visibility = View.GONE
                adapter.submitList(list)
            },
            error = { msg ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun constructorListeners()= with(binding){
        btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_calendarFragment)
        }
        newOrderBtn.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_newOrderFragment)
        }
        searchView.setOnClickListener {
            SearchDialogFragment().show(parentFragmentManager, "SearchDialog")
        }
    }
}
