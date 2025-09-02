package com.surya.a2faforme

//import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.compose.ui.text.*
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.surya.a2faforme.ui.theme._2FAformeTheme


//Onc


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2FAformeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .background(MaterialTheme.colorScheme.background)
//                        .background(Color.Cyan)
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {  },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Icon(Icons.Filled.Add, "Floating action button.")
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(ScrollState(0))
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
//                            .background(Color.Cyan),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        arrayOfNulls<Byte>(15).forEach { _ ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Text(
                                    text = "title",
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .background(
//                                        Color(156, 39, 176, 255),
//                                        shape= RoundedCornerShape(100),
                                            shape = RoundedCornerShape(32.dp, 32.dp, 8.dp, 8.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    fontSize = 48.sp
                                )
                                Text(
//                                    text = "123456",
                                    text = (0..999999)
                                        .random()
                                        .toString()
                                        .padStart(6, '0')
                                        .replaceRange(3,3," "),
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .background(
//                                        Color(156, 39, 176, 255),
//                                        shape= RoundedCornerShape(100),
                                            shape= RoundedCornerShape(8.dp, 8.dp, 32.dp, 32.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    fontSize = 64.sp
                                )
                            }

                        }

                    }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier,
//        color = color,
//        textAlign = TextAlign.Center,
//        fontSize = 64.sp
//    )
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _2FAformeTheme {
//        Greeting("Android")
    }
}