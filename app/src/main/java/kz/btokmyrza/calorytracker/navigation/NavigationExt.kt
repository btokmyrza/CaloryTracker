package kz.btokmyrza.calorytracker.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.screen(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    direction: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Right,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = this.composable(
    route = route,
    arguments = arguments,
    enterTransition = {
        slideIntoContainer(
            towards = direction,
            animationSpec = tween(300),
        )
    },
    content = content,
)