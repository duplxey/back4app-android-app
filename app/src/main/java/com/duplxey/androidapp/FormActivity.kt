package com.duplxey.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class FormActivity : ComponentActivity() {

    private fun render(note: Note?) {
        setContent {
            val application = application as App

            FormActivityContent(
                note,
                onNoteAddClick = { icon: String, title: String, content: String ->
                    if (note !== null) return@FormActivityContent
                    val generatedObjectId = Note.generateObjectId()
                    application.notes[generatedObjectId] = Note(
                        objectId = generatedObjectId,
                        icon = icon,
                        title = title,
                        content = content,
                    )
                    finish()
                },
                onNoteSaveClick = { icon: String, title: String, content: String ->
                    if (note === null) return@FormActivityContent
                    note.icon = icon
                    note.title = title
                    note.content = content
                    finish()
                },
                onNoteDeleteClick = {
                    if (note === null) return@FormActivityContent
                    application.notes.remove(note.objectId)
                    finish()
                },
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appInstance = application as App

        val objectId = intent.getStringExtra("objectId")
        val note = if (objectId !== null) appInstance.notes[objectId] else null

        render(note)
    }
}

@Composable
fun FormActivityContent(
    note: Note?,
    onNoteAddClick: (icon: String, title: String, content: String) -> Unit,
    onNoteSaveClick: (icon: String, title: String, content: String) -> Unit,
    onNoteDeleteClick: () -> Unit,
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    if (note === null) {
                        Text("Add Note")
                    } else {
                        Text("Edit Note")
                    }
                })
            },
            floatingActionButton = {
                if (note !== null) {
                    ExtendedFloatingActionButton(
                        onClick = { onNoteDeleteClick() },
                        icon = { Icon(Icons.Filled.Delete, contentDescription = "Delete") },
                        text = { Text("Delete") },
                    )
                }
            },
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                NoteForm(note = note, onNoteSave = { icon, title, content ->
                    if (note === null) {
                        onNoteAddClick(icon, title, content)
                    } else {
                        onNoteSaveClick(icon, title, content)
                    }
                })
            }
        }
    }
}

@Composable
fun NoteForm(
    note: Note?,
    onNoteSave: (icon: String, title: String, content: String) -> Unit
) {
    var icon by remember { mutableStateOf(TextFieldValue(note?.icon ?: "")) }
    var title by remember { mutableStateOf(TextFieldValue(note?.title ?: "")) }
    var content by remember { mutableStateOf(TextFieldValue(note?.content ?: "")) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            label = { Text(text = "Icon") },
            value = icon,
            onValueChange = { icon = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            label = { Text(text = "Title") },
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            label = { Text(text = "Content") },
            value = content,
            onValueChange = { content = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNoteSave(icon.text, title.text, content.text) },
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}