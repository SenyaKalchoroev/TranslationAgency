package com.kwork.translationagency.presentation.ui.fragments.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.databinding.ItemOrdersBinding
import com.kwork.translationagency.domain.model.OrderModel

class SearchAdapter(
    private val orders: List<OrderModel>
) : RecyclerView.Adapter<SearchAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size

    class OrdersViewHolder(private val binding: ItemOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel) {
            binding.itemUserNameTv.text = order.userName
            binding.itemNumberTv.text = order.description
            binding.itemOrderPrice.text = order.price
            binding.itemFirstDate.text = order.dateFrom
            binding.itemSecondDate.text = order.dateTo
        }
    }
}