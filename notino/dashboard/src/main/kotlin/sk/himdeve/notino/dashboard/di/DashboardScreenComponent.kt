package sk.himdeve.notino.dashboard.di

import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.di.AppScope
import sk.himdeve.compose.NavGraphComposableFactory
import sk.himdeve.notino.dashboard.DashboardViewModel
import sk.himdeve.notino.dashboard.navgraph.DashboardNavGraphComposableFactory
import sk.himdeve.notino.products.ProductRepository
import sk.himdeve.notino.products.ProductSyncer
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoSet

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Subcomponent(modules = [DashboardScreenModule::class])
interface DashboardScreenComponent {
    val viewModel: DashboardViewModel

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        val dashboardScreenComponentFactory: Factory
    }

    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardScreenComponent
    }
}

@Module
object DashboardScreenModule {

    @Provides
    @JvmStatic
    fun viewModel(
        dispatcherProvider: DispatcherProvider,
        productSyncer: ProductSyncer,
        productRepository: ProductRepository
    ): DashboardViewModel = DashboardViewModel(
        DashboardViewModel.State(),
        dispatcherProvider,
        productSyncer,
        productRepository,
    )
}

@Module
@ContributesTo(AppScope::class)
object DashboardNavigationModule {

    @Provides
    @JvmStatic
    @IntoSet
    fun dashboardNavGraphComposableFactory(): NavGraphComposableFactory {
        return DashboardNavGraphComposableFactory()
    }
}