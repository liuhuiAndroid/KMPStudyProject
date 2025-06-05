package com.protobuf.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.protobuf.test.ui.theme.ProtobufTestTheme
import erdai.StudentOuterClass
import erdai.student

/**
 * https://juejin.cn/post/7135365943282122765
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProtobufTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        greeting = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(greeting: String, modifier: Modifier = Modifier) {
    Button(onClick = {
        val student0 = student {
            name = "erdai"
            age = 18
            email = "erdai666@qq.com"
            course += "Math"
            course += "English"
            course += "Computer"
        }
        val student = StudentOuterClass.Student.newBuilder().setName("erdai").setAge(18)
            .setEmail("erdai666@qq.com").addAllCourse(mutableListOf("Math", "English", "Computer"))
            .build()
        Log.i("TAG", "原始对象:$student")
        // 2. 序列化为字节数组
        val byteArray: ByteArray = student.toByteArray()
        Log.i("TAG", "序列化为字节数组:${String(byteArray)}")
        // 3. 反序列化为 Protobuf 对象
        val parsedStudent = StudentOuterClass.Student.parseFrom(byteArray)
        // Log.i("TAG", "反序列化对象:$parsedStudent")
        Log.i("TAG", "反序列化对象name:${parsedStudent.name}")
        Log.i("TAG", "反序列化对象age:${parsedStudent.age}")
    }, modifier = modifier.height(48.dp)) {
        Text(
            text = "Hello $greeting!"
        )
    }
}