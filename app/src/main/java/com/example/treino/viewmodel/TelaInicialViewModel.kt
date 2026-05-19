package com.example.treino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treino.uistate.TelaInicialUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelaInicialViewModel@Inject constructor(): ViewModel(){

    private val _navegar = Channel<Int>()
    val navegar = _navegar.receiveAsFlow()

    private val _tamanho = MutableStateFlow("")

    val uiState = _tamanho.map {
        TelaInicialUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TelaInicialUiState()
    )
    fun alterarValor(valor:String){
        _tamanho.value = valor
    }

    fun lancarNavegacao(){

        viewModelScope.launch {
            val valorConvertido = _tamanho.value.toInt()
            _navegar.send(valorConvertido)
            alterarValor("")

        }
    }
}