package com.koin.test.di

import com.koin.test.DbClient
import com.koin.test.MyRepository
import com.koin.test.MyRepositoryImpl
import com.koin.test.MyViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.*
import org.koin.dsl.bind

import org.koin.dsl.module

val androidModule = module {
    singleOf(::DbClient)
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    viewModelOf(::MyViewModel)
}