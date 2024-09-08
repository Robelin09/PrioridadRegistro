package edu.ucne.prioridadregistro.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadregistro.data.database.PrioridadDb
import edu.ucne.prioridadregistro.data.entities.PrioridadEntity
import kotlinx.coroutines.launch

@Composable
fun PrioridadScreen(prioridadDb: PrioridadDb, goBack: () -> Unit) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                label = { Text("Descripción") },
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Días Compromiso") },
                value = diasCompromiso,
                onValueChange = { diasCompromiso = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(2.dp))
            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val dias = diasCompromiso.toIntOrNull()
                        if (descripcion.isBlank()) {
                            errorMessage = "La descripción no puede estar vacía"
                        } else if (dias == null || dias <= 0) {
                            errorMessage = "Días compromiso debe ser un número válido"
                        } else {
                            scope.launch {
                                try {
                                    val prioridad = PrioridadEntity(
                                        descripcion = descripcion,
                                        diascompromiso = dias
                                    )
                                    savePrioridad(prioridadDb, prioridad)
                                    descripcion = ""
                                    diasCompromiso = ""
                                    errorMessage = null
                                    focusManager.clearFocus()
                                } catch (e: Exception) {
                                    errorMessage = "Error al guardar prioridad"
                                }
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        descripcion = ""
                        diasCompromiso = ""
                        errorMessage = null
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Nuevo")
                    Text("Nuevo")
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
private suspend fun savePrioridad(prioridadDb: PrioridadDb, prioridad: PrioridadEntity) {
    prioridadDb.prioridadDao().save(prioridad)
}
