package org.maddiesoftware.komagareader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.maddiesoftware.komagareader.core.presentation.KomgaReaderMainScreen
import org.maddiesoftware.komagareader.core.presentation.theme.KomgaReaderTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

//
            KomgaReaderTheme {
                // A surface container using the 'background' color from the theme
                KomgaReaderMainScreen()

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