package edu.ucne.prioridadregistro.data.repository

import edu.ucne.prioridadregistro.data.local.dao.TicketDao
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity
import edu.ucne.prioridadregistro.data.remote.PrioridadApi
import edu.ucne.prioridadregistro.data.remote.dto.TicketDto
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: PrioridadApi
){
    suspend fun save(ticket: TicketDto) = ticketApi.saveTicket(ticket)

    suspend fun getTicket(id: Int) =  ticketApi.getTickets(id)

    suspend fun delete(id: Int) = ticketApi.deleteTicket(id)

    suspend fun getTickets() = ticketApi.getAllTicket()
}