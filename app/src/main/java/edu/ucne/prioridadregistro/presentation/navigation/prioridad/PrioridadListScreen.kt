package edu.ucne.prioridadregistro.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadregistro.data.entities.PrioridadEntity

@Composable
fun PrioridadListScreen(prioridadList: List<PrioridadEntity>, createPrioridad: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createPrioridad,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Prioridad")
            }
        }
    ) { paddingValues ->  // Añadir el contentPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Aplicar el paddingValues aquí
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
                items(prioridadList) { prioridad ->
                    PrioridadRow(prioridad)  // Pasar la variable aquí
                }
            }
        }
    }
}

@Composable
fun PrioridadRow(prioridad: PrioridadEntity) {  // Mover esta función fuera
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(2f), text = prioridad.prioridadid.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = prioridad.descripcion,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(modifier = Modifier.weight(2f), text = prioridad.diascompromiso.toString())
    }
    HorizontalDivider()
}
