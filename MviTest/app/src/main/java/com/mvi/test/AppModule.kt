package com.mvi.test

import com.mvi.test.auth.AuthViewModel
import com.mvi.test.heta.HetaViewModel
import com.mvi.test.network.HetaDataSource
import com.mvi.test.network.HttpClientFactory
import com.mvi.test.network.RemoteHetaDataSource
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::RemoteHetaDataSource).bind<HetaDataSource>()
    viewModelOf(::HetaViewModel)
    viewModelOf(::AuthViewModel)
}