package edu.ucne.prioridadregistro.presentation.prioridad

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridadregistro.data.local.entities.PrioridadEntity
import edu.ucne.prioridadregistro.data.remote.dto.PrioridadDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrioridadListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: PrioridadViewModel = hiltViewModel(),
    onPrioridadClick: (Int) -> Unit,
    createPrioridad: () -> Unit,

) {
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState,
        onPrioridadClick,
        createPrioridad,
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: Uistate,
    onPrioridadClick: (Int) -> Unit,
    createPrioridad: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lista de Prioridades")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Ir al Menu")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createPrioridad,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Prioridad")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                items(uiState.prioridades) { prioridad ->
                    PrioridadRow(prioridad, onPrioridadClick)
                }
            }
        }
    }
}


@Composable
fun PrioridadRow(
    prioridad: PrioridadDto,
    onPrioridadClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onPrioridadClick(prioridad.prioridadId!!) }
    ) {
        Text(modifier = Modifier.weight(2f), text = prioridad.prioridadId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = prioridad.descripcion,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(modifier = Modifier.weight(2f), text = prioridad.diasCompromiso.toString())

    }
    HorizontalDivider()
}
