package dev.rustybite.rustygram.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed class RustyAppRoutes {
    @Serializable
    data object EditScreen : RustyAppRoutes()
}