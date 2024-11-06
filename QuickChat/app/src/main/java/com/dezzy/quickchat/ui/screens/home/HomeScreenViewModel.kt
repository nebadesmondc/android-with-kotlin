package com.dezzy.quickchat.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dezzy.quickchat.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {
    private val database = Firebase.database
    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels() {
        database.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach(
                action = { data ->
                    val channel = Channel(data.key!!, data.value.toString())
                    list.add(channel)
                }
            )
            _channels.value = list

            val channelsString = list.joinToString(separator = ", ") { it.name }
            Log.i("HomeViewModel", channelsString)
        }
    }

    fun addChannel(name: String) {
        val key = database.getReference("channel").push().key
        database.getReference("channel").child(key!!).setValue(name).addOnSuccessListener {
            getChannels()
        }
    }
}