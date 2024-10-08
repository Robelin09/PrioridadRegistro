package edu.ucne.prioridadregistro.presentation.sistema

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
fun SistemaDetailsScreen(
    sistemaId: Int,
    viewModel: SistemaViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    LaunchedEffect(sistemaId) {
        viewModel.select(sistemaId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaDetailsBodyScreen(
        uiState = uiState,
        onNombredeSistemaChange = viewModel::onNombredeSistemaChange,
        onSaveSistema = viewModel::save,
        onDeleteSistema = viewModel::delete,
        goBack = goBack
    )
}

@Composable
fun SistemaDetailsBodyScreen(
    uiState: Uistate,
    onNombredeSistemaChange: (String) -> Unit,
    onSaveSistema: () -> Unit,
    onDeleteSistema: () -> Unit,
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
                label = { Text("Nombre del Sistema") },
                value = uiState.nombredeSistema,
                onValueChange = onNombredeSistemaChange,
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
                        onSaveSistema()
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Editar")
                    Text("Actualizar")
                }
                OutlinedButton(
                    onClick = {
                        onDeleteSistema()
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
