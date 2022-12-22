package org.maddiesoftware.komagareader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import org.maddiesoftware.komagareader.core.presentation.Navigation
import org.maddiesoftware.komagareader.ui.theme.KomagaReaderTheme
import org.maddiesoftware.komagareader.ui.theme.orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = orange.toArgb()
            window.navigationBarColor = orange.toArgb()
            KomagaReaderTheme {
                // A surface container using the 'background' color from the theme

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