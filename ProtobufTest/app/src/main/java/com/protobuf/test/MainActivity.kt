package com.protobuf.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.protobuf.test.ui.theme.ProtobufTestTheme
import erdai.StudentOuterClass

/**
 * https://juejin.cn/post/7135365943282122765
 * https://www.youtube.com/watch?v=LtvDeSX5U0s
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProtobufTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Button(onClick = {
//        val student = student {
//            this.name = "erdai"
//            this.age = 18
//            this.email = "erdai666@qq.com"
//            this.course += "Math"
//            this.course += "English"
//            this.course += "Computer"
//        }
        val student = StudentOuterClass.Student.newBuilder()
            .setName("erdai")
            .setAge(18)
            .setEmail("erdai666@qq.com")
            .addAllCourse(mutableListOf("Math", "English", "Computer"))
            .build()
        Log.i("TAG", "原始对象:$student")
        // 2. 序列化为字节数组
        val byteArray: ByteArray = student.toByteArray()
        Log.i("TAG", "序列化为字节数组:$student")

        // 3. 反序列化为 Protobuf 对象
        val parsedStudent = StudentOuterClass.Student.parseFrom(byteArray)
        Log.i("TAG", "反序列化对象:$parsedStudent")
    }) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}