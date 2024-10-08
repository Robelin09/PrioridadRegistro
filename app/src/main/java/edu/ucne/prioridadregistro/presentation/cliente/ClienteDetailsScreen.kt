package edu.ucne.prioridadregistro.presentation.cliente


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

@Composable
fun ClienteDetailsScreen(
    clienteId: Int,
    viewModel: ClienteViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(clienteId) {
        viewModel.select(clienteId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteDetailsBodyScreen(
        uiState = uiState,
        onNombresChange = viewModel::onNombresChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onCelularChange = viewModel::onCelularChange,
        onRncChange = viewModel::onRncChange,
        onEmailChange = viewModel::onEmailChange,
        onDireccionChange = viewModel::onDireccionChange,
        onSaveCliente = viewModel::save,
        onDeleteCliente = viewModel::delete,
        goBack = goBack
    )
}

@Composable
fun ClienteDetailsBodyScreen(
    uiState: Uistate,
    onNombresChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onCelularChange: (String) -> Unit,
    onRncChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onSaveCliente: () -> Unit,
    onDeleteCliente: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                label = { Text("Nombres") },
                value = uiState.nombres,
                onValueChange = onNombresChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Teléfono") },
                value = uiState.telefono,
                onValueChange = onTelefonoChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Celular") },
                value = uiState.celular,
                onValueChange = onCelularChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("RNC") },
                value = uiState.rnc,
                onValueChange = onRncChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Email") },
                value = uiState.email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Dirección") },
                value = uiState.direccion,
                onValueChange = onDireccionChange,
                modifier = Modifier.fillMaxWidth()
            )

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
                    onClick = {
                        onSaveCliente()
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Editar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteCliente()
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
