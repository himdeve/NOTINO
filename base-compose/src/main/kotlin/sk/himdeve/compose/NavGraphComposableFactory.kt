package sk.himdeve.compose

import android.content.Context
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import sk.himdeve.base.di.Components
import sk.himdeve.logger.LOG
import kotlin.reflect.KClass

/**
 * Ensures scalable Jetpack Compose navigation and its separation to feature modules.
 *
 * Taken from: https://medium.com/bumble-tech/scalable-jetpack-compose-navigation-9c0659f7c912
 */

interface NavigationCommand {
    val route: String
    val navOptions: NavOptions?
    val arguments: List<NamedNavArgument>
}

interface NavGraphComposableFactory {

    @ExperimentalAnimationApi
    fun create(builder: NavGraphBuilder, navController: NavHostController, onError: () -> Unit)
}

abstract class BaseNavComposableFactory<PC : Any, VM : BaseComposeViewModel<*>> : NavGraphComposableFactory {

    protected abstract val navCommand: NavigationCommand

    protected abstract fun parentComponentType(): KClass<PC>

    protected abstract fun viewModelType(): KClass<VM>

    protected abstract fun viewModel(context: Context, parentComponent: PC, arguments: Bundle? = null): VM

    @ExperimentalAnimationApi
    @Composable
    protected abstract fun Content(viewModel: VM)

    @ExperimentalAnimationApi
    override fun create(builder: NavGraphBuilder, navController: NavHostController, onError: () -> Unit) {
        builder.composable(
            route = navCommand.route,
            arguments = navCommand.arguments
        ) { backStackEntry ->
            val parentComponent = Components.getOrNull(parentComponentType())

            if (parentComponent == null) {
                LOG.w("ParentComponent '${parentComponentType()}' not found.")

                if (backStackEntry.isAtLeastStarted()) {
                    // Call onError() only when a Composable is visible and not transitioning to another Composable.
                    // This would be a problem when clicking on "Logout" in Settings screen. Logout would set the parent component to null
                    // and because a Composable is recomposed also during a transition to another Composable (usually multiple times),
                    // an infinite loop would happen here.
                    onError()
                }

                return@composable
            }

            Content(
                composeViewModel(modelClass = viewModelType().java) {
                    viewModel(navController.context, parentComponent, backStackEntry.arguments)
                        .apply { init() }
                })
        }
    }

    private fun NavBackStackEntry.isAtLeastStarted(): Boolean =
        lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
}
