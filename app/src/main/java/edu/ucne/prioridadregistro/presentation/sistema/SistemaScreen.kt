package edu.ucne.prioridadregistro.presentation.sistema


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
import edu.ucne.prioridadregistro.presentation.sistema.Uistate

@Composable
fun SistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyScreen(
        uiState = uiState,
        onNombredeSistemaChange = viewModel::onNombredeSistemaChange,
        onSaveSistema = viewModel::save,
        onNuevoSistema = viewModel::nuevo,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaBodyScreen(
    uiState: Uistate,
    onNombredeSistemaChange: (String) -> Unit,
    onSaveSistema: () -> Unit,
    onNuevoSistema: () -> Unit,
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
                        onSaveSistema()
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                    Text("Guardar")
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onNuevoSistema()
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
