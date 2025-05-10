package com.kwork.translationagency.presentation.ui.fragments.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.data.remote.model.ChatMessage
import com.kwork.translationagency.databinding.ItemChatReceivedBinding
import com.kwork.translationagency.databinding.ItemChatSentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(val myUid: String): ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DIFF) {
  companion object {
    val DIFF = object: DiffUtil.ItemCallback<ChatMessage>() {
      override fun areItemsTheSame(a: ChatMessage, b: ChatMessage) =
        a.messageId == b.messageId
      override fun areContentsTheSame(a: ChatMessage, b: ChatMessage) = a == b
    }
  }

  override fun getItemViewType(pos: Int) =
    if (getItem(pos).senderUid == myUid) 1 else 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    if (viewType == 1) SentVH(
      ItemChatSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) else ReceivedVH(
      ItemChatReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
    val msg = getItem(pos)
    (holder as? SentVH)?.bind(msg) ?: (holder as ReceivedVH).bind(msg)
  }

  inner class SentVH(val vb: ItemChatSentBinding): RecyclerView.ViewHolder(vb.root) {
    fun bind(m: ChatMessage) {
      vb.itemMsgTxt.text = m.text
      vb.itemTvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault())
        .format(Date(m.timestamp))
    }
  }
  inner class ReceivedVH(val vb: ItemChatReceivedBinding): RecyclerView.ViewHolder(vb.root) {
    fun bind(m: ChatMessage) {
      vb.itemMsgTxt.text = m.text
      vb.itemTvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault())
        .format(Date(m.timestamp))
    }
  }
}
