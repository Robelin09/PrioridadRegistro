package edu.ucne.prioridadregistro.data.repository

import edu.ucne.prioridadregistro.data.local.dao.PrioridadDao
import edu.ucne.prioridadregistro.data.local.entities.PrioridadEntity
import edu.ucne.prioridadregistro.data.remote.PrioridadApi
import edu.ucne.prioridadregistro.data.remote.dto.PrioridadDto
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadApi: PrioridadApi
) {
    suspend fun save(prioridad: PrioridadDto) = prioridadApi.savePrioridad(prioridad)

    suspend fun getPrioridad(id: Int) = prioridadApi.getPrioridad(id)

    suspend fun delete(id: Int) = prioridadApi.deletePrioridad(id)


    suspend fun getPrioridades() =  prioridadApi.getAllPrioridad()
}