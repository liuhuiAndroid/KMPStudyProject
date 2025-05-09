package com.mvi.test.network

interface HetaDataSource {
    suspend fun getCurrentTime(): Result<String, NetworkError>
    suspend fun queryDxSecretByDeviceCode(): Result<String, NetworkError>
}