package com.duplxey.androidapp

import android.app.Application
import com.parse.Parse

class App : Application() {

    var notes = mutableMapOf(
        "7IggsqFAKt" to Note(
            objectId = "7IggsqFAKt",
            icon = "\uD83D\uDE80",
            title = "Deploy app",
            content = "Go ahead and deploy your backend to Back4app."
        ),
        "eFRNm0hTat" to Note(
            objectId = "eFRNm0hTat",
            icon = "\uD83C\uDFA8",
            title = "Design website",
            content = "Design the website for the conference."
        ),
        "uC7hTQmG5F" to Note(
            objectId = "uC7hTQmG5F",
            icon = "\uD83D\uDC42",
            title = "Attend the meeting",
            content = "Attend meeting with the team to discuss the conference."
        ),
    )

    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )
    }
}