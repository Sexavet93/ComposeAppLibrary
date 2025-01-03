package com.compose.composelibrary

import android.content.Context
import android.content.Intent
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

object ChannelHandler {

    val CHANNEL: String = "com.example.my_flutter_channel"
    val CHANNEL2: String = "com.example.my_flutter_channel2"
    private lateinit var channel: MethodChannel
    lateinit var invokeChannel: MethodChannel

    fun setChannel(flutterEngine: FlutterEngine, activityContext: Context) {

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        invokeChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL2)

        channel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
            if (call.method == "androidMethod") {
                println("android method called")
                activityContext.startActivity(Intent(activityContext, MainActivity2::class.java))
                result.success("success")
            } else {
                result.notImplemented()
            }
        }

    }
}