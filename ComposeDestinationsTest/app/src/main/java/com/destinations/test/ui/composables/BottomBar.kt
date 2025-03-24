package com.destinations.test.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.destinations.test.R
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.AccountScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TaskListScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val navigator = navController.rememberDestinationsNavigator()
    // 在 Material 3 中，BottomNavigation 和 BottomNavigationItem 已被 NavigationBar 和 NavigationBarItem 取代
    NavigationBar {
        BottomBarItem.entries.forEach { destination ->
            // 检测当前导航项对应的页面是否在返回栈中
            val isCurrentDestOnBackStack by navController.isRouteOnBackStackAsState(destination.direction)
            NavigationBarItem(
                selected = isCurrentDestOnBackStack,
                onClick = {
                    // 已在该页面时的处理
                    if (isCurrentDestOnBackStack) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        // 回退到该页面的初始状态
                        // inclusive=false 表示保留目标页面
                        navigator.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    // 导航到新页面
                    navigator.navigate(destination.direction) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        // 清空根导航图之上的所有页面，防止返回栈过度增长
                        popUpTo(NavGraphs.root) {
                            saveState = true // 保存被弹出页面的状态（如滚动位置）
                        }

                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true // 避免重复创建相同实例。与 popUpTo 配合使用效果最佳
                        // Restore state when reselecting a previously selected item
                        restoreState = true // 恢复之前保存的状态
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}

enum class BottomBarItem(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    TaskList(TaskListScreenDestination, Icons.AutoMirrored.Filled.List, R.string.task_list_screen),
    Account(AccountScreenDestination, Icons.Default.Person, R.string.account_screen),
    Settings(SettingsScreenDestination, Icons.Default.Settings, R.string.settings_screen)
}