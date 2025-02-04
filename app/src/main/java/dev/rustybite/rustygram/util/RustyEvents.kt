package dev.rustybite.rustygram.util

import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramRoutes

sealed class RustyEvents {
    data class Navigate(val route: RustyGramRoutes) : RustyEvents()
    data class ShowSnackBar(val message: String) : RustyEvents()
    data class ShowToast(val message: String) : RustyEvents()
    data object PopBackStack : RustyEvents()
}