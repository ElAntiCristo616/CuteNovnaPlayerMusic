package com.maxrave.simpmusic.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maxrave.simpmusic.ui.navigation.destination.home.AppLogDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.ListenTogetherDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.MoodDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.RadioBuilderDestination
import com.maxrave.simpmusic.ui.screen.home.ListenTogetherScreen
import com.maxrave.simpmusic.ui.screen.home.MoodScreen
import com.maxrave.simpmusic.ui.screen.other.AppLogScreen
import com.maxrave.simpmusic.ui.screen.other.RadioBuilderScreen

fun NavGraphBuilder.homeScreenGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    // CuteNovna v1.0.3: sin Créditos (la pantalla existe en el código por la
    // atribución GPL, pero ya no se puede abrir desde ningún lado) y sin
    // pantalla de notificaciones del desarrollador.
    composable<AppLogDestination> {
        AppLogScreen(
            paddingValues = innerPadding,
            navController = navController,
        )
    }
    composable<MoodDestination> { entry ->
        val params = entry.toRoute<MoodDestination>().params
        MoodScreen(
            navController = navController,
            params = params,
        )
    }
    composable<ListenTogetherDestination> {
        ListenTogetherScreen(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
    composable<RadioBuilderDestination> {
        RadioBuilderScreen(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}