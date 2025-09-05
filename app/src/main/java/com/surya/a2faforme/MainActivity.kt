package com.surya.a2faforme

//import android.graphics.drawable.Icon
//import androidx.compose.ui.text.*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surya.a2faforme.ui.theme._2FAformeTheme
import java.io.File


//Onc


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val TOTP_DIR = File(filesDir.absolutePath + File.separator + "TOTP_FILES")

        setContent {
            _2FAformeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

//                            if(TOTP_DIR.)
//                        arrayOfNulls<Int>(15).forEach { _ ->
                        retrieveKeys(TOTP_DIR).forEach { keydata:Map<String, String> ->
//                            Log.d("FILE_FOUND", file.absolutePath)
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
//                                val text = (0..999999)
//                                    .random()
//                                    .toString()
//                                    .padStart(6, '0')
//                                    .replaceRange(3,3," ")
                                val text = generateTOTP(
                                    keydata["secret"]!!,
                                    System.currentTimeMillis(),
                                    keydata["period"]!!,
                                    keydata["digits"]!!,
                                    keydata["algorithm"]!!,
                                )
                                val clipboardManager = LocalClipboardManager.current
                                Text(
//                                    text = "123456",
                                    text = text,
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .background(
//                                        Color(156, 39, 176, 255),
//                                        shape= RoundedCornerShape(100),
                                            shape= RoundedCornerShape(8.dp, 8.dp, 32.dp, 32.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                        .clickable(
                                            onClick = {
                                                clipboardManager.setText(
                                                    AnnotatedString(
                                                        text
                                                            .replaceRange(3,3,"")
                                                    )
                                                )
                                                Toast.makeText(applicationContext, "copied to clipboard", Toast.LENGTH_SHORT,).show()

                                            }
                                        )
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    fontSize = 64.sp
                                )
                            }

                        }
//                        }

//                        arrayOfNulls<Byte>(15).forEach { _ ->
//                            Column(
//                                verticalArrangement = Arrangement.spacedBy(1.dp)
//                            ) {
//                                Text(
//                                    text = "title",
//                                    modifier = Modifier
//                                        .padding(1.dp)
//                                        .background(
////                                        Color(156, 39, 176, 255),
////                                        shape= RoundedCornerShape(100),
//                                            shape = RoundedCornerShape(32.dp, 32.dp, 8.dp, 8.dp),
//                                            color = MaterialTheme.colorScheme.primaryContainer,
//                                        )
//                                        .fillMaxWidth(),
//                                    color = MaterialTheme.colorScheme.primary,
//                                    textAlign = TextAlign.Center,
//                                    fontSize = 48.sp
//                                )
//                                val text = (0..999999)
//                                    .random()
//                                    .toString()
//                                    .padStart(6, '0')
//                                    .replaceRange(3,3," ")
//                                val clipboardManager = LocalClipboardManager.current
//                                Text(
////                                    text = "123456",
//                                    text = text,
//                                    modifier = Modifier
//                                        .padding(1.dp)
//                                        .background(
////                                        Color(156, 39, 176, 255),
////                                        shape= RoundedCornerShape(100),
//                                            shape= RoundedCornerShape(8.dp, 8.dp, 32.dp, 32.dp),
//                                            color = MaterialTheme.colorScheme.primaryContainer,
//                                        )
//                                        .clickable(
//                                            onClick = {
//                                                clipboardManager.setText(
//                                                    AnnotatedString(
//                                                        text
//                                                            .replaceRange(3,3,"")
//                                                    )
//                                                )
//                                                Toast.makeText(applicationContext, "copied to clipboard", Toast.LENGTH_SHORT,).show()
//
//                                            }
//                                        )
//                                        .fillMaxWidth(),
//                                    color = MaterialTheme.colorScheme.primary,
//                                    textAlign = TextAlign.Center,
//                                    fontSize = 64.sp
//                                )
//                            }
//
//                        }

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