package com.example.treino.nav.controller

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.treino.nav.routes.TelaInicialRoute
import com.example.treino.nav.routes.TelaTesteRoute
import com.example.treino.screens.TelaTeste
import com.example.treino.screens.TelaIncial

@Composable
fun NavController(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = TelaInicialRoute){
        composable<TelaInicialRoute>{
            TelaIncial(pegarNavegacao = {
                navController.navigate(TelaTesteRoute(it))
            })
        }
        composable<TelaTesteRoute>{
            TelaTeste(pegarNavegacao = {
                navController.navigate(TelaInicialRoute){
                    popUpTo(TelaInicialRoute){
                        inclusive = true
                    }
                }
            })
        }
    }
}