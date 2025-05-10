package com.kwork.translationagency.presentation.ui.fragments.home

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentHomeBinding
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.OrdersAdapter
import com.kwork.translationagency.presentation.ui.fragments.search.SearchDialogFragment
import com.kwork.translationagency.presentation.viewmodel.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, OrdersViewModel>(
    R.layout.fragment_home
) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<OrdersViewModel>()

    private val ordersAdapter = OrdersAdapter()

    override fun init() = with(binding) {
        userImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        newOrderCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newOrderFragment)
        }
        notifBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }
        searchView.setOnClickListener {
            SearchDialogFragment().show(parentFragmentManager, "SearchDialog")
        }
        newClientCard.setOnClickListener {
            findNavController().navigate(R.id.chatFragment)
        }

        rvOrders.layoutManager = LinearLayoutManager(requireContext())
        rvOrders.adapter = ordersAdapter
    }

    override fun launchObserver() {
        viewModel.loadOrders()
        viewModel.ordersState.observeUIState(
            loading = {
                binding.progressBar.visibility = View.VISIBLE
            },
            success = { list ->
                binding.progressBar.visibility = View.GONE
                ordersAdapter.submitList(list)
            },
            error = { msg ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun constructorListeners() = Unit
}
