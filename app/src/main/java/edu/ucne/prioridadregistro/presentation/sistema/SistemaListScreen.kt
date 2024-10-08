package edu.ucne.prioridadregistro.presentation.sistema

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
import edu.ucne.prioridadregistro.data.remote.dto.SistemaDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SistemaListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SistemaViewModel = hiltViewModel(),
    onSistemaClick: (Int) -> Unit,
    createSistema: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState,
        onSistemaClick,
        createSistema
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: Uistate,
    onSistemaClick: (Int) -> Unit,
    createSistema: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lista de Sistemas")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Ir al Menú")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createSistema,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Sistema")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("Lista de Sistemas", style = MaterialTheme.typography.headlineMedium)
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
                    text = "Nombre del Sistema",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sistemas) { sistema ->
                    SistemaRow(sistema, onSistemaClick)
                }
            }
        }
    }
}

@Composable
fun SistemaRow(
    sistema: SistemaDto,
    onSistemaClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onSistemaClick(sistema.sistemaId!!) }
            .padding(8.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = sistema.sistemaId.toString())
        Text(
            modifier = Modifier.weight(1f),
            text = sistema.nombredeSistema,
            style = MaterialTheme.typography.headlineSmall
        )
    }
    HorizontalDivider()
}
