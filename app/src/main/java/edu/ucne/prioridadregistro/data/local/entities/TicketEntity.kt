package edu.ucne.prioridadregistro.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets",
    foreignKeys = [
        ForeignKey(
            entity = PrioridadEntity::class,
            parentColumns = ["prioridadId"],
            childColumns = ["prioridadId"]
        )
    ]
)
data class TicketEntity(
    @PrimaryKey
    val ticketId: Int? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val fecha: Date? = null,

    val prioridadId: Int
)