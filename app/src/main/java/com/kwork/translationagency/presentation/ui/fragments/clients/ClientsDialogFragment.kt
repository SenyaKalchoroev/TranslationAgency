package com.kwork.translationagency.presentation.ui.fragments.clients

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.BottomSheetClientFragmentBinding
import com.kwork.translationagency.presentation.ui.fragments.clients.adapter.ClientAdapter
import com.kwork.translationagency.presentation.viewmodel.clients.ClientsBottomVm
import com.kwork.translationagency.presentation.viewmodel.clients.ClientsBottomVm.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientsDialogFragment : DialogFragment() {

    private var _binding: BottomSheetClientFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ClientsBottomVm>()
    private val adapter by lazy {
        ClientAdapter { clientUi ->
            parentFragmentManager.setFragmentResult(
                "clientSelect",
                bundleOf(
                    "clientName" to clientUi.name,
                    "clientId"   to clientUi.id
                )
            )
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext(), R.style.BottomSheetTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetClientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        rvClients.layoutManager = GridLayoutManager(requireContext(), 3)
        rvClients.adapter = adapter

        searchView.doAfterTextChanged { query ->
            viewModel.query.value = query?.toString().orEmpty()
        }

        filterAll .setOnClickListener { viewModel.setFilter(Filter.ALL) }
        filterNew .setOnClickListener { viewModel.setFilter(Filter.NEW) }
        filterWait.setOnClickListener { viewModel.setFilter(Filter.ACTIVE) }
        filterMore.setOnClickListener { viewModel.setFilter(Filter.WITH_ORDER) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiClients.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
