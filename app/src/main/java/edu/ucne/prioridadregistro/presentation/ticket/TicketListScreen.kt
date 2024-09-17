package edu.ucne.prioridadregistro.presentation.ticket

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
import edu.ucne.prioridadregistro.data.local.entities.TicketEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TicketListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: TicketViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit,
    createTicket: () -> Unit,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onTicketClick = onTicketClick,
        createTicket = createTicket,
        prioridades = uiState.prioridades,

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: TicketUiState,
    onTicketClick: (Int) -> Unit,
    createTicket: () -> Unit,
    prioridades: List<PrioridadEntity>
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lista de Tickets")
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
                onClick = createTicket,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Ticket")
            }
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("Lista de Tickets", style = MaterialTheme.typography.headlineMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Cliente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Asunto",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Prioridad",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tickets) { ticket ->
                    TicketRow(
                        ticket = ticket,
                        onTicketClick = onTicketClick,
                        prioridades = prioridades
                    )
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    ticket: TicketEntity,
    onTicketClick: (Int) -> Unit,
    prioridades: List<PrioridadEntity>
) {
    val prioridad = prioridades.find { it.prioridadId == ticket.prioridadId }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onTicketClick(ticket.ticketId!!) }
            .padding(vertical = 8.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = ticket.ticketId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.cliente,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(modifier = Modifier.weight(2f), text = ticket.asunto)

        Text(
            modifier = Modifier.weight(1f),
            text = prioridad?.descripcion ?: "N/A",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    HorizontalDivider()
}
