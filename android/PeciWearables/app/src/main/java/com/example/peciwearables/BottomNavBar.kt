package com.example.peciwearables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomBar(selectedTab: Tab, onItemSelected: (Tab) -> Unit) {
    NavigationBar(
        containerColor = Color(0xFF1A1A1A),
        tonalElevation = 0.dp
    ) {
        navItems.forEach { item ->
            val selected = selectedTab == item.tab

            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(item.tab) },
                label = null,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Box(
                        modifier = Modifier
                            .then(
                                if (selected) Modifier
                                    .background(Color(0xFF252525), RoundedCornerShape(20.dp))
                                    .padding(6.dp)
                                else Modifier
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(if (selected) Constants.accentColor else Color(0xFF2A2A2A))
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = if (selected) Color.Black else Color.Gray,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            AnimatedVisibility(
                                visible = selected,
                                enter = expandHorizontally() + fadeIn(),
                                exit = shrinkHorizontally() + fadeOut()
                            ) {
                                Text(
                                    text = item.label,
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}