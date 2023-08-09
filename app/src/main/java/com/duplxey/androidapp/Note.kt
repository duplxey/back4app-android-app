package com.duplxey.androidapp

data class Note(
    val objectId: String,
    var icon: String,
    var title: String,
    var content: String,
) {
    companion object {
        fun generateObjectId(): String {
            val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return (1..10).map { chars.random() }.joinToString("")
        }
    }
}
