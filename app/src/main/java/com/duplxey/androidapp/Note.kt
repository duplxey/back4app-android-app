package com.duplxey.androidapp

import com.parse.ParseObject
import com.parse.ParseQuery

data class Note(
    var objectId: String? = null,
    var icon: String,
    var title: String,
    var content: String,
) {
    fun addToParse(callback: (objectId: String) -> Unit) {
        if (objectId !== null) throw Exception("Note is already saved to Parse!")

        val parseNote = ParseObject("Note")
        parseNote.put("icon", icon)
        parseNote.put("title", title)
        parseNote.put("content", content)

        parseNote.saveInBackground {
            if (it !== null) throw Exception("Error: ${it.message}")
            objectId = parseNote.objectId
            callback(parseNote.objectId)
        }
    }

    fun updateToParse(callback: (objectId: String) -> Unit) {
        if (objectId === null) throw Exception("Note hasn't been saved to Parse yet!")

        val query = ParseQuery.getQuery<ParseObject>("Note")
        val parseNote = query.get(objectId)
        parseNote.put("icon", icon)
        parseNote.put("title", title)
        parseNote.put("content", content)

        parseNote.saveInBackground {
            if (it !== null) throw Exception("Error: ${it.message}")
            callback(parseNote.objectId)
        }
    }

    fun deleteFromParse(callback: () -> Unit) {
        if (objectId === null) throw Exception("Note hasn't been saved to Parse yet!")

        val query = ParseQuery.getQuery<ParseObject>("Note")
        val parseNote = query.get(objectId)
        parseNote.deleteInBackground {
            if (it !== null) throw Exception("Error: ${it.message}")
            callback()
        }
    }
}
