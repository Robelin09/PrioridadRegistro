package edu.ucne.prioridadregistro.presentation.cliente


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadregistro.data.remote.dto.ClienteDto
import edu.ucne.prioridadregistro.data.repository.ClienteRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(Uistate())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.nombres.isNullOrBlank() || _uiState.value.telefono.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "El nombre y el teléfono no pueden estar vacíos")
                }
            } else {
                clienteRepository.save(_uiState.value.toEntity())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                clienteId = null,
                nombres = "",
                telefono = "",
                celular = "",
                rnc = "",
                email = "",
                direccion = "",
                errorMessage = null
            )
        }
    }

    fun select(clienteId: Int) {
        viewModelScope.launch {
            if (clienteId > 0) {
                val cliente = clienteRepository.getCliente(clienteId)
                _uiState.update {
                    it.copy(
                        clienteId = cliente?.clienteId,
                        nombres = cliente?.nombres ?: "",
                        telefono = cliente?.telefono ?: "",
                        celular = cliente?.celular ?: "",
                        rnc = cliente?.rnc ?: "",
                        email = cliente?.email ?: "",
                        direccion = cliente?.direccion ?: ""
                    )
                }
            }
        }
    }

    fun onNombresChange(nombres: String) {
        _uiState.update {
            it.copy(nombres = nombres)
        }
    }

    fun onTelefonoChange(telefono: String) {
        _uiState.update {
            it.copy(telefono = telefono)
        }
    }

    fun onCelularChange(celular: String) {
        _uiState.update {
            it.copy(celular = celular)
        }
    }

    fun onRncChange(rnc: String) {
        _uiState.update {
            it.copy(rnc = rnc)
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onDireccionChange(direccion: String) {
        _uiState.update {
            it.copy(direccion = direccion)
        }
    }

    fun delete() {
        viewModelScope.launch {
            clienteRepository.delete(_uiState.value.clienteId!!)
            nuevo()
        }
    }

    private fun getClientes() {
        viewModelScope.launch {
            val clientes = clienteRepository.getClientes()
            _uiState.update {
                it.copy(clientes = clientes)
            }
        }
    }
}

data class Uistate(
    val clienteId: Int? = null,
    val nombres: String = "",
    val telefono: String = "",
    val celular: String = "",
    val rnc: String = "",
    val email: String = "",
    val direccion: String = "",
    val errorMessage: String? = null,
    val clientes: List<ClienteDto> = emptyList()
)

fun Uistate.toEntity() = ClienteDto(
    clienteId = clienteId,
    nombres = nombres,
    telefono = telefono,
    celular = celular,
    rnc = rnc,
    email = email,
    direccion = direccion
)
