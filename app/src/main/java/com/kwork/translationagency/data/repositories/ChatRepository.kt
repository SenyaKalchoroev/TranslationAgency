package com.kwork.translationagency.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.kwork.translationagency.data.remote.model.ChatMessage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:   FirebaseDatabase
) {
    private val me get() = auth.currentUser!!

    private fun makeChatId(otherUid: String): String {
        val uids = listOf(me.uid, otherUid).sorted()
        return "${uids[0]}_${uids[1]}"
    }

    fun streamMessages(otherUid: String): Flow<List<ChatMessage>> = callbackFlow {
        val chatId = makeChatId(otherUid)
        val ref = db.getReference("chats/$chatId/messages")
        val buffer = mutableListOf<ChatMessage>()
        val listener = object: ChildEventListener {
            override fun onChildAdded(s: DataSnapshot, prev: String?) {
                s.getValue(ChatMessage::class.java)?.let {
                    buffer += it.copy(messageId = s.key!!)
                    trySend(buffer.toList())
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(e: DatabaseError) { close(e.toException()) }
            
        }
        ref.addChildEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    suspend fun sendMessage(otherUid: String, text: String) {
        val chatId = makeChatId(otherUid)
        val chatRef = db.getReference("chats/$chatId")

        val members = mapOf(me.uid to true, otherUid to true)
        chatRef.child("members").setValue(members).await()

        val msgRef = chatRef.child("messages").push()
        val msg = ChatMessage(
            messageId = msgRef.key!!,
            senderUid = me.uid,
            text      = text,
            timestamp = System.currentTimeMillis()
        )
        msgRef.setValue(msg).await()
    }
}
