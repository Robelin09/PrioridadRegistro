package edu.ucne.prioridadregistro.presentation.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onSistemaChange = viewModel::onSistemaChange,
        onSolicitadoPorChange = viewModel::onSolicitadoPorChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onSaveTicket = viewModel::save,
        onNuevoTicket = viewModel::nuevo,
        onPrioridadChange = viewModel::onPrioridadChange,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    uiState: TicketUiState,
    onClienteChange: (Int) -> Unit,
    onSistemaChange: (Int) -> Unit,
    onSolicitadoPorChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onSaveTicket: () -> Unit,
    onNuevoTicket: () -> Unit,
    onPrioridadChange: (Int) -> Unit,
    goBack: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate = dateFormat.format(uiState.fecha ?: Date()) // Usa la fecha actual si uiState.fecha es null

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            ClienteDropdown(uiState = uiState, onClienteChange = onClienteChange)
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

            PrioridadDropdown(uiState = uiState, onPrioridadChange = onPrioridadChange)

            Spacer(modifier = Modifier.padding(2.dp))
            uiState.errorMessage?.let {
                Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSaveTicket()
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onNuevoTicket()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDropdown(
    uiState: TicketUiState,
    onClienteChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona Cliente") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Cliente") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.clientes.forEach { cliente ->
                DropdownMenuItem(
                    text = { Text(cliente.nombres) },
                    onClick = {
                        selectedText = cliente.nombres
                        cliente.clienteId?.let { onClienteChange(it) }
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaDropdown(
    uiState: TicketUiState,
    onSistemaChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona Sistema") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Sistema") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.sistemas.forEach { sistema ->
                DropdownMenuItem(
                    text = { Text(sistema.nombredeSistema) },
                    onClick = {
                        selectedText = sistema.nombredeSistema
                        sistema.sistemaId?.let { onSistemaChange(it) }
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadDropdown(
    uiState: TicketUiState,
    onPrioridadChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona Prioridad") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Prioridad") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.prioridades.forEach { prioridad ->
                DropdownMenuItem(
                    text = { Text(prioridad.descripcion) },
                    onClick = {
                        selectedText = prioridad.descripcion
                        prioridad.prioridadId?.let { onPrioridadChange(it) }
                        expanded = false
                    }
                )
            }
        }

    }
}
