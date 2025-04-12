package com.kwork.translationagency.presentation.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.databinding.ItemMessagesBinding
import com.kwork.translationagency.domain.model.MessageModel

class MessagesAdapter(
    private val messages: List<MessageModel>
) : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val binding = ItemMessagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    class MessagesViewHolder(private val binding: ItemMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageModel) {
            binding.itemUserName.text = message.userName
            binding.itemDescMsg.text = message.messageText
            binding.itemTime.text = message.date
        }
    }
}
