package com.surya.a2faforme

//import android.graphics.drawable.Icon
//import androidx.compose.ui.text.*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.surya.a2faforme.ui.theme._2FAformeTheme
import kotlinx.coroutines.delay
import java.io.File
import kotlin.text.get


//Onc
val options = GmsBarcodeScannerOptions.Builder()
    .setBarcodeFormats(
        Barcode.FORMAT_QR_CODE,
        )
    .enableAutoZoom()
    .build()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val scanner = GmsBarcodeScanning.getClient(this, options)

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

                        retrieveKeys(TOTP_DIR).forEach { keydata:Map<String, String> ->
//                            Log.d("FILE_FOUND", file.absolutePath)
                            TotpEntry(keydata)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TotpEntry(keydata: Map<String, String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        val scrollState = rememberScrollState()
        val context = LocalContext.current

        LaunchedEffect(key1 = keydata["label"]) {
            while (true) {
                val max = scrollState.maxValue
                if (max > 0) {
                    scrollState.animateScrollTo(
                        max,
                        tween(durationMillis = (keydata["label"]!!.length-12)*125, easing = LinearEasing)
                        )
                    delay(1000)
                    scrollState.animateScrollTo(0)
                    delay(2000)
                } else {
                    delay(500)
                }
            }
        }

        Text(
            text = keydata["label"]!!,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    shape = RoundedCornerShape(32.dp, 32.dp, 8.dp, 8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            color = MaterialTheme.colorScheme.primary,
            textAlign = if(scrollState.maxValue == 0) TextAlign.Center else TextAlign.Start,
            fontSize = 48.sp,
            softWrap = false,
            overflow = TextOverflow.Visible
        )
        val text = generateTOTP(
            keydata["secret"]!!,
            System.currentTimeMillis(),
            keydata["period"]!!,
            keydata["digits"]!!,
            keydata["algorithm"]!!,
        )
        val clipboardManager = LocalClipboardManager.current
        Text(
            text = text,
            modifier = Modifier
                .padding(1.dp)
                .background(
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
                        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT,).show()

                    }
                )
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 64.sp
        )
    }
}


