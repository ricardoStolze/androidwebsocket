package com.example.fesricardostolze

import android.util.Log
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

object SocketUtils {
    val wsUrl = "ws.//ubuntu01.fes-sport.de:10001"
    fun webSocketConnection(userEndUpdateListener: (String) -> Unit): WebSocket {
        val client = OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
        val request = Request.Builder().url(wsUrl).build()

        val webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("webSocket", "open new connection")
                super.onOpen(webSocket, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("webSocket", "message received: $text")
                userEndUpdateListener(text)
                super.onMessage(webSocket, text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("webSocket", "message received: $bytes")
                super.onMessage(webSocket, bytes)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("webSocket", "closing")
                super.onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("webSocket", "closed")
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
            }
        })

        client.dispatcher.executorService.shutdown()

        return webSocket
    }
}