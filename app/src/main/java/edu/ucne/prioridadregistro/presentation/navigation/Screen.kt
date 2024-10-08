package edu.ucne.prioridadregistro.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
    @Serializable
    data class PrioridadDetails(val prioridadId: Int) : Screen()
    @Serializable
    data object TicketList : Screen()
    @Serializable
    data class Ticket(val ticketId: Int) : Screen()
    @Serializable
    data class TicketDetails(val ticketId: Int) : Screen()
    @Serializable
    data object SistemaList : Screen()
    @Serializable
    data class Sistema(val sistemaId: Int) : Screen()
    @Serializable
    data class SistemaDetails(val sistemaId: Int) : Screen()
    @Serializable
    data object ClienteList : Screen()
    @Serializable
    data class Cliente(val clienteId: Int) : Screen()
    @Serializable
    data class ClienteDetails(val clienteId: Int) : Screen()
}
