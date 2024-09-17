package edu.ucne.prioridadregistro.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity

@Dao
interface TicketDao {
    @Upsert
    suspend fun  save (ticket: TicketEntity)
    @Query(
        """
            SELECT *
            FROM Tickets
            WHERE ticketId =:id
            LIMIT 1
        """
    )
    suspend fun find (id: Int): TicketEntity?

    @Query(
        """
            SELECT *
            FROM Tickets
            WHERE LOWER(:cliente)
            LIMIT 1
            """
    )
    suspend fun findByCliente(cliente: String): TicketEntity?

    @Query(
        """
            SELECT *
            FROM Tickets
            WHERE LOWER(:asunto)
            LIMIT 1
            """
    )
    suspend fun findByAsunto(asunto: String): TicketEntity?

    @Query(
        """
            SELECT *
            FROM Tickets
            WHERE LOWER(:descripcion)
            LIMIT 1
        """
    )
    suspend fun findByDescripcion(descripcion: String): TicketEntity?

    @Delete
    suspend fun delete (ticket: TicketEntity)
    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>
}