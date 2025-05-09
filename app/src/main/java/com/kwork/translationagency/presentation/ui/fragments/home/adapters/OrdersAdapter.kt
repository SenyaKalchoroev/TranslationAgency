package com.kwork.translationagency.presentation.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.ItemOrdersBinding
import com.kwork.translationagency.presentation.model.clients.OrderUi

class OrdersAdapter :
    ListAdapter<OrderUi, OrdersAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(
        ItemOrdersBinding.inflate(LayoutInflater.from(p.context), p, false)
    )

    override fun onBindViewHolder(h: VH, i: Int) = h.bind(getItem(i))

    inner class VH(private val b: ItemOrdersBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(it: OrderUi) = with(b) {
            itemUserNameTv.text = it.userName
            itemNumberTv.text   = it.description
            itemFirstDate.text  = it.dateFrom
            itemSecondDate.text = it.dateTo

            statusNew.visibility = if (it.isNew) View.VISIBLE else View.GONE

            itemStatusOrder.text = it.orderStatus
            val (bg, txt) = if (it.orderStatus == "готов")
                R.color.green_transparent to R.color.green
            else
                R.color.yellow_transparent to R.color.yellow
            itemStatusOrder.setBackgroundResource(bg)
            itemStatusOrder.setTextColor(
                ContextCompat.getColor(root.context, txt)
            )
        }
    }
    class Diff : DiffUtil.ItemCallback<OrderUi>() {
        override fun areItemsTheSame(a: OrderUi, b: OrderUi) = a.id == b.id
        override fun areContentsTheSame(a: OrderUi, b: OrderUi) = a == b
    }
}
