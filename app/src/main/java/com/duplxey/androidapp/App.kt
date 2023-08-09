package com.duplxey.androidapp

import android.app.Application
import com.parse.Parse

class App : Application() {

    val notes = mutableMapOf<String, Note>()

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )

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
                    this.notes[note.objectId!!] = note
                }
            } else {
                println("Error: ${e.message}")
            }
        }
    }
}