package edu.ucne.prioridadregistro.data.repository

import edu.ucne.prioridadregistro.data.remote.PrioridadApi
import edu.ucne.prioridadregistro.data.remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: PrioridadApi
) {
    suspend fun save(cliente: ClienteDto) = clienteApi.saveCliente(cliente)

    suspend fun getCliente(id: Int) = clienteApi.getCliente(id)

    suspend fun delete(id: Int) = clienteApi.deleteCliente(id)


    suspend fun getClientes() =  clienteApi.getAllCliente()

}