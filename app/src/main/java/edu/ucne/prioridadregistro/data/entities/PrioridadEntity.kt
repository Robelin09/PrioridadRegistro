package edu.ucne.prioridadregistro.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Prioridades")

data class PrioridadEntity(
    @PrimaryKey
    val prioridadid: Int? = null,
    val descripcion: String = "",
    val diascompromiso: Int?
)
