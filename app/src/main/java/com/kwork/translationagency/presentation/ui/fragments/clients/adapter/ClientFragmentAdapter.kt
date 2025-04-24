package com.kwork.translationagency.presentation.ui.fragments.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.core.utils.Extensions.loadImageURL
import com.kwork.translationagency.core.utils.Extensions.onClick
import com.kwork.translationagency.databinding.ItemClientBinding
import com.kwork.translationagency.presentation.model.clients.ClientsUi

class ClientFragmentAdapter(
    private val onHomeClick: (ClientsUi)->Unit
) : ListAdapter<ClientsUi, ClientFragmentAdapter.Holder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemClientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, pos: Int) =
        holder.bind(getItem(pos))

    inner class Holder(private val b: ItemClientBinding)
        : RecyclerView.ViewHolder(b.root) {

        fun bind(ui: ClientsUi) = with(b) {
            itemUserName.text  = ui.name
            itemUserLogin.text = ui.nickname
            itemUserImage.loadImageURL(ui.avatarUrl)

            itemHomeBtn.onClick {
                onHomeClick(ui)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<ClientsUi>() {
        override fun areItemsTheSame(o: ClientsUi, n: ClientsUi) = o.id == n.id
        override fun areContentsTheSame(o: ClientsUi, n: ClientsUi) = o == n
    }
}



