package edu.ucne.prioridadregistro.presentation.ticket

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TicketDetailsScreen(
    ticketId: Int,
    viewModel: TicketViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(ticketId) {
        viewModel.select(ticketId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketDetailsBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onSistemaChange = viewModel::onSistemaChange,
        onSolicitadoPorChange = viewModel::onSolicitadoPorChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onPrioridadChange = viewModel::onPrioridadChange,
        onSaveTicket = viewModel::save,
        onDeleteTicket = viewModel::delete,
        goBack = goBack
    )
}

@Composable
fun TicketDetailsBodyScreen(
    uiState: TicketUiState,
    onClienteChange: (Int) -> Unit,
    onSistemaChange: (Int) -> Unit,
    onSolicitadoPorChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPrioridadChange: (Int) -> Unit,
    onSaveTicket: () -> Unit,
    onDeleteTicket: () -> Unit,
    goBack: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate = dateFormat.format(uiState.fecha ?: Date())

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Cliente Dropdown
            ClienteDropdown(uiState = uiState, onClienteChange = onClienteChange)

            // Sistema Dropdown
            SistemaDropdown(uiState = uiState, onSistemaChange = onSistemaChange)

            OutlinedTextField(
                label = { Text("Solicitado por") },
                value = uiState.solicitadoPor,
                onValueChange = onSolicitadoPorChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text("Fecha") },
                value = formattedDate,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            OutlinedTextField(
                label = { Text("Asunto") },
                value = uiState.asunto,
                onValueChange = onAsuntoChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text("Descripción") },
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                modifier = Modifier.fillMaxWidth()
            )

            // Prioridad Dropdown
            PrioridadDropdown(uiState = uiState, onPrioridadChange = onPrioridadChange)

            Spacer(modifier = Modifier.height(16.dp))

            uiState.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onSaveTicket() }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Guardar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteTicket()
                        goBack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Text("Eliminar")
                }
            }
        }
    }
}
