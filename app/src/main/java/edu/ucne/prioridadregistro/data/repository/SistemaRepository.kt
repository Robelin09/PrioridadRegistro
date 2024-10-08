package edu.ucne.prioridadregistro.data.repository

import edu.ucne.prioridadregistro.data.remote.PrioridadApi
import edu.ucne.prioridadregistro.data.remote.dto.SistemaDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemaApi: PrioridadApi
) {

    suspend fun save(sistema: SistemaDto) = sistemaApi.saveSistema(sistema)

    suspend fun getSistema(id: Int) = sistemaApi.getSistema(id)


    suspend fun delete(id: Int) = sistemaApi.deleteSistema(id)

    suspend fun getAllSistema()=sistemaApi.getAllSistema()
}
