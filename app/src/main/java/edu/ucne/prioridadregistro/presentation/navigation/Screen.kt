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
}
