package com.mvi.test.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mvi.test.heta.HetaScreen
import com.mvi.test.screen.auth.AuthScreen
import com.mvi.test.screen.book.BookDetailsScreen
import com.mvi.test.screen.book.BookHomeScreen
import com.mvi.test.screen.book.BookManageScreen
import com.mvi.test.screen.splash.SplashScreen
import com.mvi.test.screen.weather.WeatherScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    // 获取当前路由
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            println("当前 route 是：$currentRoute")
            if (currentRoute == Screens.AuthScreen.toString()) {
                TopAppBar(
                    title = { Text("Sign in") }, colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.LightGray
                    )
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.SplashScreen,
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            composable<Screens.SplashScreen> {
                SplashScreen {
                    navController.navigate(Screens.AuthScreen)
                }
            }
            composable<Screens.AuthScreen> {
                AuthScreen(
                    snackbarHostState, navigateToMainScreen = {
                        navController.navigate(Screens.BookHomeScreen)
                    }
                )
            }
            composable<Screens.WeatherScreen> {
                WeatherScreen(navController)
            }
            composable<Screens.HetaScreen> {
                HetaScreen(
                    navigateBack = {
                        // https://www.youtube.com/watch?v=QpRuoxKd5SY
                        // 3 Navigation issues that you MUST Avoid!
                        // navController.navigateUp()
                        navController.navigate(Screens.AuthScreen) {
                            launchSingleTop = true
                            popUpTo<Screens.HetaScreen> {
                                inclusive = true
                            }
                        }
                        // navController.popBackStack()
                    })
            }
            composable<Screens.BookHomeScreen> {
                BookHomeScreen(
                    onBookSelect = {
                        navController.navigate(Screens.BookDetailsScreen(it))
                    },
                    onCreateClick = {
                        navController.navigate(Screens.BookManageScreen())
                    }
                )
            }
            composable<Screens.BookManageScreen> {
                val args = it.toRoute<Screens.BookManageScreen>()
                BookManageScreen(
                    id = args.id,
                    onBackClick = { navController.navigateUp() }
                )
            }
            // https://www.youtube.com/watch?v=AIC_OFQ1r3k
            // Type-Safe Navigation with the OFFICIAL Compose Navigation Library
            // => Search navigation
            composable<Screens.BookDetailsScreen> {
                val args = it.toRoute<Screens.BookDetailsScreen>()
                BookDetailsScreen(
                    id = args.id,
                    onEditClick = {
                        navController.navigate(Screens.BookManageScreen(args.id))
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}