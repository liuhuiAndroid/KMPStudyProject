package com.mvi.test.di

import com.mvi.test.database.getDatabaseBuilder
import com.mvi.test.database.getRoomDatabase
import com.mvi.test.heta.HetaViewModel
import com.mvi.test.network.HetaDataSource
import com.mvi.test.network.HttpClientFactory
import com.mvi.test.network.RemoteHetaDataSource
import com.mvi.test.screen.auth.AuthViewModel
import com.mvi.test.screen.book.BookDetailsViewModel
import com.mvi.test.screen.book.BookHomeViewModel
import com.mvi.test.screen.book.BookManageViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    single { getRoomDatabase(getDatabaseBuilder(context = get())) }
    singleOf(::RemoteHetaDataSource).bind<HetaDataSource>()
    viewModelOf(::HetaViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::BookHomeViewModel)
    viewModel { (id: Int) -> BookDetailsViewModel(id, get()) }
    viewModel { (id: Int) -> BookManageViewModel(id, get()) }
}

// https://www.youtube.com/watch?v=Yg7WNdYo8_c
// No more Manual Koin Initialization - New Context Binding is Awesome! 🍻
// https://www.youtube.com/watch?v=a6P6AJ5u2vY
// Koin just Got Better! Configuration Navigation Simplified
fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        androidLogger()
        modules(appModule)
    }
}