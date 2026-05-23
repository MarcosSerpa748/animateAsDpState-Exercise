package com.example.treino.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.treino.viewmodel.TelaInicialViewModel

@Composable
fun TelaIncial(pegarNavegacao:(String) -> Unit,viewModel: TelaInicialViewModel = hiltViewModel()){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = uiState.mensagem ?: "", color = Color.Red, fontWeight = FontWeight.ExtraBold)

        OutlinedTextField(
            value = uiState.valor,
            onValueChange = {viewModel.alterarValor(it)},
            label = {Text(text = "Digire um valor:")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword))

        Button(onClick = { viewModel.lancarNavegacao() })
        {
            Text(text = "Próxima tela")
            Icon(imageVector = Icons.Filled.ArrowForward,contentDescription = null)
        }

        LaunchedEffect(Unit){
            viewModel.navegar.collect {
                pegarNavegacao(uiState.valor)
            }
        }
    }
}