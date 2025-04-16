![landscapist](https://user-images.githubusercontent.com/24237865/127760344-bb042fe8-23e1-4014-b208-b7b549d32086.png)<br>

landscapist

Image loading library for Jetpack Compose using Glide, Coil, and Fresco

<br>

<p align="center">
  <a href="https://devlibrary.withgoogle.com/products/android/repos/skydoves-Landscapist"><img alt="Google" src="https://skydoves.github.io/badges/google-devlib.svg"/></a>
  <a href="https://youtu.be/8y65xBHDHK0?feature=shared"><img alt="Google" src="https://skydoves.github.io/badges/youtube-landscapist.svg"/></a>
  <a href="https://github.com/doveletter"><img alt="Profile" src="https://skydoves.github.io/badges/dove-letter.svg"/></a><br>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/Landscapist/actions"><img alt="Build Status" src="https://github.com/skydoves/landscapist/workflows/Android%20CI/badge.svg"/></a>
  <a href="https://androidweekly.net/issues/issue-441"><img alt="Android Weekly" src="https://skydoves.github.io/badges/android-weekly.svg"/></a>
  <a href="https://skydoves.medium.com/optimized-image-loading-for-compose-and-kotlin-multiplatform-a45eb2e710c0"><img alt="Medium" src="https://skydoves.github.io/badges/Story-Medium.svg"/></a>
  <a href="https://github.com/skydoves"><img alt="Profile" src="https://skydoves.github.io/badges/skydoves.svg"/></a> 
</p>

<p align="center">
🌻 <a href="https://skydoves.github.io/landscapist" target="_blank"> Landscapist</a> is a highly optimized, pluggable Jetpack Compose and Kotlin Multiplatform image loading solution that fetches and displays network images with <a href="https://github.com/bumptech/glide" target="_blank"> Glide</a>, <a href="https://github.com/coil-kt/coil" target="_blank"> Coil</a>, and <a href="https://github.com/facebook/fresco" target="_blank"> Fresco.</a> This library supports tracing image loading states, composing custom implementations, and some valuable animations, such as crossfades, blur transformation, and circular reveals. You can also configure and attach image-loading behaviors easily and fast with image plugins. <br><br> <a align="center" href="https://skydoves.github.io/landscapist" target="_blank">See official documentation for Landscapist</a>
</p>
🌻 Landscapist 是一款高度优化、可插拔的 Jetpack Compose 和 Kotlin 多平台图片加载解决方案，可使用 Glide、Coil 和 Fresco 获取并显示网络图片。该库支持跟踪图片加载状态、编写自定义实现以及一些实用的动画，例如交叉淡入淡出、模糊变换和圆形显示。您还可以使用图片插件轻松快速地配置和附加图片加载行为。

## Who's using Landscapist?

👉 [Check out who's using Landscapist](https://skydoves.github.io/landscapist/#whos-using-landscapist).

👉 查看谁在使用 Landscapist。

Landscapist hits **+900,000 downloads every month** around the globe! 🚀

Landscapist 每月全球下载量超过 90 万次！🚀

![globe](https://user-images.githubusercontent.com/24237865/196018576-a9c87534-81a2-4618-8519-0024b67964bf.png)

## Why Landscapist?

Landscapist is built with a lot of consideration to improve the performance of image loadings in Jetpack Compose. Most composable functions of Landscapist are Restartable and Skippable, which indicates fairly improved recomposition performance according to the Compose compiler metrics. Also, the library performance was improved with [Baseline Profiles](https://android-developers.googleblog.com/2022/01/improving-app-performance-with-baseline.html) and it supports many pluggable features, such as [ImageOptions](https://github.com/skydoves/landscapist#imageoptions), [listening image state changes](https://github.com/skydoves/landscapist#listening-image-state-changes), [custom composables](https://github.com/skydoves/landscapist#custom-composables), [preview on Android Studio](https://github.com/skydoves/landscapist#preview-on-android-studio), [ImageComponent and ImagePlugin](https://github.com/skydoves/landscapist#imagecomponent-and-imageplugin), [placeholder](https://github.com/skydoves/landscapist#placeholder), [animations (circular reveal, crossfade)](https://github.com/skydoves/landscapist#animation), [transformation (blur)](https://github.com/skydoves/landscapist#transformation), and [palette](https://github.com/skydoves/landscapist#palette).

Landscapist 的构建充分考虑了 Jetpack Compose 中图片加载的性能提升。Landscapist 的大部分可组合函数都支持 Restartable 和 Skippable，根据 Compose 编译器指标，这表明重组性能得到了显著提升。此外，该库的性能也通过 Baseline Profiles 得到了提升，并支持许多可插拔功能，例如 ImageOptions、监听图片状态变化、自定义可组合函数、Android Studio 预览、ImageComponent 和 ImagePlugin、占位符、动画（循环显示、交叉淡入淡出）、变换（模糊）和调色板。

<details>
 <summary>See the Compose compiler metrics for Landscapist</summary>
查看 Landscapist 的 Compose 编译器指标

![metrics](https://user-images.githubusercontent.com/24237865/201906004-f4490bdf-7af9-4ad6-b586-7dcc6f07d0c8.png)

</details>

## Documentation

For comprehensive information about Landscapist, please refer to the **[official documentation](https://skydoves.github.io/landscapist)**.

有关 Landscapist 的完整信息，请参阅官方文档。

## Technical Content

For the more details of the history, performance, customizability, and how to load network images and implement placeholders, animations, and transformations with Landscapist, check out [Optimized Image Loading for Compose and Kotlin Multiplatform](https://medium.com/proandroiddev/optimized-image-loading-for-compose-and-kotlin-multiplatform-a45eb2e710c0).

有关历史、性能、可定制性以及如何使用 Landscapist 加载网络图像和实现占位符、动画和转换的更多详细信息，请查看针对 Compose 和 Kotlin Multiplatform 的优化图像加载。

## Demo projects
You can see the use cases of this library in the repositories below:

您可以在下面的存储库中看到该库的用例：

- [google/modernstorage](https://github.com/google/modernstorage/tree/e62cda539ca75884dd49df3bcf8629751f0a91e6/sample): ModernStorage is a group of libraries that provide an abstraction layer over storage on Android to simplify its interactions. ModernStorage 是一组库，它在 Android 上的存储上提供抽象层以简化其交互。
- [GetStream/WhatsApp-Clone-Compose](https://github.com/getStream/whatsApp-clone-compose): 📱 WhatsApp clone project demonstrates modern Android development built with Jetpack Compose and Stream Chat SDK for Compose. 📱WhatsApp 克隆项目展示了使用 Jetpack Compose 和 Stream Chat SDK for Compose 构建的现代 Android 开发。
- [android/storage-samples](https://github.com/android/storage-samples/tree/main/ScopedStorage): Multiple samples showing the best practices in storage APIs on Android. 多个示例展示了 Android 上存储 API 的最佳实践。
- [skydoves/DisneyCompose](https://github.com/skydoves/disneycompose): 🧸 A demo Disney app using Jetpack Compose and Hilt based on modern Android tech-stacks and MVVM architecture. 🧸 基于现代 Android 技术栈和 MVVM 架构，使用 Jetpack Compose 和 Hilt 的 Disney 演示应用程序。
- [skydoves/MovieCompose](https://github.com/skydoves/MovieCompose): 🎞 A demo movie app using Jetpack Compose and Hilt based on modern Android tech stacks. 🎞 基于现代 Android 技术栈使用 Jetpack Compose 和 Hilt 的演示电影应用程序。

<div class="header">
  <a href="https://github.com/bumptech/glide" target="_blank"> <img src="https://user-images.githubusercontent.com/24237865/95545537-1bc15200-0a39-11eb-883d-644f564da5d3.png" align="left" width="4%" alt="Glide" /></a>
  <h1>Glide</h1>
</div>

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

Add the codes below to your **root** `build.gradle` file (not your module-level build.gradle file):

将以下代码添加到您的根 build.gradle 文件（而不是模块级 build.gradle 文件）：

```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

Next, add the dependency below to your **module**'s `build.gradle` file:

接下来，将以下依赖项添加到模块的 build.gradle 文件中：

```gradle
dependencies {
    implementation("com.github.skydoves:landscapist-glide:2.4.7")
}
```

> **Note**: `Landscapist-Glide` includes version `4.16.0` of [Glide](https://github.com/bumptech/glide) internally. So please make sure your project is using the same Glide version or exclude the Glide dependency to adapt yours. Also, please make sure the Jetpack Compose version on the [release page](https://github.com/skydoves/Landscapist/releases).
>
> 注意：Landscapist-Glide 内部包含 Glide 4.16.0 版本。因此，请确保您的项目使用相同的 Glide 版本，或者排除 Glide 依赖项以适应您的项目。另外，请确保发布页面上的 Jetpack Compose 版本正确。

### GlideImage
You can load images simply by using `GlideImage` composable function as the following example below:

您可以使用 GlideImage 可组合函数简单地加载图像，如下例所示：

```kotlin
GlideImage(
  imageModel = { imageUrl }, // loading a network image using an URL.
  imageOptions = ImageOptions(
    contentScale = ContentScale.Crop,
    alignment = Alignment.Center
  )
)
```

### More Details for GlideImage
<details>
 <summary>👉 Read further for more details</summary>
👉 阅读更多以了解更多详情

### Custom RequestOptions and TransitionOptions

You can customize your request-options with your own [RequestOptions](https://bumptech.github.io/glide/doc/options.html#requestoptions) and [TransitionOptions](https://bumptech.github.io/glide/doc/options.html#transitionoptions) for applying caching strategies, loading transformations like below:

您可以使用自己的 RequestOptions 和 TransitionOptions 自定义请求选项，以应用缓存策略、加载转换，如下所示：

```kotlin
GlideImage(
  imageModel = { imageUrl },
  requestOptions = {
    RequestOptions()
        .override(256, 256)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
  }
)
```

### Custom RequestBuilder
You can request image with your own [RequestBuilder](https://bumptech.github.io/glide/doc/options.html#requestbuilder), which is the backbone of the request in Glide and is responsible for bringing your options together with your requested url or model to start a new load.

您可以使用自己的 RequestBuilder 请求图像，它是 Glide 中请求的骨干，负责将您的选项与您请求的 url 或模型一起开始新的加载。

```kotlin
GlideImage(
  imageModel = { imageUrl },
  requestBuilder = { Glide.with(LocalContext.current.applicationContext).asDrawable() },
  modifier = Modifier.constrainAs(image) {
    centerHorizontallyTo(parent)
    top.linkTo(parent.top)
  }.aspectRatio(0.8f)
)
```

### Custom RequestListener
You can register your own [RequestListener](https://bumptech.github.io/glide/javadocs/440/com/bumptech/glide/request/RequestListener.html), which allows you to trace the status of a request while images load.

您可以注册自己的 RequestListener，它允许您在图像加载时跟踪请求的状态。

```kotlin
GlideImage(
  imageModel = { imageUrl },
  requestListener = object: RequestListener<Drawable> {
    override fun onLoadFailed(
      e: GlideException?,
      model: Any?,
      target: Target<Drawable>?,
      isFirstResource: Boolean
    ): Boolean {
      // do something
      return false
    }

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      // do something
      return true
    }
  }
)
```

### LocalGlideRequestOptions
You can pass the same instance of your `RequestOptions` down through the Composition in your composable hierarchy as following the example below:

您可以将 RequestOptions 的相同实例通过可组合层次结构中的 Composition 向下传递，如下例所示：

```kotlin
val requestOptions = RequestOptions()
    .override(300, 300)
    .circleCrop()

CompositionLocalProvider(LocalGlideRequestOptions provides requestOptions) {
  // Loads images with the custom `requestOptions` without explicit defines.
  GlideImage(
    imageModel = ...
  )
}
```

</details>

<div class="header">
  <a href="https://github.com/coil-kt/coil" target="_blank"> <img src="https://user-images.githubusercontent.com/24237865/95545538-1cf27f00-0a39-11eb-83dd-ef9b8c6a74cb.png" align="left" width="4%" alt="Fresco" /></a>
  <h1>Coil</h1>
</div>
[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

Add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation("com.github.skydoves:landscapist-coil:$version")
}
```

If you're targeting on Kotlin Multiplatform, add the dependency below to your module's `build.gradle.kts` file:

```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            implementation("com.github.skydoves:landscapist-coil3:$version")
        }
    }
}
```

The `coil3-landscapist` package functions identically to the `coil-landscapist` package, with the key distinction being its focus on Kotlin Multiplatform. This enables the use of Coil3 across various platforms, including Android, iOS, and Desktop (JVM), facilitating a unified image loading experience across different environments.

> **Note**: Please make sure your project uses the same Jetpack Compose version on the [release page](https://github.com/skydoves/Landscapist/releases).

### CoilImage
You can load images by using the `CoilImage` composable function as the following example below:

```kotlin
CoilImage(
  imageModel = { imageUrl }, // loading a network image or local resource using an URL.
  imageOptions = ImageOptions(
    contentScale = ContentScale.Crop,
    alignment = Alignment.Center
  )
)
```

### More Details for CoilImage

<details>
 <summary>👉 Read further for more details</summary>

### Custom ImageRequest and ImageLoader
You can load images with your own [ImageRequest](https://coil-kt.github.io/coil/image_requests/) and [ImageLoader](https://coil-kt.github.io/coil/image_loaders/), which provides all the necessary information for loading images like caching strategies and transformations.

```kotlin
CoilImage(
  imageRequest = {
      ImageRequest.Builder(LocalContext.current)
        .data(poster.poster)
        .crossfade(true)
        .build() },
  imageLoader = {
      ImageLoader.Builder(LocalContext.current)
        .availableMemoryPercentage(0.25)
        .crossfade(true)
        .build() },
  modifier = modifier,
)
```

 ### LocalCoilImageLoader
 You can pass the same instance of your `ImageLoader` down through the Composition in your composable hierarchy as following the example below:

 ```kotlin
 val imageLoader = ImageLoader.Builder(context).build()
CompositionLocalProvider(LocalCoilImageLoader provides imageLoader) {
  
   // This will automatically use the value of current imageLoader in the hierarchy.
   CoilImage(
     imageModel = ...
   )
 }
 ```

 <img src="https://user-images.githubusercontent.com/24237865/131246748-b88903a1-43de-4e6c-9069-3e956a0cf8a6.gif" align="right" width="32%"/>

## Animated Image Supports (GIF, Webp)
You can load animated GIFs and WebP Images with your `ImageLoader`.

```kotlin
val context = LocalContext.current
val imageLoader = ImageLoader.Builder(context)
  .components {
    if (SDK_INT >= 28) {
      add(ImageDecoderDecoder.Factory())
    } else {
      add(GifDecoder.Factory())
    }
  }
  .build()

CoilImage(
    imageModel = { poster.gif }, // URL of an animated image.
    imageLoader = { imageLoader },
    shimmerParams = ShimmerParams(
      baseColor = background800,
      highlightColor = shimmerHighLight
    ),
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .height(500.dp)
      .clip(RoundedCornerShape(8.dp))
  )
```

</details>

<div class="header">
  <a href="https://github.com/facebook/fresco" target="_blank"> <img src="https://user-images.githubusercontent.com/24237865/95545540-1cf27f00-0a39-11eb-9e84-96b9df81364b.png" align="left" width="4%" alt="Fresco" /></a>
  <h1>Fresco</h1>
</div>

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

Add the dependency below to your **module**'s `build.gradle` file:
```gradle
dependencies {
    implementation("com.github.skydoves:landscapist-fresco:$version")
}
```
> **Note**: `Landscapist-Fresco` includes version `3.1.0` of Fresco. So please make sure your project is using the same Fresco version or exclude the Fresco dependency to adapt yours. Also, please make sure the Jetpack Compose version on the [release page](https://github.com/skydoves/Landscapist/releases).

### Setup
To get started, you should set up `Fresco` with [ImagePipelineConfig](https://frescolib.org/docs/configure-image-pipeline.html) in your `Application` class. Generally, it's recommended initializing with  `OkHttpImagePipelineConfigFactory`. Also, you can customize caching, networking, and thread pool strategies with your own `ImagePipelineConfig`. For more details, you can check out [Using Other Network Layers](https://frescolib.org/docs/using-other-network-layers.html#using-okhttp).
```kotlin
class App : Application() {

  override fun onCreate() {
    super.onCreate()

    val pipelineConfig =
      OkHttpImagePipelineConfigFactory
        .newBuilder(this, OkHttpClient.Builder().build())
        .setDiskCacheEnabled(true)
        .setDownsampleEnabled(true)
        .setResizeAndRotateEnabledForNetwork(true)
        .build()

    Fresco.initialize(this, pipelineConfig)
  }
}
```

### FrescoImage
You can load images by using the `FrescoImage` composable function as the following example below:

```kotlin
FrescoImage(
  imageUrl = stringImageUrl, // loading a network image using an URL.
  imageOptions = ImageOptions(
    contentScale = ContentScale.Crop,
    alignment = Alignment.Center
  )
)
```

### More Details for FrescoImage
<details>
 <summary>👉 Read further for more details</summary>

### Custom ImageRequest
You can load images with your own [ImageRequest](https://frescolib.org/docs/image-requests.html), which provides some necessary information for loading images like decoding strategies and resizing.

```kotlin
val imageRequest = ImageRequestBuilder
  .newBuilderWithSource(uri)
  .setImageDecodeOptions(decodeOptions)
  .setLocalThumbnailPreviewsEnabled(true)
  .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
  .setProgressiveRenderingEnabled(false)
  .setResizeOptions(ResizeOptions(width, height))
  .build()

FrescoImage(
  imageUrl = stringImageUrl,
  imageRequest = { imageRequest }
)
```

### LocalFrescoImageRequest
You can pass the same instance of your `imageRequestBuilder` down through the Composition in your composable hierarchy as following the example below:

```kotlin
// customize the ImageRequest as needed
val imageRequestBuilder = ImageRequestBuilder
  .newBuilderWithSource(uri)
  .setImageDecodeOptions(decodeOptions)
  .setLocalThumbnailPreviewsEnabled(true)
  .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
  .setProgressiveRenderingEnabled(false)
  .setResizeOptions(ResizeOptions(width, height))

CompositionLocalProvider(LocalFrescoImageRequest provides imageRequestBuilder) {
  // This will automatically use the value of current ImageRequest in the hierarchy.
  FrescoImage(
    imageUrl = ...
  )
}
```

<img src="https://user-images.githubusercontent.com/24237865/131246748-b88903a1-43de-4e6c-9069-3e956a0cf8a6.gif" align="right" width="32%"/>

## Fresco Animated Image Support (GIF, Webp)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

Add the below dependency to your **module**'s `build.gradle` file.

```gradle
dependencies {
  implementation("com.github.skydoves:landscapist-fresco-websupport:$version")
}
```

You can load animated GIFs and WebP Images with `FrescoWebImage` composable function. You should pass the `AbstractDraweeController` like the following example below:

```kotlin
FrescoWebImage(
  controllerBuilder = {
      Fresco.newDraweeControllerBuilder()
          .setUri(poster.gif) // GIF or Webp image url.
          .setAutoPlayAnimations(true)
  },
  modifier = Modifier
    .fillMaxWidth()
    .height(300.dp)
)
```

For more details, check out [DraweeController](https://frescolib.org/docs/animations.html), and [Supported URIs](https://frescolib.org/docs/supported-uris.html) for setting URI addresses. Also, you can load general images (jpeg, png, etc) which can be loaded with `FrescoImage` by using `FrescoWebImage` and your custom controller.

</details>

## ImageOptions

You can give image options to your image composable functions by passing `ImageOptions` instance like the below:

你可以通过传递 `ImageOptions` 实例，为你的图像可组合函数提供图像选项，方法如下：

```kotlin
GlideImage(
  ..
  imageOptions = ImageOptions(
      contentScale = ContentScale.Crop,
      alignment = Alignment.Center,
      contentDescription = "main image",
      colorFilter = null,
      alpha = 1f
    )
)
```

### RequestSize

You can set the request size of your image by giving `requestSize` property as seen in the below:

你可以通过设置 `requestSize` 属性来指定图像的请求大小，方法如下：

```kotlin
GlideImage(
  ..
  imageOptions = ImageOptions(requestSize = IntSize(800, 600)),
)
```

## Listening image state changes

You can listen the image state changes by giving `onImageStateChanged` parameter to your image composable functions like the below:

你可以通过为图像可组合函数提供 `onImageStateChanged` 参数来监听图像状态的变化，方法如下：

```kotlin
GlideImage(
  ..
  onImageStateChanged = {
    when (it) {
      GlideImageState.None -> ..
      GlideImageState.Loading -> ..
      is GlideImageState.Success -> ..
      is GlideImageState.Failure -> ..
    }
  }
)
```

> **Note**: You can use `CoilImageState` for `CoilImage` and `FrescoImageState` for `FrescoImage`.
>
> 注意：你可以在 `CoilImage` 中使用 `CoilImageState`，在 `FrescoImage` 中使用 `FrescoImageState`。

### DataSource

For the success state, you can trace the origin of the image with the `DataSource` parameter. `DataSource` represents the following source origins below:

对于成功状态，你可以通过 `DataSource` 参数追踪图像的来源。`DataSource` 表示以下几种来源：

- **Memory**: Represents an in-memory data source or cache (e.g. bitmap, ByteBuffer). 表示内存中的数据源或缓存（例如，位图、`ByteBuffer`）。
- **Disk**: Represents a disk-based data source (e.g. drawable resource, or File). 表示基于磁盘的数据源（例如，drawable 资源或文件）。
- **Network**: Represents a network-based data source. 表示基于网络的数据源。
- **Unknown**: Represents an unknown data source. 表示未知的数据源。

<img src="https://user-images.githubusercontent.com/24237865/94174882-d6e1db00-fed0-11ea-86ec-671b5039b1b9.gif" align="right" width="310px"/>

## Custom Composables
You can execute your own composable functions depending on the three request states below:

你可以根据以下三种请求状态执行自定义的可组合函数：

- **loading**: Executed while loading an image. 在加载图像时执行。
- **success**: Executed if loading an image successfully. 在成功加载图像时执行。
- **failure**: Executed if fails to load an image (e.g. network error, wrong destination). 在加载图像失败时执行（例如，网络错误、目标地址错误）。

```kotlin
GlideImage( // CoilImage, FrescoImage
  imageModel = { imageUrl },
  modifier = modifier,
  // shows an indicator while loading an image.
  loading = {
    Box(modifier = Modifier.matchParentSize()) {
      CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
      )
    }
  },
  // shows an error text if fail to load an image.
  failure = {
    Text(text = "image request failed.")
  }
)
```

Also, you can customize the image content with our own composable function like the example below:

此外，你还可以通过我们自定义的可组合函数来定制图像内容，方法如下：

```kotlin
GlideImage( // CoilImage, FrescoImage
  imageModel = { imageUrl },
  success = { state, painter ->
    Image(
      painter = painter,
      modifier = Modifier.size(128.dp), // draw a resized image.
      contentDescription = "Image"
    )
  },
  ..  
)
```
> **Note**: You can also use the custom Composables for **`CoilImage`** and **`FrescoImage`**.
>
> 注意：你也可以在 `CoilImage` 和 `FrescoImage` 中使用自定义的可组合函数。

<img src="https://user-images.githubusercontent.com/24237865/148672035-6a82eba5-900c-44ee-a42c-acbf8038d0ab.png" align="right" width="46%">


## Preview on Android Studio
Landscapist supports preview mode for each image library; **Glide**, **Coil**, and **Fresco**. You can show the preview image on your editor with a `previewPlaceholder` parameter as following:

`Landscapist` 支持为每个图像库（Glide、Coil 和 Fresco）提供预览模式。你可以通过 `previewPlaceholder` 参数在编辑器中显示预览图像，方法如下：

```kotlin
GlideImage(
  imageModel = { imageUrl },
  modifier = Modifier.aspectRatio(0.8f),
  previewPlaceholder = painterResource(id = R.drawable.poster)
)
```
> **Note**: You can also use the the `previewPlaceholder` parameter for **`CoilImage`** and **`FrescoImage`**.
>
> 注意：你也可以在 `CoilImage` 和 `FrescoImage` 中使用 `previewPlaceholder` 参数。

## ImageComponent and ImagePlugin

You can compose supported image plugins by Landscapist or you can create your own image plugin that will be composed following the image loading state.
`ImagePlugin` is a pluggable compose interface that will be executed for loading images. `ImagePlugin` provides following types below:

你可以使用 `Landscapist` 提供的图像插件，或者创建自己的图像插件，这些插件将根据图像加载状态进行组合。`ImagePlugin` 是一个可插拔的组合接口，用于图像加载时的执行。`ImagePlugin` 提供以下类型：

- **PainterPlugin**: A pinter plugin interface to be composed with the given `Painter`. 一个与给定 `Painter` 组合的插件接口。
- **LoadingStatePlugin**: A pluggable state plugin that will be composed while the state is `ImageLoadState.Loading`. 一个可插拔的状态插件，当状态为 `ImageLoadState.Loading` 时将被组合。
- **SuccessStatePlugin**: A pluggable state plugin that will be composed when the state is `ImageLoadState.Success`. 一个可插拔的状态插件，当状态为 `ImageLoadState.Success` 时将被组合。
- **FailureStatePlugin**: A pluggable state plugin that will be composed when the state is `ImageLoadState.Failure`. 一个可插拔的状态插件，当状态为 `ImageLoadState.Failure` 时将被组合。

For example, you can implement your own `LoadingStatePlugin` that will be composed while loading an image like the below:

例如，你可以实现自己的 `LoadingStatePlugin`，该插件将在加载图像时被组合，方法如下：

```kotlin
data class LoadingPlugin(val source: Any?) : ImagePlugin.LoadingStatePlugin {

  @Composable
  override fun compose(
    modifier: Modifier,
    imageOptions: ImageOptions?
  ): ImagePlugin = apply {
    if (source != null && imageOptions != null) {
      ImageBySource(
        source = source,
        modifier = modifier,
        alignment = imageOptions.alignment,
        contentDescription = imageOptions.contentDescription,
        contentScale = imageOptions.contentScale,
        colorFilter = imageOptions.colorFilter,
        alpha = imageOptions.alpha
      )
    }
  }
}
```

Next, you can compose plugins by adding them in the `rememberImageComponent` like the below:

接下来，你可以通过将插件添加到 `rememberImageComponent` 中来组合它们，方法如下：

```kotlin
GlideImage(
  imageModel = { poster.image },
  component = rememberImageComponent {
    add(CircularRevealPlugin())
    add(LoadingPlugin(source))
  },
)
```

or you can just add plugins by using the **+** expression like the below:

或者你也可以通过使用 `+` 表达式来添加插件，方法如下：

```kotlin
GlideImage(
  imageModel = { poster.image },
  component = rememberImageComponent {
    +CircularRevealPlugin()
    +LoadingPlugin(source)
  },
)
```

### LocalImageComponent

You can provide the same `ImageComponent` instance in the composable hierarchy by using `imageComponent` extension and `LocalImageComponent` like the below:

你可以通过使用 `imageComponent` 扩展和 `LocalImageComponent`，在可组合层次结构中提供相同的 `ImageComponent` 实例，方法如下：

```kotlin
val component = imageComponent {
  +CrossfadePlugin()
  +PalettePlugin()
}

CompositionLocalProvider(LocalImageComponent provides component) {
  ..
}
```

## Placeholder

占位符

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

The `landscapist-placeholder` package provides useful image plugins, such as loading & failure placeholder supports and shimmering animation.
To use placeholder supports, add the dependency below:

`landscapist-placeholder` 包提供了实用的图像插件，例如加载中和加载失败时的占位图支持，以及闪光（shimmering）动画效果。要使用这些占位功能，请添加以下依赖：

```kotlin
dependencies {
    implementation("com.github.skydoves:landscapist-placeholder:$version")
}
```

<img src="https://user-images.githubusercontent.com/24237865/95812167-be3a4780-0d4f-11eb-9360-2a4a66a3fb46.gif" align="right" width="250px"/>

### ShimmerPlugin
You can implement a shimmering effect while loading an image by using the `ShimmerPlugin` as following the example below:

你可以通过以下示例使用 `ShimmerPlugin` 在加载图像时实现闪光效果（Shimmering Effect）：

```kotlin
GlideImage( // CoilImage, FrescoImage
  imageModel = { imageUrl },
  modifier = modifier,
  component = rememberImageComponent {
    // shows a shimmering effect when loading an image.
    +ShimmerPlugin(
      Shimmer.Flash(
        baseColor = Color.White,
        highlightColor = Color.LightGray,
      ),
    )
  },
  // shows an error text message when request failed.
  failure = {
    Text(text = "image request failed.")
  }
)
```
 > **Note**: You can also use the Shimmer effect for **`CoilImage`** and **`FrescoImage`**.
 >
 > 注意：你也可以在 `CoilImage` 和 `FrescoImage` 中使用闪光效果（Shimmer effect）。

`Shimmer` sealed class provides following the three different types: `Resonate`, `Fade`, and `Flash`.

`Shimmer` 密封类（sealed class）提供了以下三种不同类型的闪光效果：Resonate（共振型）Fade（淡入淡出型）Flash（闪烁型）

|                                                               Resonate                                                                |                                                                 Fade                                                                  |                                                                 Flash                                                                 |
|:-------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/skydoves/landscapist/assets/24237865/bf8f6d04-2e30-44a5-ba9d-9e706af15a09" align="center" width="100%"/> | <img src="https://github.com/skydoves/landscapist/assets/24237865/add42855-9e71-4222-b41e-aa9cfa7f0ce3" align="center" width="100%"/> | <img src="https://github.com/skydoves/landscapist/assets/24237865/c79bcad3-bd30-4b63-b3f8-5b3bdd7c561a" align="center" width="100%"/> |

### PlaceholderPlugin

You can show your own placeholder while loading an image or when fails to load an image with `PlaceholderPlugin.Loading` and `PlaceholderPlugin.Failure`.

你可以使用 `PlaceholderPlugin.Loading` 和 `PlaceholderPlugin.Failure` 显示自定义的占位图，分别用于图像加载中或加载失败的情况。

```kotlin
GlideImage(
  ..
  component = rememberImageComponent {
      +PlaceholderPlugin.Loading(painterResource(id = R.drawable.placeholder_loading))
      +PlaceholderPlugin.Failure(painterResource(id = R.drawable.placeholder_failure))
    },
)
```

> **Note**: The source should be one of `ImageBitmap`, `ImageVector`, or `Painter`.
>
> 注意：源应该是 `ImageBitmap`、`ImageVector` 或 `Painter` 之一。

### ThumbnailPlugin

<img src="https://github.com/skydoves/landscapist/assets/24237865/dad9db76-31c5-453a-98a8-f3dfd3103993" align="right" width="250px" />

Landscapist supports the thumbnail feature, which pre-loads and displays small sizes of images while loading the original image. So you can make users feel images loading faster and give images a nature loading effect while displaying an original image.
To show thumbnail, add the image plugin into your image component like the example below:

`Landscapist` 支持缩略图功能，在加载原始图像时预加载并显示图像的较小版本。这样，你可以让用户感觉图像加载更快，并在显示原始图像时提供自然的加载效果。

要显示缩略图，请像下面的示例一样将图像插件添加到你的图像组件中：

```kotlin
GlideImage(
  ..,
  component = rememberImageComponent {
      +ThumbnailPlugin() 
  },
)
```

You can also adjust the request sizes by giving the `requestSize` parameter:

你也可以通过提供 `requestSize` 参数来调整请求的图像大小：

```kotlin
component = rememberImageComponent {
    +ThumbnailPlugin(IntSize(30 ,30)) 
},
```

> **Note**: It's highly recommended to use a small size of the request size on the thumbnail plugin to load the pre-load images process faster.
>
> 注意：强烈建议在缩略图插件中使用较小的请求大小，以加快预加载图像的过程。


## Animation

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

The `landscapist-animation` package provides useful image plugins related to animations, such as crossfade and circular reveal animation.
To use animation supports, add the dependency below:

`landscapist-animation` 包提供了与动画相关的有用图像插件，如交叉淡入和圆形揭示动画。要使用动画支持，请在项目中添加以下依赖：

```kotlin
dependencies {
    implementation("com.github.skydoves:landscapist-animation:$version")
}
```

### Preview

|                                                                Circular Reveal                                                                 |                                                                   Crossfade                                                                    |
|:----------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://user-images.githubusercontent.com/24237865/189552544-5f8e1209-4930-45e6-a050-3a0cda088e9f.gif" align="center" width="100%"/> | <img src="https://user-images.githubusercontent.com/24237865/189552547-d933cee7-e811-4170-a806-1ac165e8f055.gif" align="center" width="100%"/> | 


### Crossfade Animation

You can implement the crossfade animation while drawing images with `CrossfadePlugin` as the following:

你可以通过如下方式使用 `CrossfadePlugin` 在绘制图像时实现交叉淡入动画：

```kotlin
GlideImage(
  imageModel = { poster.image },
  component = rememberImageComponent {
    +CrossfadePlugin(
      duration = 550
    )
  }
)
```

 > **Note**: You can also use the crossfade animation for **`CoilImage`** and **`FrescoImage`**.
 >
 > 注意：你也可以在 `CoilImage` 和 `FrescoImage` 中使用交叉淡入动画。

### Circular Reveal Animation

圆形揭示动画

You can implement the circular reveal animation while drawing images with `CircularRevealplugin` as the following:

你可以通过以下方式使用 **CircularRevealPlugin** 在绘制图像时实现圆形揭示动画效果：

```kotlin
GlideImage(
  imageModel = { poster.image },
  component = rememberImageComponent {
    +CircularRevealPlugin(
      duration = 350
    )
  }
)
```

 > **Note**: You can also use the Circular Reveal animation for **`CoilImage`** and **`FrescoImage`**.
 >
 > 注意：你也可以在 `CoilImage` 和 `FrescoImage` 中使用圆形揭示动画。

 ## Transformation

图像转换

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)

<img src="https://user-images.githubusercontent.com/24237865/196038507-54a3a79c-2e8e-45ec-b5e8-5de65cd50248.png" align="right" width="250"/>

The `landscapist-transformation` package provides useful image transformation plugins, such as the blur effect.
To use transformation supports, add the dependency below:

**landscapist-transformation** 包提供了有用的图像转换插件，例如模糊效果。
 若要使用转换功能，请添加以下依赖项：

```kotlin
dependencies {
    implementation("com.github.skydoves:landscapist-transformation:$version")
}
```

### BlurTransformationPlugin

You can implement the blur effect with `BlurTransformationPlugin` as the following:

你可以通过以下方式使用 **BlurTransformationPlugin** 实现模糊效果：

```kotlin
GlideImage( // CoilImage, FrescoImage also can be used.
  imageModel = { poster.image },
  component = rememberImageComponent {
      +BlurTransformationPlugin(radius = 10) // between 0 to Int.MAX_VALUE.
  }
)
```

>**Note**: Landscapist's blur transformation falls back onto a CPU-based implementation to support older API levels. So you don't need to worry about API compatibilities and performance issues.
>
>注意：**Landscapist** 的模糊转换会回退到基于 CPU 的实现，以支持较旧的 API 级别。因此，你无需担心 API 兼容性和性能问题。

## Palette

**调色板**

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

The `landscapist-palette` package provides useful image plugins related to palette, such as extracting primary color sets.
To use palette supports, add the dependency below:

**landscapist-palette** 包提供了与调色板（palette）相关的实用图像插件，例如提取主色调集合。
 若要使用调色板相关的功能，请添加以下依赖项：

```kotlin
dependencies {
    implementation("com.github.skydoves:landscapist-palette:$version")
}
```

You can extract primary (theme) color profiles with `PalettePlugin`. You can check out [Extract color profiles](https://developer.android.com/training/material/palette-colors#extract-color-profiles) to see what kinds of colors can be extracted.

你可以使用 **PalettePlugin** 提取主色（主题色）配置。
你可以查看 **Extract color profiles** 来了解可以提取哪些类型的颜色。

<img src="https://user-images.githubusercontent.com/24237865/129226361-877689b8-a1ec-4f59-b8a6-e2efe33a8de7.gif" align="right" width="250"/>

```kotlin
var palette by rememberPaletteState(null)

GlideImage( // CoilImage, FrescoImage also can be used.
  imageModel = { poster.image },
  component = rememberImageComponent {
      +PalettePlugin { palette = it }
  }
)

Crossfade(
  targetState = palette,
  modifier = Modifier
    .padding(horizontal = 8.dp)
    .size(45.dp)
) {
  Box(
    modifier = Modifier
      .background(color = Color(it?.lightVibrantSwatch?.rgb ?: 0))
      .fillMaxSize()
  )
}
```

Also, you can customize attributes of `PalettePlugin` like the example below:

此外，你还可以自定义 **PalettePlugin** 的属性，如下面的示例所示：

```kotlin
var palette by remember { mutableStateOf<Palette?>(null) }

GlideImage( // CoilImage, FrescoImage also can be used.
  imageModel = { poster.image },
  component = rememberImageComponent {
    +PalettePlugin(
      imageModel = poster.image,
      useCache = true, // use cache strategies for the same image model.
      interceptor = {
        it.addFilter { rgb, hsl ->
          // here edit to add the filter colors.
          false
        }
      },
      paletteLoadedListener = {
        palette = it
      }
    )
  }
)
```
 > **Note**: You can also use the Palette for **`CoilImage`** and **`FrescoImage`**.
 >
 > 注意：你也可以将 **Palette** 用于 **CoilImage** 和 **FrescoImage**。

 ## BOM

 [![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/landscapist.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=skydoves%2520landscapist)<br>

 The landscapist Bill of Materials (BOM) lets you manage all of your landscapist library versions by specifying only the BOM’s version.

Landscapist 的 BOM（版本清单）允许你只需指定 BOM 的版本，就能统一管理所有 landscapist 库的版本。

 ```kotlin
dependencies {
    // Import the landscapist BOM
    implementation("com.github.skydoves:landscapist-bom:$version")

    // Import landscapist libraries
    implementation("com.github.skydoves:landscapist-glide") // fresco or coil
    implementation("com.github.skydoves:landscapist-placeholder")
    implementation("com.github.skydoves:landscapist-palette")
    implementation("com.github.skydoves:landscapist-transformation")
}
 ```

 ## Taking Snapshot Images With Paparazzi

使用 **Paparazzi** 进行快照图片捕获（或拍摄快照图像）

[Paparazzi](https://github.com/cashapp/paparazzi) allows you to take snapshot images of your Composable functions without running them on physical devices. You can take proper snapshots images about your images with Paparazzi like the below:

**Paparazzi** 允许你在不依赖实体设备的情况下，为你的 Composable 函数拍摄快照图像。你可以使用 Paparazzi 生成图像的规范快照，就像下面这样：

```kotlin
paparazzi.snapshot {
  CompositionLocalProvider(LocalInspectionMode provides true) {
    GlideImage(
      modifier = Modifier.fillMaxSize(),
      imageModel = { ".." },
      previewPlaceholder = painterResource(R.drawable.placeholder)
  )
 }
}
```

## Who's using Landscapist?
If your project uses Landscapist, please let me know by creating a new issue! 🤗

如果你的项目使用了 **Landscapist**，请通过创建一个新 issue 告诉我吧！🤗

## [Twitter for Android](https://user-images.githubusercontent.com/24237865/125583736-f0ffa76f-8f87-433b-a9fd-192231dc5e63.jpg)

[![twitter](https://user-images.githubusercontent.com/24237865/125583182-9527dd48-433e-4e17-ae52-3f2bb544a847.jpg)](https://play.google.com/store/apps/details?id=com.twitter.android&hl=ko&gl=US)

## Inspiration

灵感

This library was mostly inspired by [Accompanist](https://github.com/chrisbanes/accompanist).

这个库主要受到了 **Accompanist** 的启发。

> Accompanist is a group of libraries that contains some utilities which I've found myself copying around projects which use Jetpack Compose. Currently, it contains image loading and insets. You can get more variety and recent systems from the library maintained by Google.
>
> **Accompanist** 是一组库，包含了一些我经常在使用 Jetpack Compose 的项目中复制来复制去的实用工具。目前，它包括图像加载和系统内边距（insets）处理。你可以在由 Google 维护的这个库中获得更多种类和更新的功能。

## Find this repository useful? :heart:

觉得这个仓库有用吗？❤️

Support it by joining __[stargazers](https://github.com/skydoves/Landscapist/stargazers)__ for this repository. :star:

通过为这个仓库点亮星标来支持它吧。⭐ 

Also __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

也请关注我，了解我的下一个作品！🤩

# License
```xml
Designed and developed by 2020 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
