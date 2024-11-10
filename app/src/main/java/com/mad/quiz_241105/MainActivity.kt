package com.mad.quiz_241105

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var kamalaVoters by remember { mutableStateOf("") }
            var trumpVoters by remember { mutableStateOf("") }
            var winner = remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    // .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Vote! Vote! Vote!",
                    style = MaterialTheme.typography.headlineLarge,
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = kamalaVoters,
                    onValueChange = { kamalaVoters = it },
                    label = { Text("Enter voter names for VP Kamala Harris") },
                    maxLines = 2,
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = trumpVoters,
                    onValueChange = { trumpVoters = it },
                    label = { Text("Enter names for Trump") },
                    maxLines = 2,
                )

                Button(
                    onClick = {
                        winner.value = countVotes(kamalaVoters, trumpVoters)
                    }
                ) {
                    Text("Click to see who won!")
                }

                if(winner.value != "" ) {
                    Text(
                        text = "And the winner is...",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    if (winner.value == "VP Kamala Harris") {
                        Text(
                            text = "Hooray, the winner is \n${winner.value}!",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Magenta
                        )
                    } else {
                        Text(
                            text = "The winner is \n${winner.value}!",
                        )
                    }
                }
            }
        }
    }

    private fun countVotes(kamala: String, trump: String): String {
        var kamalaVoters = kamala.split(", ")
        Log.d("XU", "kamala voters are $kamalaVoters")
        // like [kamala, doug, maya, donald, shyamala, meena, ella, cole]

        var trumpVoters = trump.split(", ")
        Log.d("XU", "trump voters are $trumpVoters")
        // like [melania, marla, kamala, barron, ivanka, donald, eric, donald]

        // TODO 1 - Only unique voters per candidate
        kamalaVoters = kamalaVoters.toSet().toList()
        Log.d("XU", "kamala unique voters are $kamalaVoters")

        trumpVoters = trumpVoters.toSet().toList()
        Log.d("XU", "trump unique voters are $trumpVoters")

        var numKamala = kamalaVoters.size
        var numTrump = trumpVoters.size

        // // TODO 3 - If "doug" voted for Trump, subtract 2 votes from Kamala.
        if (trumpVoters.contains("doug")) {
            numKamala -= 2
        }

        // TODO 4 - If "melania" voted for Kamala, add 4 votes to Kamala.
        if (kamalaVoters.contains("melania")) {
            numKamala += 4
        }

        // TODO 2 - If a candidate voted for their opponent, delete the last half of their voters.
        numKamala = if (trumpVoters.contains(kamala)) numKamala - trumpVoters.size / 2 else numKamala
        numTrump = if (kamalaVoters.contains(trump)) numTrump - kamalaVoters.size / 2 else numTrump

        // TODO 5 - Return the name of the candidate with the most votes, or if a tie return "".
        if (numKamala > numTrump) {
            return "VP Kamala Harris"
        } else if (numKamala == numTrump) {
            return "Tie"
        } else {
            return "Trump"
        }
    }
}

