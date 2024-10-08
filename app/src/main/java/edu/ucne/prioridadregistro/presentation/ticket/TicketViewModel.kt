package edu.ucne.prioridadregistro.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadregistro.data.remote.dto.ClienteDto
import edu.ucne.prioridadregistro.data.remote.dto.PrioridadDto
import edu.ucne.prioridadregistro.data.remote.dto.SistemaDto
import edu.ucne.prioridadregistro.data.remote.dto.TicketDto
import edu.ucne.prioridadregistro.data.repository.ClienteRepository
import edu.ucne.prioridadregistro.data.repository.PrioridadRepository
import edu.ucne.prioridadregistro.data.repository.SistemaRepository
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
    private val prioridadRepository: PrioridadRepository,
    private val clienteRepository: ClienteRepository,
    private val sistemaRepository: SistemaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
        getClientes()
        getSistemas()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.asunto.isNullOrBlank() || _uiState.value.descripcion.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "El asunto y la descripción no pueden estar vacíos")
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
                solicitadoPor = "",
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
                        solicitadoPor = ticket?.solicitadoPor ?: "",
                        asunto = ticket?.asunto ?: "",
                        descripcion = ticket?.descripcion ?: ""
                    )
                }
            }
        }
    }

    fun onClienteChange(clienteId: Int) {
        _uiState.update {
            it.copy(clienteId = clienteId)
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
            ticketRepository.delete(_uiState.value.ticketId!!)
            nuevo()
        }
    }

    fun onPrioridadChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

    fun onFechaChange(fecha: Date) {
        _uiState.update {
            it.copy(fecha = fecha)
        }
    }

    fun onSolicitadoPorChange(solicitadoPor: String) {
        _uiState.update {
            it.copy(solicitadoPor = solicitadoPor)
        }
    }

    fun onSistemaChange(sistemaId: Int) {
        _uiState.update {
            it.copy(sistemaId = sistemaId)
        }
    }



    private fun getTickets() {
        viewModelScope.launch {
            val tickets = ticketRepository.getTickets()
            _uiState.update {
                it.copy(tickets = tickets)
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            val prioridades = prioridadRepository.getPrioridades()
            _uiState.update {
                it.copy(prioridades = prioridades)
            }
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

    private fun getSistemas() {
        viewModelScope.launch {
            val sistemas = sistemaRepository.getAllSistema()
            _uiState.update {
                it.copy(sistemas = sistemas)
            }
        }
    }
}

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date? = Date(),
    val clienteId: Int = 0,
    val sistemaId: Int = 0,
    val prioridadId: Int = 0,
    val solicitadoPor: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val errorMessage: String? = null,
    val tickets: List<TicketDto> = emptyList(),
    val prioridades: List<PrioridadDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val sistemas: List<SistemaDto> = emptyList()
)

fun TicketUiState.toEntity() = TicketDto(
    ticketId = ticketId,
    fecha = fecha ?: Date(),
    clienteId = clienteId,
    sistemaId = sistemaId,
    prioridadId = prioridadId,
    solicitadoPor = solicitadoPor,
    asunto = asunto,
    descripcion = descripcion
)
