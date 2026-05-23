package com.example.treino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treino.uistate.TelaInicialUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelaInicialViewModel@Inject constructor(): ViewModel(){

    private val _navegar = Channel<Unit>()
    val navegar = _navegar.receiveAsFlow()

    private val _valor = MutableStateFlow("")
    private val _mensagem = MutableStateFlow<String?>(null)

    val uiState = combine(
        _valor,
        _mensagem
    ){valor,mensagem ->
        TelaInicialUiState(valor = valor, mensagem = mensagem)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TelaInicialUiState()
    )
    fun alterarValor(valor:String){
        _valor.value = valor
    }

    fun lancarNavegacao(){
        if (_valor.value.isBlank()){
            _mensagem.value = "Campo vazio"
            return
        }else{
            viewModelScope.launch {
                _navegar.send(Unit)

                alterarValor("")
                _mensagem.value = null
            }
        }
    }
}