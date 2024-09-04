package edu.ucne.prioridadregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.prioridadregistro.data.database.PrioridadDb
import edu.ucne.prioridadregistro.data.entities.PrioridadEntity
import edu.ucne.prioridadregistro.ui.theme.PrioridadRegistroTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration().build()
        setContent {
            PrioridadRegistroTheme {
                PrioridadScreen()
            }
        }
    }
    @Composable
    fun PrioridadScreen() {
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
                                    savePrioridad(
                                        PrioridadEntity(
                                            descripcion = descripcion,
                                            diascompromiso = dias
                                        )
                                    )
                                    descripcion = ""
                                    diasCompromiso = ""
                                    errorMessage = null
                                    focusManager.clearFocus()
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
                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                Spacer(modifier = Modifier.padding(8.dp))
                PrioridadListScreen(prioridadList)
            }
        }
    }
    @Composable
    fun PrioridadListScreen(prioridadList: List<PrioridadEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("Lista de Prioridades", style = MaterialTheme.typography.headlineMedium)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = "ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Descripción",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Días Compromiso",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(prioridadList) {
                    PrioridadRow(it)
                }
            }
        }
    }
    @Composable
    private fun PrioridadRow(it: PrioridadEntity) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(2f), text = it.prioridadid.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.descripcion,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(modifier = Modifier.weight(2f), text = it.diascompromiso.toString())
        }
        HorizontalDivider()
    }
    private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        prioridadDb.prioridadDao().save(prioridad)
    }
}