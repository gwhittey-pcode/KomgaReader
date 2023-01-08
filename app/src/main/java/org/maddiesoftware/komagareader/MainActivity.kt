package org.maddiesoftware.komagareader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import org.maddiesoftware.komagareader.core.presentation.theme.KomgaReaderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor =  MaterialTheme.colors.primary.toArgb()
            window.navigationBarColor = MaterialTheme.colors.primary.toArgb()
            KomgaReaderTheme {
                // A surface container using the 'background' color from the theme
               DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    KomagaReaderTheme {
//        Greeting("Android")
//    }
//}