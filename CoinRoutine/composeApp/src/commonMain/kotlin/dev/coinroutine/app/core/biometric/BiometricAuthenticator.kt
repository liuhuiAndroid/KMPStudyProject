package dev.coinroutine.app.core.biometric

import androidx.compose.runtime.Composable

interface PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext

interface BiometricAuthenticator {
    suspend fun authenticate(): Boolean
}

expect fun getBiometricAuthenticator(context: PlatformContext): BiometricAuthenticator

enum class BiometricAuthNotAvailable {
    BIO_AUTH_NOT_AVAILABLE
}