package edu.ucne.prioridadregistro.data.repository

import edu.ucne.prioridadregistro.data.local.dao.TicketDao
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
){
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun getTicket(id: Int) = ticketDao.find(id)

    suspend fun getCliente(cliente: String) = ticketDao.findByCliente(cliente)

    suspend fun getAsunto(asunto: String) = ticketDao.findByAsunto(asunto)

    suspend fun getDescripcion(descripcion: String) = ticketDao.findByDescripcion(descripcion)

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getTickets() = ticketDao.getAll()

}