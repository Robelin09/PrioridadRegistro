package edu.ucne.prioridadregistro.presentation.sistema


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadregistro.data.remote.dto.SistemaDto
import edu.ucne.prioridadregistro.data.repository.SistemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(Uistate())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.nombredeSistema.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "El nombre del sistema no puede estar vacío")
                }
            } else {
                sistemaRepository.save(_uiState.value.toEntity())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                sistemaId = null,
                nombredeSistema = "",
                errorMessage = null
            )
        }
    }

    fun select(sistemaId: Int) {
        viewModelScope.launch {
            if (sistemaId > 0) {
                val sistema = sistemaRepository.getSistema(sistemaId)
                _uiState.update {
                    it.copy(
                        sistemaId = sistema?.sistemaId,
                        nombredeSistema = sistema?.nombredeSistema ?: ""
                    )
                }
            }
        }
    }

    fun onNombredeSistemaChange(nombre: String) {
        _uiState.update {
            it.copy(nombredeSistema = nombre)
        }
    }

    fun delete() {
        viewModelScope.launch {
            sistemaRepository.delete(_uiState.value.sistemaId!!)
            nuevo()
        }
    }

    private fun getSistemas() {
        viewModelScope.launch {
            val sistemas = sistemaRepository.getAllSistema()
            _uiState.update {
                it.copy(sistemas = sistemas)
            }
        }
    }
}

data class Uistate(
    val sistemaId: Int? = null,
    val nombredeSistema: String = "",
    val errorMessage: String? = null,
    val sistemas: List<SistemaDto> = emptyList()
)

fun Uistate.toEntity() = SistemaDto(
    sistemaId = sistemaId,
    nombredeSistema = nombredeSistema
)
