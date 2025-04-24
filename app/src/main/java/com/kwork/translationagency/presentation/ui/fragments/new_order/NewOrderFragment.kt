package com.kwork.translationagency.presentation.ui.fragments.new_order

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.Extensions.gone
import com.kwork.translationagency.core.utils.Extensions.plusDaysString
import com.kwork.translationagency.core.utils.Extensions.showToast
import com.kwork.translationagency.core.utils.Extensions.visible
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.data.remote.model.OrderCreateDto
import com.kwork.translationagency.databinding.FragmentNewOrderBinding
import com.kwork.translationagency.presentation.ui.fragments.clients.ClientsDialogFragment
import com.kwork.translationagency.presentation.viewmodel.orders.NewOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOrderFragment : BaseFragment<FragmentNewOrderBinding, NewOrderViewModel>(
    R.layout.fragment_new_order
) {
    override val binding by viewBinding(FragmentNewOrderBinding::bind)
    override val viewModel by viewModels<NewOrderViewModel>()

    private val langs    = listOf("рус", "англ", "исп", "тур", "китай", "инди")
    private val statuses = listOf("новый заказ", "в процессе", "готов")

    override fun init() = with(binding) {
        spinnerFrom.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, langs)
        spinnerTo  .adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, langs)
        secondSpinnerFrom.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf(0L, 1L, 2L).map(::plusDaysString)
        )
        secondSpinnerTo.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf(7L, 8L, 9L).map(::plusDaysString)
        )
        statusMenu.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses)

        parentFragmentManager.setFragmentResultListener("clientSelect", this@NewOrderFragment) { _, bundle ->
            val name = bundle.getString("clientName").orEmpty()
            val id   = bundle.getString("clientId").orEmpty()
            itemClientName.text = name
            itemClientName.visible()
            itemAddClient.gone()
            tvClient.tag = id
        }

        itemAddClient.setOnClickListener {
            ClientsDialogFragment()
                .show(parentFragmentManager, "clients_bottom")
        }

        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSave.setOnClickListener {
            saveOrder()
        }
    }

    private fun saveOrder() = with(binding) {
        val clientId = tvClient.tag as? String
        if (clientId.isNullOrEmpty()) {
            showToast("Пожалуйста, выберите клиента")
            return
        }

        val dto = OrderCreateDto(
            clientId    = clientId,
            username    = itemClientName.text.toString(),
            description = searchView.text.toString().trim(),
            price       = searchView.text.toString().trim(),
            dateFrom    = secondSpinnerFrom.selectedItem.toString(),
            dateTo      = secondSpinnerTo.selectedItem.toString(),
            langFrom    = spinnerFrom.selectedItem.toString(),
            langTo      = spinnerTo.selectedItem.toString(),
            status      = statusMenu.selectedItem.toString() == "новый заказ",
            orderStatus = statusMenu.selectedItem.toString()
        )

        viewModel.save(dto)
    }

    override fun launchObserver() {
        viewModel.state.observeUIState(
            loading =    { binding.progressBar.visible() },
            success =   {
                binding.progressBar.gone()
                showToast("Заказ сохранён")
                findNavController().popBackStack()
            },
            error = { msg ->
                binding.progressBar.gone()
                showToast(msg)
            }
        )
    }

    override fun constructorListeners() = Unit
}
