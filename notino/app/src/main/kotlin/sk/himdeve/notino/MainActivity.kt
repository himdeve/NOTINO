package sk.himdeve.notino

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import sk.himdeve.base.SupervisedScope
import sk.himdeve.base.di.AppScope
import sk.himdeve.base.di.Components
import sk.himdeve.compose.NavGraphComposableFactory
import sk.himdeve.notino.base.R
import sk.himdeve.notino.navigation.Navigator
import com.squareup.anvil.annotations.ContributesTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class MainActivity : AppCompatActivity() {

    private val scope = SupervisedScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        val navigator = Components.get<ComposeMainComponent>().navigator
        val navGraphComposableFactories = Components.get<ComposeMainComponent>().navGraphComposableFactories
        val startDestination = Navigator.Destination.Dashboard

        setContent {
            MainContent(
                navigator = navigator,
                navGraphComposableFactories = navGraphComposableFactories,
                startDestination = startDestination,
                defaultDestination = Navigator.Destination.Dashboard
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

@ContributesTo(AppScope::class)
interface ComposeMainComponent {
    val navigator: Navigator
    val navGraphComposableFactories: @JvmSuppressWildcards Set<NavGraphComposableFactory>
}