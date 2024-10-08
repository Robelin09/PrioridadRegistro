package edu.ucne.prioridadregistro.data.remote

import edu.ucne.prioridadregistro.data.remote.dto.ClienteDto
import edu.ucne.prioridadregistro.data.remote.dto.PrioridadDto
import edu.ucne.prioridadregistro.data.remote.dto.SistemaDto
import edu.ucne.prioridadregistro.data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrioridadApi {
    @GET("api/Prioridades/{id}")
    suspend fun getPrioridad(@Path("id")id: Int):PrioridadDto

    @GET("api/Prioridades")
    suspend fun getAllPrioridad(): List<PrioridadDto>

    @POST("api/Prioridades")
    suspend fun savePrioridad(@Body prioridadDto: PrioridadDto?): PrioridadDto

    @DELETE("api/Prioridades/{id}")
    suspend fun deletePrioridad(@Path("id") id: Int)

    @GET("api/Sistemas/{id}")
    suspend fun getSistema(@Path("id") id: Int): SistemaDto

    @GET("api/Sistemas")
    suspend fun getAllSistema(): List<SistemaDto>

    @POST("api/Sistemas")
    suspend fun saveSistema(@Body sistemaDto: SistemaDto?): SistemaDto


    @DELETE("api/Sistemas/{id}")
    suspend fun deleteSistema(@Path("id") id: Int)


    @GET("api/Tickets/{id}")
    suspend fun getTickets(@Path("id") id: Int): TicketDto

    @GET("api/Tickets")
    suspend fun getAllTicket(): List<TicketDto>

    @POST("api/Tickets")
    suspend fun saveTicket(@Body ticketDto: TicketDto?): TicketDto

    @DELETE("api/Tickets/{id}")
    suspend fun deleteTicket(@Path("id") id: Int)

    @GET("api/Clientes/{id}")
    suspend fun getCliente(@Path("id") id: Int): ClienteDto

    @GET("api/Clientes")
    suspend fun getAllCliente(): List<ClienteDto>

    @POST("api/Clientes")
    suspend fun saveCliente(@Body clienteDto: ClienteDto?): ClienteDto

    @DELETE("api/Clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Int)

}