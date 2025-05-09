package com.mvi.test.domain

import kotlinx.serialization.Serializable

@Serializable
class HetaCurrentTimeDto<T>(
    val statusCode: Int,
    val statusMsg: String,
    val responseData: T,
)

@Serializable
class CurrentTimeDto(
    val timeStamp: String,
)

@Serializable
class DxSecretDto(
    val pointId: String,
    val pointName: String,
    val secret: String,
    val secretHash: String,
    val deviceId: String,
    val deviceName: String,
    val deviceDesc: String,
    val macAddress: String,
    val deviceStatus: String,
    val areaId: String,
    val areaName: String,
    val deviceCode: String,
    val isClose: String,
    val id: Int,
)