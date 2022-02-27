package sk.himdeve.notino.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import sk.himdeve.base.Scoped
import sk.himdeve.compose.NavigationCommand
import kotlinx.coroutines.flow.Flow

/**
 * Navigation mechanism inspired by:
 * https://github.com/hitherejoe/minimise
 * https://proandroiddev.com/jetpack-compose-navigation-architecture-with-viewmodels-1de467f19e1c
 * https://proandroiddev.com/how-to-make-jetpack-compose-navigation-easier-and-testable-b4b19fd5f2e4
 */

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
interface Navigator : Scoped {
    val navigationEvent: Flow<NavigationEvent>

    val backClickEvent: Flow<Unit>

    fun navigateTo(destination: Destination, routeWithArgs: (() -> String)? = null)

    fun goBack()

    data class NavigationEvent(
        val route: String,
        val navOptions: NavOptions? = null
    )

    //TODO: Consider using a code-gen tool for the destinations. E.g. https://github.com/raamcosta/compose-destinations
    sealed class Destination(
        override val route: String,
        override val navOptions: NavOptions? = null,
        override val arguments: List<NamedNavArgument> = emptyList()
    ) : NavigationCommand {

        object Dashboard : Destination(route = "dashboard", navOptions = navOptions {
            popUpTo(0)
            launchSingleTop
        })
    }
}