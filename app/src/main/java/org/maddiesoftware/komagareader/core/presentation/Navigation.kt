package org.maddiesoftware.komagareader.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = ScreenRoutes.ServerScreen.route
    ){
        composable(ScreenRoutes.ServerScreen.route){
//            ScreenRoutes.ServerListScreen(navController=navController)
        }
    }

}
sealed class ScreenRoutes(val route:String){
    object ServerScreen:ScreenRoutes("server_screen")

}