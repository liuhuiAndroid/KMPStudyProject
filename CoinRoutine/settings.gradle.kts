rootProject.name = "CoinRoutine"
// 启用 Gradle 的一个特性预览功能 —— 类型安全的项目访问器
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            // mavenContent 是一个过滤器，意思是：
            // 只包含 androidx、com.android 和 com.google 这些 group 的内容，其他 group 的内容不会加载（加快同步、避免冲突）。
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        // Gradle 官方插件门户，部分插件只在这里发布
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")