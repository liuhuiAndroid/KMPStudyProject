package com.destinations.test

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import com.destinations.test.core.viewmodel.activityViewModel
import com.destinations.test.ui.composables.BottomBar
import com.destinations.test.ui.composables.SampleScaffold
import com.destinations.test.ui.composables.TopBar

@Composable
fun SampleApp() {
    val navController = rememberNavController()

    val vm = activityViewModel<MainViewModel>()
    // 👇 this avoids a jump in the UI that would happen if we relied only on ShowLoginWhenLoggedOut
    val start = if (!vm.isLoggedIn) LoginScreenDestination else NavGraphs.root.defaultStartDirection

    SampleScaffold(
        navController = navController,
        topBar = { dest, backStackEntry ->
            if (dest.shouldShowScaffoldElements) {
                TopBar(dest, backStackEntry)
            }
        },
        bottomBar = {
            if (it.shouldShowScaffoldElements) {
                BottomBar(navController)
            }
        }
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            start = start
        )

        // Has to be called after calling DestinationsNavHost because only
        // then does NavController have a graph associated that we need for
        // `appCurrentDestinationAsState` method
        ShowLoginWhenLoggedOut(vm, navController)
    }
}

private val DestinationSpec.shouldShowScaffoldElements get() = this !is LoginScreenDestination

/**
 * 自动检测用户登录状态，在未登录时强制跳转至登录页。
 */
@Composable
private fun ShowLoginWhenLoggedOut(
    vm: MainViewModel,
    navController: NavHostController,
) {
    // 实时获取当前导航终点
    val currentDestination by navController.currentDestinationAsState()
    // 监听登录状态流
    val isLoggedIn by vm.isLoggedInFlow.collectAsState()
    val navigator = navController.rememberDestinationsNavigator()

    // 当用户未登录且当前不在登录页时
    if (!isLoggedIn && currentDestination != LoginScreenDestination) {
        // everytime destination changes or logged in state we check
        // if we have to show Login screen and navigate to it if so
        // 导航至登录页
        navigator.navigate(LoginScreenDestination) {
            launchSingleTop = true // 避免重复创建登录页实例
        }
    }
}