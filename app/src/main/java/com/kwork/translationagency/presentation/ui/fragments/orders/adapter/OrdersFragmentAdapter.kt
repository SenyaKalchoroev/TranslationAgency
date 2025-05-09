package com.kwork.translationagency.presentation.ui.fragments.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.databinding.ItemOrdersBinding
import com.kwork.translationagency.presentation.model.clients.OrderUi

class OrdersAdapter :
    ListAdapter<OrderUi, OrdersAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val b: ItemOrdersBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: OrderUi) = with(b) {
            itemUserNameTv.text = item.userName
            itemNumberTv.text   = item.description
            itemOrderPrice.text = item.price
            itemFirstDate.text  = item.dateFrom
            itemSecondDate.text = item.dateTo
        }
    }

    class Diff : DiffUtil.ItemCallback<OrderUi>() {
        override fun areItemsTheSame(a: OrderUi, b: OrderUi) = a.id == b.id
        override fun areContentsTheSame(a: OrderUi, b: OrderUi) = a == b
    }
}
