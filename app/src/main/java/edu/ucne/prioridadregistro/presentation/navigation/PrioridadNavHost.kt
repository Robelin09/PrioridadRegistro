package edu.ucne.prioridadregistro.presentation.navigation


import android.widget.RemoteViews.RemoteCollectionItems
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridadregistro.NavigationItem
import edu.ucne.prioridadregistro.data.local.database.PrioridadDb
import edu.ucne.prioridadregistro.presentation.prioridad.PrioridadDetailsScreen
import edu.ucne.prioridadregistro.presentation.prioridad.PrioridadListScreen
import edu.ucne.prioridadregistro.presentation.prioridad.PrioridadScreen
import edu.ucne.prioridadregistro.presentation.ticket.TicketDetailsScreen
import edu.ucne.prioridadregistro.presentation.ticket.TicketListScreen
import edu.ucne.prioridadregistro.presentation.ticket.TicketScreen
import kotlinx.coroutines.launch

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
    items: List<NavigationItem>
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var SelectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu")
                items.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        label = { Text(text = navigationItem.titulo) },
                        selected = index == SelectedItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when(navigationItem.titulo){
                                "Prioridades" -> {navHostController.navigate(Screen.PrioridadList)}
                                "Tickets" -> {navHostController.navigate(Screen.TicketList)}
                            }
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.TicketList
        ) {
            composable<Screen.PrioridadList> {
                PrioridadListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createPrioridad = {
                        navHostController.navigate(Screen.Prioridad(0))
                    },
                    onPrioridadClick = {
                        navHostController.navigate(Screen.PrioridadDetails(it))
                    }
                )
            }
            composable<Screen.TicketList> {
                TicketListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    createTicket = {
                        navHostController.navigate(Screen.Ticket(0))
                    },
                    onTicketClick = {
                        navHostController.navigate(Screen.TicketDetails(it))
                    },

                    )
            }

            composable<Screen.Prioridad> {
                val args = it.toRoute<Screen.Prioridad>()
                PrioridadScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<Screen.PrioridadDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.PrioridadDetails>()
                PrioridadDetailsScreen(
                    prioridadId = args.prioridadId,
                    goBack = { navHostController.navigateUp() }
                )
            }
            composable<Screen.Ticket> {
                val args = it.toRoute<Screen.Ticket>()
                TicketScreen(
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
            composable<Screen.TicketDetails> {
                val args = it.toRoute<Screen.TicketDetails>()
                TicketDetailsScreen(
                    ticketId = args.ticketId,
                    goBack = { navHostController.navigateUp() }
                )
            }
        }
    }
}