package com.kwork.translationagency.presentation.ui.fragments.clients

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.Extensions.gone
import com.kwork.translationagency.core.utils.Extensions.showToast
import com.kwork.translationagency.core.utils.Extensions.visible
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentClientsBinding
import com.kwork.translationagency.presentation.ui.fragments.clients.adapter.ClientFragmentAdapter
import com.kwork.translationagency.presentation.viewmodel.clients.ClientsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientsFragment
    : BaseFragment<FragmentClientsBinding, ClientsViewModel>(R.layout.fragment_clients) {

    override val binding by viewBinding(FragmentClientsBinding::bind)
    override val viewModel by viewModels<ClientsViewModel>()

    private val adapter by lazy {
        ClientFragmentAdapter { clientUi ->
            val action = ClientsFragmentDirections
                .actionClientsFragmentToProfileFragment(clientUi.id)
            findNavController().navigate(action)
        }
    }

    override fun init() = with(binding) {
        rvOrders.layoutManager = GridLayoutManager(requireContext(), 3)
        rvOrders.adapter = adapter
    }

    override fun launchObserver() {
        viewModel.loadClients()
        viewModel.clientsState.observeUIState(
            loading =   { binding.progressBar.visible() },
            success = { list ->
                binding.progressBar.gone()
                adapter.submitList(list)
            },
            error = { msg ->
                binding.progressBar.gone()
                requireContext().showToast(msg)
            }
        )
    }
    override fun constructorListeners() = Unit
}




