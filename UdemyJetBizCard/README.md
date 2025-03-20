| **Material2 (旧 API)**          | **Material3 (新 API)**                  |
|--------------------------------|----------------------------------|
| `MaterialTheme.typography.button` | `MaterialTheme.typography.labelLarge` |
| `MaterialTheme.typography.body2` | `MaterialTheme.typography.bodyMedium` |
| `MaterialTheme.typography.h4` | `MaterialTheme.typography.headlineLarge` |
| `MaterialTheme.typography.subtitle1` | `MaterialTheme.typography.titleMedium` |
| `MaterialTheme.colors.surface` | `MaterialTheme.colorScheme.surface` |
| `MaterialTheme.colors.onSurface.copy(alpha = 0.5f)` | `MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)` |
| `MaterialTheme.colors.primaryVariant` | `MaterialTheme.colorScheme.secondary` 或 `tertiary` |

### **Material2 到 Material3 API 变更对照表**
| **Material2 (旧 API)**            | **Material3 (新 API)**                      | **属性说明** |
|--------------------------------|----------------------------------|-----------------------------|
| `MaterialTheme.typography.button` | `MaterialTheme.typography.labelLarge` | 按钮文字样式 |
| `MaterialTheme.typography.body2` | `MaterialTheme.typography.bodyMedium` | 正文文字（较小） |
| `MaterialTheme.typography.body1` | `MaterialTheme.typography.bodyLarge` | 正文文字（较大） |
| `MaterialTheme.typography.h4` | `MaterialTheme.typography.headlineLarge` | 主要标题（大） |
| `MaterialTheme.typography.h5` | `MaterialTheme.typography.headlineMedium` | 主要标题（中） |
| `MaterialTheme.typography.h6` | `MaterialTheme.typography.headlineSmall` | 主要标题（小） |
| `MaterialTheme.typography.subtitle1` | `MaterialTheme.typography.titleMedium` | 副标题（中等大小） |
| `MaterialTheme.typography.subtitle2` | `MaterialTheme.typography.titleSmall` | 副标题（较小） |
| `MaterialTheme.colors.primary` | `MaterialTheme.colorScheme.primary` | 主要品牌色 |
| `MaterialTheme.colors.primaryVariant` | `MaterialTheme.colorScheme.secondary` 或 `tertiary` | 主要品牌色的变体 |
| `MaterialTheme.colors.secondary` | `MaterialTheme.colorScheme.secondary` | 次要品牌色 |
| `MaterialTheme.colors.surface` | `MaterialTheme.colorScheme.surface` | 组件的背景色 |
| `MaterialTheme.colors.background` | `MaterialTheme.colorScheme.background` | 整体背景色 |
| `MaterialTheme.colors.error` | `MaterialTheme.colorScheme.error` | 错误状态颜色 |
| `MaterialTheme.colors.onPrimary` | `MaterialTheme.colorScheme.onPrimary` | 主要品牌色上的文本颜色 |
| `MaterialTheme.colors.onSecondary` | `MaterialTheme.colorScheme.onSecondary` | 次要品牌色上的文本颜色 |
| `MaterialTheme.colors.onSurface` | `MaterialTheme.colorScheme.onSurface` | 组件背景色上的文本颜色 |
| `MaterialTheme.colors.onBackground` | `MaterialTheme.colorScheme.onBackground` | 整体背景色上的文本颜色 |
| `MaterialTheme.colors.onError` | `MaterialTheme.colorScheme.onError` | 错误状态上的文本颜色 |
| `MaterialTheme.colors.onSurface.copy(alpha = 0.5f)` | `MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)` | 半透明的文本颜色 |
| `MaterialTheme.shapes.small` | `MaterialTheme.shapes.extraSmall` | 小组件的圆角形状 |
| `MaterialTheme.shapes.medium` | `MaterialTheme.shapes.small` | 中等大小组件的圆角 |
| `MaterialTheme.shapes.large` | `MaterialTheme.shapes.medium` | 大组件的圆角 |
| `elevation = 4.dp` (Card, Surface) | `shadowElevation = 4.dp`（Surface），`elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)`（Card） | 控制阴影高度 |
