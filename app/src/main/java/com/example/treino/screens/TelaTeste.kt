package com.example.treino.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.treino.viewmodel.TelaTesteViewModel

@Composable
fun TelaTeste(pegarNavegacao:(Unit) -> Unit,viewModel: TelaTesteViewModel = hiltViewModel()){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val valorAnimado by animateDpAsState(
        targetValue = if (uiState.transformar) uiState.valorRecebido!!.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "Animação"
    )
    Column(Modifier.fillMaxSize()){
        Button(onClick = { viewModel.lancarNavegacao() },
            Modifier.padding(start = 10.dp,top = 30.dp)){

            Icon(imageVector = Icons.Filled.ArrowBack,contentDescription = null)
        }
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally){

            Box(
                Modifier
                    .height(valorAnimado)
                    .width(50.dp)
                    .background(Color.Gray)){ }

            Spacer(Modifier.height(200.dp))
        }

        LaunchedEffect(Unit){
            viewModel.alterarEstado(true)
        }
        LaunchedEffect(Unit){
            viewModel.navegar.collect {
                pegarNavegacao(it)
            }
        }
    }
}