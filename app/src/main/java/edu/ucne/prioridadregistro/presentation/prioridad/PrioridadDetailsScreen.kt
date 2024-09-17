package edu.ucne.prioridadregistro.presentation.prioridad

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
fun PrioridadDetailsScreen(
    prioridadId: Int,
    viewModel: PrioridadViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(prioridadId) {
        viewModel.select(prioridadId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadDetailsBodyScreen(
        uiState = uiState,
        onDescripcionChange = viewModel::onDescripcionChange,
        onDiasCompromisoChange = viewModel::onDiasCompromisoChange,
        onSavePrioridad = viewModel::save,
        onDeletePrioridad = viewModel::delete,
        goBack = goBack
    )
}

@Composable
fun PrioridadDetailsBodyScreen(
    uiState: Uistate,
    onDescripcionChange: (String) -> Unit,
    onDiasCompromisoChange: (Int) -> Unit,
    onSavePrioridad: () -> Unit,
    onDeletePrioridad: () -> Unit,
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
                    label = { Text("Descripción") },
                    value = uiState.descripcion,
                    onValueChange = onDescripcionChange,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Días Compromiso") },
                    value = uiState.diasCompromiso.toString(),
                    onValueChange = { newValue ->
                        val diasCompromiso = newValue.toIntOrNull() ?: 0
                        onDiasCompromisoChange(diasCompromiso)
                    }
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
                            onSavePrioridad()
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Editar")
                        Text("Actualizar")
                    }
                    OutlinedButton(
                        onClick = {
                           onDeletePrioridad()
                            goBack()
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

