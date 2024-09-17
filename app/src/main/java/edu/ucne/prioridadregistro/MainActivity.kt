package edu.ucne.prioridadregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prioridadregistro.data.local.database.PrioridadDb
import edu.ucne.prioridadregistro.presentation.navigation.PrioridadNavHost
import edu.ucne.prioridadregistro.ui.theme.PrioridadRegistroTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrioridadRegistroTheme {
                val navHost = rememberNavController()
                val items = NavigationItems()
                PrioridadNavHost(navHost, items)

            }
        }
    }
}
fun NavigationItems():List<NavigationItem>{
    return listOf(
        NavigationItem(
            titulo = "Prioridades",
            SelectIcon = Icons.Filled.Info,
            UnSelectIcon = Icons.Outlined.Info
        ),
        NavigationItem(
            titulo = "Tickets",
            SelectIcon = Icons.Filled.Build,
            UnSelectIcon = Icons.Outlined.Build
        ),
    )
}