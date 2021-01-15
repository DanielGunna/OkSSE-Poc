package com.gunna.okssepoc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import okhttp3.Request
import okhttp3.Response


class MainActivity : AppCompatActivity() {


    private val tvMessage  by lazy {   findViewById<TextView>(R.id.tvMessage)}
    private var counter: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<View>(R.id.fab).setOnClickListener {
            configureSse()
        }
    }

    private fun notifyEvent(serverMessage: String){
        Log.e("SSE", serverMessage)
        runOnUiThread {
           tvMessage.text = serverMessage
        }
    }

    private fun configureSse() {
        val request: Request = Request.Builder().method("GET", null).url("http://10.0.2.2:3000/events").build()
        val okSse = OkSse()
        okSse.newServerSentEvent(request, object : ServerSentEvent.Listener {
            override fun onOpen(sse: ServerSentEvent?, response: Response?) {
                notifyEvent("Conexão aberta ")
            }

            override fun onMessage(sse: ServerSentEvent?, id: String?, event: String?, message: String?) {
                notifyEvent("Mensagem recebida $message")
            }

            override fun onComment(sse: ServerSentEvent?, comment: String?) {
                notifyEvent("Comment recebido $comment")
            }

            override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean {
                return true
            }

            override fun onRetryError(sse: ServerSentEvent?, throwable: Throwable?, response: Response?): Boolean {
                return true
            }

            override fun onClosed(sse: ServerSentEvent?) {
                notifyEvent("Closed")
            }

            override fun onPreRetry(sse: ServerSentEvent?, originalRequest: Request?): Request {
                return request
            }
        })

    }

}