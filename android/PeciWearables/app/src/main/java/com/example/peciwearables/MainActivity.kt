package com.example.peciwearables

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.peciwearables.ui.theme.PeciWearablesTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect

data class NavItem(val label: String, val icon: ImageVector, val tab: Tab)

val navItems = listOf(
    NavItem("Info", Icons.Filled.Info, Tab.INFO),
    NavItem("Map", Icons.Filled.Place, Tab.MAP),
    NavItem("Settings", Icons.Filled.Settings, Tab.SETTINGS)
)

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeciWearablesTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: AppViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val tabs = Tab.entries.toList()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    // Sync pager -> tab
    LaunchedEffect(selectedTab) {
        val index = tabs.indexOf(selectedTab)
        if (pagerState.currentPage != index) {
            pagerState.animateScrollToPage(index)
        }
    }

    // Sync tab -> pager
    LaunchedEffect(pagerState.settledPage) {
        viewModel.setTab(tabs[pagerState.settledPage])
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CustomBottomBar(
                selectedTab = selectedTab,
                onItemSelected = { viewModel.setTab(it) }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (tabs[page]) {
                Tab.INFO -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    InfoBox(
                        title = "Omi Glasses",
                        description = "Connected",
                        icon = R.drawable.glasses_solid_full,
                        state = BoxState.MID_BATTERY,
                        modalContent = listOf(
                            ModalContent.TextContent("Your Omi Glasses are connected and working properly."),
                            ModalContent.TextContent("Battery: 85%\nFirmware: v1.2.3")
                        )
                    )
                    InfoBox(
                        title = "Wrist Band",
                        description = "Disconnected",
                        icon = R.drawable.watch,
                        state = BoxState.WARNING,
                    )
                }
                Tab.MAP -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Map Screen") }
                Tab.SETTINGS -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Settings Screen") }
            }
        }
    }
}

