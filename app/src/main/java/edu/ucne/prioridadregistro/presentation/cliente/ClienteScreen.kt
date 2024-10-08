package edu.ucne.prioridadregistro.presentation.cliente

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ClienteScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteBodyScreen(
        uiState = uiState,
        onNombresChange = viewModel::onNombresChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onCelularChange = viewModel::onCelularChange,
        onRncChange = viewModel::onRncChange,
        onEmailChange = viewModel::onEmailChange,
        onDireccionChange = viewModel::onDireccionChange,
        onSaveCliente = viewModel::save,
        onNuevoCliente = viewModel::nuevo,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteBodyScreen(
    uiState: Uistate,
    onNombresChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onCelularChange: (String) -> Unit,
    onRncChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onSaveCliente: () -> Unit,
    onNuevoCliente: () -> Unit,
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
            Spacer(modifier = Modifier.padding(2.dp))
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
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onNuevoCliente()
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