package com.doron.personalnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    var prgs_bar: ProgressBar? = null
    var i = 0
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prgs_bar = findViewById<ProgressBar>(R.id.progess)

        handler.postDelayed(Runnable() {
            startActivity(Intent(this@SplashActivity, signup::class.java))
            finish()
        }, 2000)
    }
}

