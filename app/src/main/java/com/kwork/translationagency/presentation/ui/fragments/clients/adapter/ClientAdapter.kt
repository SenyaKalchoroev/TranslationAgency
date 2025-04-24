package com.kwork.translationagency.presentation.ui.fragments.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.core.utils.Extensions.loadImageURL
import com.kwork.translationagency.databinding.ItemClientBinding
import com.kwork.translationagency.presentation.model.clients.ClientsUi

class ClientAdapter(
    private val click: (ClientsUi) -> Unit
) : ListAdapter<ClientsUi, ClientAdapter.Holder>(Diff()) {

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = Holder(
        ItemClientBinding.inflate(LayoutInflater.from(p.context), p, false)
    )

    override fun onBindViewHolder(h: Holder, pos: Int) =
        h.bind(getItem(pos))

    inner class Holder(private val b: ItemClientBinding)
        : RecyclerView.ViewHolder(b.root) {

        fun bind(ui: ClientsUi) = with(b) {
            itemUserName.text = ui.name
            itemUserLogin.text = ui.nickname
            itemUserImage.loadImageURL(ui.avatarUrl)
            root.isSelected = ui.isChecked
            root.setOnClickListener { click(ui) }
        }
    }
    class Diff : DiffUtil.ItemCallback<ClientsUi>() {
        override fun areItemsTheSame(o: ClientsUi, n: ClientsUi) = o.id == n.id
        override fun areContentsTheSame(o: ClientsUi, n: ClientsUi) = o == n
    }
}

