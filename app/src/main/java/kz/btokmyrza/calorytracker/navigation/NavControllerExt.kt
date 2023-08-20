package kz.btokmyrza.calorytracker.navigation

import androidx.navigation.NavController
import kz.btokmyrza.calorytracker.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(route = event.route)
}