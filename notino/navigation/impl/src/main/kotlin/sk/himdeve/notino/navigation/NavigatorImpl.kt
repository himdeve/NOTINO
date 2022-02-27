package sk.himdeve.notino.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import sk.himdeve.base.AbstractScoped
import sk.himdeve.base.DispatcherProvider
import kotlinx.coroutines.launch

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class NavigatorImpl(dispatcherProvider: DispatcherProvider) : Navigator, AbstractScoped(dispatcherProvider.main) {

    private val _navigationEvent = MutableSharedFlow<Navigator.NavigationEvent>()
    override val navigationEvent: Flow<Navigator.NavigationEvent> get() = _navigationEvent

    private val _backClickEvent = MutableSharedFlow<Unit>()
    override val backClickEvent: Flow<Unit> get() = _backClickEvent

    override fun navigateTo(destination: Navigator.Destination, routeWithArgs: (() -> String)?) {
        scope.launch {
            _navigationEvent.emit(
                Navigator.NavigationEvent(
                    route = routeWithArgs?.invoke() ?: destination.route,
                    navOptions = destination.navOptions
                )
            )
        }
    }

    override fun goBack() {
        scope.launch {
            _backClickEvent.emit(Unit)
        }
    }

    override fun init() {
    }
}