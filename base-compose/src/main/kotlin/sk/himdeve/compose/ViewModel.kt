package sk.himdeve.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.SupervisedScope
import sk.himdeve.base.set
import kotlinx.coroutines.cancel

/**
 * Extends androidx.lifecycle.ViewModel due to usage with LocalViewModelStoreOwner and easy lifecycle management.
 */
abstract class BaseComposeViewModel<S>(
    initialState: S,
    protected val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    protected val scope = SupervisedScope(dispatcherProvider.main)

    private val _state = MutableStateFlow(initialState)
    val state: Flow<S> get() = _state

    abstract fun init()

    fun setState(reducer: S.() -> S): Boolean {
        dispatcherProvider.assertMainThread()
        return _state.set(reducer)
    }

    protected fun state(): S {
        return _state.value
    }

    override fun onCleared() {
        scope.cancel()
    }
}

/**
 * A helper function for connecting Compose with ViewModels with Dagger 2 dependencies. It uses LocalViewModelStoreOwner
 * to ensure correct lifecycle scope of the ViewModels. Which means the owner may be Activity, Fragment or
 * NavBackStackEntry in this case.
 *
 * Taken from https://proandroiddev.com/dagger-2-and-jetpack-compose-integration-8a8d424ffdb4
 */
@Composable
@Suppress("UNCHECKED_CAST")
fun <T : ViewModel> composeViewModel(
    modelClass: Class<T>,
    key: String? = null,
    viewModelInstanceCreator: () -> T
): T = androidx.lifecycle.viewmodel.compose.viewModel(
    modelClass = modelClass,
    key = key,
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelInstanceCreator() as T
        }
    }
)

