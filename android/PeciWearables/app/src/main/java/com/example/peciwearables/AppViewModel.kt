package com.example.peciwearables

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class Tab {
    INFO,
    MAP,
    SETTINGS
}

class AppViewModel : ViewModel()
{
    private val _selectedTab = MutableStateFlow(Tab.INFO)
    val selectedTab: StateFlow<Tab> = _selectedTab

    fun setTab(tab: Tab)
    {
        _selectedTab.value = tab
    }

    fun getTab(): Tab
    {
        return _selectedTab.value
    }

}