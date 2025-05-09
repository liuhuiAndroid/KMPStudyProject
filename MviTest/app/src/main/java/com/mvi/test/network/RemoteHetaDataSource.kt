package com.mvi.test.network

import com.mvi.test.domain.CurrentTimeDto
import com.mvi.test.domain.DxSecretDto
import com.mvi.test.domain.HetaCurrentTimeDto
import com.mvi.test.network.request.PointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.Json

class RemoteHetaDataSource(
    private val httpClient: HttpClient,
) : HetaDataSource {
    override suspend fun getCurrentTime(): Result<String, NetworkError> {
        return safeCall<HetaCurrentTimeDto<String>> {
            httpClient.post(
                urlString = "http://47.99.39.200:8080/hthw/area/getCurrentTime"
            )
        }.map { response ->
            Json.decodeFromString<CurrentTimeDto>(response.responseData).timeStamp
        }
    }

    override suspend fun queryDxSecretByDeviceCode(): Result<String, NetworkError> {
        return safeCall<HetaCurrentTimeDto<List<DxSecretDto>>> {
            httpClient.post(
                urlString = "http://47.99.39.200:8080/hthw/area/queryDxSecretByDeviceCode"
            ) {
                setBody(PointRequest(pointId = "1173"))
            }
        }.map { response ->
            Json.encodeToString(response.responseData)
        }
    }

}