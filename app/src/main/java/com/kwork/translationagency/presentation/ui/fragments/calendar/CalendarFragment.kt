package com.kwork.translationagency.presentation.ui.fragments.calendar

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.Extensions.collectUi
import com.kwork.translationagency.core.utils.Extensions.isVisible
import com.kwork.translationagency.core.utils.Extensions.onClick
import com.kwork.translationagency.core.utils.Extensions.toast
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentCalendarBinding
import com.kwork.translationagency.databinding.FragmentProfileBinding
import com.kwork.translationagency.presentation.ui.fragments.home.adapters.OrdersAdapter
import com.kwork.translationagency.presentation.viewmodel.calendar.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>(
    R.layout.fragment_calendar
) {
    override val binding by viewBinding(FragmentCalendarBinding::bind)
    override val viewModel by viewModels<CalendarViewModel>()
    private val adapter = OrdersAdapter()
    private var currentDateStr = ""

    @SuppressLint("SetTextI18n")
    override fun init() = with(binding) {
        orderRv.layoutManager = LinearLayoutManager(requireContext())
        orderRv.adapter = adapter

        backBtn.onClick { findNavController().popBackStack() }

        newOrderBtn.onClick {
            val action = CalendarFragmentDirections
                .actionCalendarFragmentToNewOrderFragment(currentDateStr)
            findNavController().navigate(action)
        }

        calendarView.setOnDateChangeListener { _, y, m, d ->
            currentDateStr = "%02d.%02d.%04d"
                .format(d, m + 1, y)
            tvOrders.text = "Заказы $currentDateStr:"
            viewModel.load(currentDateStr)
        }

        calendarView.date.let {
            val cal = Calendar.getInstance().apply { timeInMillis = it }
            currentDateStr = "%02d.%02d.%04d"
                .format(cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR))
            tvOrders.text = "Заказы $currentDateStr:"
            viewModel.load(currentDateStr)
        }
    }

    override fun launchObserver() {
        viewModel.orders.collectUi(
            owner = viewLifecycleOwner,
            onLoading = { binding.progressBar.isVisible = true },
            onSuccess = {
                binding.progressBar.isVisible = false
                adapter.submitList(it)
            },
            onError = { toast(it) }
        )
    }


}
