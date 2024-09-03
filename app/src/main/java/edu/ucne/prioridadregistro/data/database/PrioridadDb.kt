package edu.ucne.prioridadregistro.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.prioridadregistro.data.dao.PrioridadDao
import edu.ucne.prioridadregistro.data.entities.PrioridadEntity

@Database(
    entities = [
        PrioridadEntity::class
    ],
    version=1,
    exportSchema=false
)
abstract class PrioridadDb : RoomDatabase(){
    abstract fun prioridadDao(): PrioridadDao
}
