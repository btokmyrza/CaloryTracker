package kz.btokmyrza.calorytracker.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kz.btokmyrza.calorytracker.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(route = event.route)
}

fun NavGraphBuilder.screen(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = this.composable(
    route = route,
    enterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(300),
        )
    },
    content = content,
)