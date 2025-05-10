package com.kwork.translationagency.presentation.viewmodel.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwork.translationagency.data.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
  private val repo: ChatRepository,
  savedState: SavedStateHandle
): ViewModel() {
  private val otherUid = checkNotNull(savedState["otherUid"])
  val messages = repo.streamMessages(otherUid.toString())
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  fun send(text: String) = viewModelScope.launch {
    repo.sendMessage(otherUid.toString(), text)
  }
}
