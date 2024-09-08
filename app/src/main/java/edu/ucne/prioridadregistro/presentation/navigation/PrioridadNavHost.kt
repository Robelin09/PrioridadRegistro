package edu.ucne.prioridadregistro.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridadregistro.data.database.PrioridadDb
import edu.ucne.prioridadregistro.presentation.navigation.prioridad.PrioridadDetailsScreen
import edu.ucne.prioridadregistro.presentation.navigation.prioridad.PrioridadListScreen
import edu.ucne.prioridadregistro.presentation.navigation.prioridad.PrioridadScreen
import kotlinx.coroutines.launch

@Composable
fun PrioridadNavHost(
    prioridadDb: PrioridadDb,
    navHostController: NavHostController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadList by prioridadDb.prioridadDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                prioridadList,
                createPrioridad = {
                    navHostController.navigate(Screen.Prioridad(0))
                },
                deletePrioridad = { prioridad ->
                    scope.launch {
                        prioridadDb.prioridadDao().delete(prioridad)
                    }
                },
                onPrioridadClick = { prioridadId ->
                    navHostController.navigate(Screen.PrioridadDetails(prioridadId))
                }
            )
        }
        composable<Screen.Prioridad> {
            val args = it.toRoute<Screen.Prioridad>()
            PrioridadScreen(
                prioridadDb,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.PrioridadDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PrioridadDetails>()
            PrioridadDetailsScreen(
                prioridadDb,
                prioridadId = args.prioridadId,
                goBack = { navHostController.navigateUp() }
            )
        }
    }
}