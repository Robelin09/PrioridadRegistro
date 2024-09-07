package edu.ucne.prioridadregistro.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadregistro.data.entities.PrioridadEntity

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