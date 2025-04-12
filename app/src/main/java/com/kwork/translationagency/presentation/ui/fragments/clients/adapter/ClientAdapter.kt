package com.kwork.translationagency.presentation.ui.fragments.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.databinding.ItemClientBinding
import com.kwork.translationagency.domain.model.ClientModel

class ClientsAdapter(
    private val clients: List<ClientModel>
) : RecyclerView.Adapter<ClientsAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val binding = ItemClientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    override fun getItemCount(): Int = clients.size

    inner class ClientViewHolder(private val binding: ItemClientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(client: ClientModel) {
            binding.itemUserImage.setImageResource(client.avatarResId)
            binding.itemUserName.text = client.name
            binding.itemUserLogin.text = client.nickname
            binding.filterNew.text = client.phone

            // binding.itemHomeBtn.setOnClickListener { /* Действие при клике */ }
            // binding.itemChatBtn.setOnClickListener { /* Действие при клике */ }
            // binding.itemSettingsBtn.setOnClickListener { /* Действие при клике */ }
        }
    }
}
