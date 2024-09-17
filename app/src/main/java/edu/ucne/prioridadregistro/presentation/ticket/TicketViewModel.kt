package edu.ucne.prioridadregistro.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadregistro.data.local.entities.PrioridadEntity
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity
import edu.ucne.prioridadregistro.data.repository.PrioridadRepository
import edu.ucne.prioridadregistro.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.cliente.isNullOrBlank() || _uiState.value.asunto.isNullOrBlank() || _uiState.value.descripcion.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "El cliente, asunto y la descripción no pueden estar vacíos")
                }
            } else {
                ticketRepository.save(_uiState.value.toEntity())
                nuevo()
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                cliente = "",
                asunto = "",
                descripcion = "",
                errorMessage = null
            )
        }
    }

    fun select(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket?.ticketId,
                        cliente = ticket?.cliente ?: "",
                        asunto = ticket?.asunto ?: "",
                        descripcion = ticket?.descripcion ?: ""
                    )
                }
            }
        }
    }

    fun onClienteChange(cliente: String) {
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun delete() {
        viewModelScope.launch {
            ticketRepository.delete(_uiState.value.toEntity())
        }
    }
    fun onPrioridadChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTickets().collect { tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getPrioridades().collect { prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            }
        }
    }
}

data class TicketUiState(
    val ticketId: Int? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val fecha: Date? = null,
    val prioridadId: Int = 0,
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList(),
    val prioridades: List<PrioridadEntity> = emptyList()
)

fun TicketUiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    cliente = cliente,
    asunto = asunto,
    descripcion = descripcion,
    fecha = null,
    prioridadId = prioridadId
)


