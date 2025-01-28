package com.example.fesricardostolze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.appcompat.app.AppCompatActivity
import com.example.fesricardostolze.ui.theme.FESRicardoStolzeTheme
import com.example.fesricardostolze.databinding.ActivityMainBinding
import android.app.ProgressDialog
import androidx.core.view.isVisible
import com.example.fesricardostolze.SocketUtils
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBarDialog = ProgressDialog(this)

        with(binding) {
            btnConnect.setOnClickListener {
                progressBarDialog.apply {
                    setTitle("Connecting")
                    setMessage("Please wait...")
                    show()
                }

                webSocket = SocketUtils.webSocketConnection {
                    runOnUiThread {
                        initView(isConnected = true)
                        progressBarDialog.cancel()
                        tvReplyMsg.text = it
                    }
                }
                }
            }
        }

        /*setContent {
            FESRicardoStolzeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }*/

    private fun initView(isConnected:Boolean = false) {
        with(binding) {
            if (isConnected) {
                btnConnect.isVisible = !isConnected
                tvReplyMsg.isVisible = isConnected
                tv6.isVisible = isConnected
                tvStatus.text = if (isConnected) "Status: Connected" else "Status: Not connected"
            }
        }

    }
}
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FESRicardoStolzeTheme {
        Greeting("Android")
    }
}
*/
