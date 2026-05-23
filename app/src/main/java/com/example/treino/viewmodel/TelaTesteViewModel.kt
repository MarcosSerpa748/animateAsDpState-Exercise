package com.example.treino.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.treino.nav.routes.TelaTesteRoute
import com.example.treino.uistate.TelaTesteUIState
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
class TelaTesteViewModel@Inject constructor(private val savedStateHandle: SavedStateHandle):ViewModel(){

    private val _navegar = Channel<Unit>()
    val navegar = _navegar.receiveAsFlow()

    private val _transformar = MutableStateFlow(false)
    private val _valorRecebido = MutableStateFlow<Int?>(null)

    val valoresPassados = savedStateHandle.toRoute<TelaTesteRoute>()
    val valorConvertido = valoresPassados.tamanho.toIntOrNull()

    val uiState = _transformar.map {
        TelaTesteUIState(transformar = it, valorRecebido = valorConvertido)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TelaTesteUIState()
        )

    fun alterarEstado(valor: Boolean){
        _transformar.value = valor
    }

    fun lancarNavegacao(){
        viewModelScope.launch{
            _navegar.send(Unit)

            alterarEstado(false)
        }
    }
}