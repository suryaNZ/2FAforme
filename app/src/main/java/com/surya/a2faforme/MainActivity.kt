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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.surya.a2faforme.ui.theme._2FAformeTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.text.get

// Navigation

@Serializable
object MainScreen
@Serializable
object AddNewCode
@Serializable
object EditCode




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



        val filesDir = filesDir

        setContent {
            MainNavHost(filesDir)
//            MainScreen(filesDir)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(filesDir:File, navController: NavController) {



    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context, options)

    val TOTP_DIR = File(filesDir.absolutePath + File.separator + "TOTP_FILES")

    _2FAformeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(AddNewCode) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "2FA For Me")
                    },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            navController.navigate(MainScreen)
//                        }) {
//                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
//                        }
//                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCode(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add new code")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(MainScreen)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }, content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Content of the page",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Composable
fun EditCode(navController: NavController) {
//    NavHost(navController = navController, startDestination = MainScreen) {
//        composable<MainScreen> { MainScreen(filesDir) }
//        composable<AddNewCode> { AddNewCode(  ) }
//        composable<EditCode> { EditCode(  ) }
//    }
}

@Composable
fun MainNavHost(filesDir: File) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen) {
        composable<MainScreen> { MainScreen(filesDir, navController) }
        composable<AddNewCode> { AddNewCode(navController) }
        composable<EditCode> { EditCode(navController) }
    }
}