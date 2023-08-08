package com.duplxey.androidapp

import android.app.Application
import android.util.Log
import com.parse.Parse
import com.parse.ParseObject

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )
        val testMessage = ParseObject("Message")
        testMessage.put("type", "debug")
        testMessage.put("message", "Yay, Android app has successfully connected to Back4app.")
        testMessage.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            } else {
                Log.d("MainActivity", "Object saved.")
            }
        }
    }
}