package com.surya.a2faforme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.surya.a2faforme.ui.theme._2FAformeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2FAformeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(ScrollState(0)),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                                .background(Color(156, 39, 176, 255)),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _2FAformeTheme {
        Greeting("Android")
    }
}