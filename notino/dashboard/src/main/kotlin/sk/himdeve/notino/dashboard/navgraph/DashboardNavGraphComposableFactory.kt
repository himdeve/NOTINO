package sk.himdeve.notino.dashboard.navgraph

import android.content.Context
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import sk.himdeve.compose.BaseNavComposableFactory
import sk.himdeve.compose.NavigationCommand
import sk.himdeve.notino.dashboard.DashboardScreen
import sk.himdeve.notino.dashboard.DashboardViewModel
import sk.himdeve.notino.dashboard.di.DashboardScreenComponent
import sk.himdeve.notino.navigation.Navigator
import kotlin.reflect.KClass

class DashboardNavGraphComposableFactory :
    BaseNavComposableFactory<DashboardScreenComponent.ParentComponent, DashboardViewModel>() {

    override val navCommand: NavigationCommand = Navigator.Destination.Dashboard

    override fun parentComponentType(): KClass<DashboardScreenComponent.ParentComponent> =
        DashboardScreenComponent.ParentComponent::class

    override fun viewModelType(): KClass<DashboardViewModel> = DashboardViewModel::class

    override fun viewModel(
        context: Context,
        parentComponent: DashboardScreenComponent.ParentComponent,
        arguments: Bundle?
    ): DashboardViewModel =
        parentComponent.dashboardScreenComponentFactory
            .create()
            .viewModel

    @ExperimentalAnimationApi
    @Composable
    override fun Content(viewModel: DashboardViewModel) = DashboardScreen(viewModel = viewModel)
}