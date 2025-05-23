package dev.coinroutine.app

/**
 * 使用 sealed class：
 * - 限制子类范围，提高类型安全；
 * - 让 when 表达式更安全（不用写 else）；
 * - 更适合 Kotlin Multiplatform 的 expect/actual 架构；
 * - 比 enum 更灵活，便于扩展。
 */
sealed class Platform {
    data object Android : Platform()
    data object Ios : Platform()
}

expect val platform: Platform