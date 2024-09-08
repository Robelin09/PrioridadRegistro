package edu.ucne.prioridadregistro.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
    @Serializable
    data class PrioridadDetails(val prioridadId: Int) : Screen()
}
