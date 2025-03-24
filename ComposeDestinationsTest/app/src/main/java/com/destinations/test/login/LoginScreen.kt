package com.destinations.test.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.startDestination
import com.destinations.test.MainViewModel
import com.destinations.test.core.viewmodel.activityViewModel
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination

@Destination<RootGraph>
@Composable
fun LoginScreen(
    vm: MainViewModel = activityViewModel(),
    navigator: DestinationsNavigator,
) {
    // 禁用物理返回键
    BackHandler(true) { /* We want to disable back clicks */ }

    // 监听登录状态，实时响应 ViewModel 中的登录状态变化
    val isLoggedIn by vm.isLoggedInFlow.collectAsState()
    val hasNavigatedUp = remember { mutableStateOf(false) }

    if (isLoggedIn && !hasNavigatedUp.value) {
        hasNavigatedUp.value = true // avoids double navigation // 防止重复跳转

        // 尝试返回上一个页面
        if (!navigator.navigateUp()) {
            // Sometimes we are starting on LoginScreen (to avoid UI jumps)
            // In those cases, navigateUp fails, so we just navigate to the registered start destination
            // 如果无法返回（如直接启动登录页），导航到根导航图的起始页
            navigator.navigate(NavGraphs.root.startDestination as DirectionDestinationSpec) {
                popUpTo(LoginScreenDestination) { // 清除登录页回退栈
                    inclusive = true // 包含登录页本身，确保返回栈中不再保留登录页
                }
            }
        }
        // 双重导航策略？
        // 场景	处理方式	示例
        // 从其他页跳转登录	navigateUp()	主页 → 登录 → 返回主页
        // 直接启动登录页	导航到根起始页	App 冷启动时直接打开登录页 → 登录后跳主页
    }

    LoginScreenContent { vm.login() }
}

@Composable
private fun LoginScreenContent(onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("WORK IN PROGRESS")

            Spacer(Modifier.height(32.dp))

            Button(onClick = onLoginClick) {
                Text("Login")
            }
        }
    }
}