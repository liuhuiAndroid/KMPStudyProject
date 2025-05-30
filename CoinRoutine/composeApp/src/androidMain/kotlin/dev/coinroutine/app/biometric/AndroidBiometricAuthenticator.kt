package dev.coinroutine.app.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dev.coinroutine.app.core.biometric.BiometricAuthNotAvailable
import dev.coinroutine.app.core.biometric.BiometricAuthenticator
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidBiometricAuthenticator(private val context: Context) : BiometricAuthenticator {

    override suspend fun authenticate(): Boolean {
        // ✅ 检查设备是否支持生物识别
        val biometricManager = BiometricManager.from(context)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) != BiometricManager.BIOMETRIC_SUCCESS) {
            throw Exception(BiometricAuthNotAvailable.BIO_AUTH_NOT_AVAILABLE.toString())
        }

        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                // 执行协程取消时的收尾工作
            }
            val executor = ContextCompat.getMainExecutor(context)
            val biometricPrompt = BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        if (continuation.isActive) {
                            continuation.resumeWithException(Exception(errString.toString()))
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        if (continuation.isActive) {
                            continuation.resume(true)
                        }
                    }

                    override fun onAuthenticationFailed() {
                        if (continuation.isActive) {
                            continuation.resume(false)
                        }
                    }
                }
            )

            // ✅ 构建认证提示信息
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate using biometrics")
                .setNegativeButtonText("Cancel")
                .build()
            // ✅ 启动认证过程
            biometricPrompt.authenticate(promptInfo)
        }
    }
}