package edu.ucne.prioridadregistro.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridadregistro.data.database.PrioridadDb
import edu.ucne.prioridadregistro.presentation.navigation.prioridad.PrioridadListScreen
import edu.ucne.prioridadregistro.presentation.navigation.prioridad.PrioridadScreen

@Composable
fun PrioridadNavHost( prioridadDb: PrioridadDb,
    navHostController: NavHostController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadList by prioridadDb.prioridadDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen( prioridadList,
                createPrioridad = {
                    navHostController.navigate(Screen.Prioridad(0))

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
    }
}