package edu.ucne.prioridadregistro.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun PrioridadDetailsScreen(
    prioridadDb: PrioridadDb,
    prioridadId: Int,
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val prioridad = remember { mutableStateOf<PrioridadEntity?>(null) }
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(prioridadId) {
        prioridad.value = prioridadDb.prioridadDao().find(prioridadId)
        prioridad.value?.let {
            descripcion = it.descripcion
            diasCompromiso = it.diascompromiso?.toString() ?: ""
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            prioridad.value?.let {
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
                Spacer(modifier = Modifier.height(16.dp))

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
                                    prioridadDb.prioridadDao().save(
                                        it.copy(
                                            descripcion = descripcion,
                                            diascompromiso = dias
                                        )
                                    )
                                    errorMessage = null
                                    focusManager.clearFocus()
                                    goBack()
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                        Text("Actualizar")
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                prioridadDb.prioridadDao().delete(it)
                                goBack()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}