package sk.himdeve.notino

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import sk.himdeve.compose.NavGraphComposableFactory
import sk.himdeve.notino.base.theme.DefaultPadding
import sk.himdeve.notino.base.theme.NotinoTheme
import sk.himdeve.notino.base.theme.defaultFadeIn
import sk.himdeve.notino.base.theme.defaultFadeOut
import sk.himdeve.notino.base.ui.AppBar
import sk.himdeve.notino.navigation.Navigator

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(
    navigator: Navigator,
    navGraphComposableFactories: Set<NavGraphComposableFactory>,
    startDestination: Navigator.Destination,
    // TODO: This is generally use during log out
    defaultDestination: Navigator.Destination
) {
    NotinoTheme {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onPrimary
        ) {
            Column {
                // AppBar should be shown on each screen. That's why it is here.
                AppBar()

                val navController = rememberAnimatedNavController()

                LaunchedEffect("navigation") {
                    navigator.navigationEvent
                        .collect { event ->
                            navController.navigate(route = event.route, navOptions = event.navOptions)
                        }
                }

                LaunchedEffect("backClick") {
                    navigator.backClickEvent
                        .collect {
                            navController.popBackStack()
                        }
                }

                AnimatedNavHost(
                    modifier = Modifier.padding(
                        start = DefaultPadding,
                        end = DefaultPadding
                    ),
                    navController = navController,
                    startDestination = startDestination.route,
                    enterTransition = { defaultFadeIn() },
                    exitTransition = { defaultFadeOut() }
                ) {
                    navGraphComposableFactories.forEach { factory ->
                        factory.create(this, navController) {
                            navController.navigate(
                                route = defaultDestination.route,
                                navOptions = defaultDestination.navOptions
                            )
                        }
                    }
                }
            }
        }
    }
}