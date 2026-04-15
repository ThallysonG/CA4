package com.example.ca4_mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ca4_mobileapp.ui.theme.CA4_MobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA4_MobileAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CA4App(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CA4App(modifier: Modifier = Modifier) {
    val playList = remember {
        mutableStateListOf(
            Song(
                title = "Bad Penny",
                artist = "Rory Gallagher",
                year = "1977",
                description = "Bad Penny is a blues rock song by Rory Gallagher. It is known for its energetic guitar style and strong rhythm.",
                imageRes = R.drawable.bad_penny
            ),
            Song(
                title = "Exit Song",
                artist = "Radiohead",
                year = "1997",
                description = "Exit Song is a slow and emotional song by Radiohead. It is known for its dark atmosphere and expressive vocals.",
                imageRes = R.drawable.exit_song
            ),
            Song(
                title = "Enter Sandman",
                artist = "Metallica",
                year = "1991",
                description = "Enter Sandman is one of Metallica's most famous songs and helped bring heavy metal to a wider audience.",
                imageRes = null
            ),
            Song(
                title = "Silver in Blood",
                artist = "Neurosis",
                year = "1996",
                description = "Silver in Blood is a powerful experimental metal track by Neurosis with a dark and intense sound.",
                imageRes = null
            ),
            Song(
                title = "Smells Like Teen Spirit",
                artist = "Nirvana",
                year = "1991",
                description = "Smells Like Teen Spirit became one of the defining songs of the grunge era and made Nirvana globally famous.",
                imageRes = null
            )
        )
    }

    var selectedSong by remember { mutableStateOf<Song?>(null) }

    if (selectedSong == null) {
        SongListScreen(
            songs = playList,
            modifier = modifier,
            onViewDetails = { song ->
                selectedSong = song
            }
        )
    } else {
        SongDetailScreen(
            song = selectedSong!!,
            modifier = modifier,
            onBackClick = {
                selectedSong = null
            }
        )
    }
}

@Composable
fun SongListScreen(
    songs: List<Song>,
    modifier: Modifier = Modifier,
    onViewDetails: (Song) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Play List",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        itemsIndexed(songs) { index, song ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Title: ${song.title}")
                        Text("Artist: ${song.artist}")
                        Text("Year: ${song.year}")
                    }

                    if (index == 0 || index == 1) {
                        Button(
                            onClick = { onViewDetails(song) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                        ) {
                            Text("View Details", color = Color.White)
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Extra Feature: Two songs include a detail page with image support.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SongDetailScreen(
    song: Song,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Song Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Title: ${song.title}", style = MaterialTheme.typography.titleLarge)
        Text("Artist: ${song.artist}")
        Text("Year: ${song.year}")

        Spacer(modifier = Modifier.height(16.dp))

        if (song.imageRes != null) {
            Image(
                painter = painterResource(id = song.imageRes),
                contentDescription = song.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = song.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(onClick = onBackClick) {
            Text("Back")
        }
    }
}