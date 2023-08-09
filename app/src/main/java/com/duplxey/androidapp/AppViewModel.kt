package com.duplxey.androidapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    val notes: MutableState<Map<String, Note>> = mutableStateOf(mapOf())

    init {
        val query = com.parse.ParseQuery.getQuery<com.parse.ParseObject>("Note")
        query.orderByDescending("createdAt")
        query.findInBackground { notes, e ->
            if (e == null) {
                for (parseNote in notes) {
                    val note = Note(
                        objectId = parseNote.objectId,
                        icon = parseNote.getString("icon")!!,
                        title = parseNote.getString("title")!!,
                        content = parseNote.getString("content")!!,
                    )
                    this.notes.value += (note.objectId!! to note)
                }
            } else {
                println("Error: ${e.message}")
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AppViewModel? = null

        fun getInstance(): AppViewModel {
            return instance ?: synchronized(this) {
                instance ?: AppViewModel().also { instance = it }
            }
        }
    }
}