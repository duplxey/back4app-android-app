package com.duplxey.androidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private fun render() {
        setContent {
            val application = application as App
            val context = this as Context

            MainActivityContent(
                application.notes,
                onNoteListItemClick = {
                    val intent = Intent(context, FormActivity::class.java)
                    intent.putExtra("objectId", it.objectId)
                    context.startActivity(intent)
                },
                onNoteAddClick = {
                    val intent = Intent(context, FormActivity::class.java)
                    context.startActivity(intent)
                },
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        render()
    }

    override fun onRestart() {
        super.onRestart()
        render()
    }
}

@Composable
fun MainActivityContent(
    notes: MutableMap<String, Note>,
    onNoteListItemClick: (note: Note) -> Unit,
    onNoteAddClick: () -> Unit,
) {
    MaterialTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("My Notes") }) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { onNoteAddClick() },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    text = { Text("Add") }
                )
            },
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                NoteList(notes, onNoteListItemClick = { onNoteListItemClick(it) })
            }
        }
    }
}

@Composable
fun NoteListItem(note: Note, onNoteListItemClick: (note: Note) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onNoteListItemClick(note) })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = note.icon, fontSize = 32.sp, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = note.title, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun NoteList(notes: Map<String, Note>, onNoteListItemClick: (note: Note) -> Unit) {
    LazyColumn {
        items(notes.entries.toList()) { (_, note) ->
            NoteListItem(note = note, onNoteListItemClick = onNoteListItemClick)
        }
    }
}
