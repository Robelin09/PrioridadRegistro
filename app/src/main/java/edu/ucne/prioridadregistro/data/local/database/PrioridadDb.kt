package edu.ucne.prioridadregistro.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.prioridadregistro.data.local.dao.PrioridadDao
import edu.ucne.prioridadregistro.data.local.dao.TicketDao
import edu.ucne.prioridadregistro.data.local.entities.PrioridadEntity
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}
