package com.gunna.okssepoc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import okhttp3.Request
import okhttp3.Response


class MainActivity : AppCompatActivity() {

    private var counter: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<View>(R.id.fab).setOnClickListener {
            configureSse()
        }
    }

    private fun notifyEvent(msg: String){
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
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
                return false
            }

            override fun onRetryError(sse: ServerSentEvent?, throwable: Throwable?, response: Response?): Boolean {
                return false
            }

            override fun onClosed(sse: ServerSentEvent?) {
            }

            override fun onPreRetry(sse: ServerSentEvent?, originalRequest: Request?): Request {
                return Request.Builder().build()
            }
        })

    }

}