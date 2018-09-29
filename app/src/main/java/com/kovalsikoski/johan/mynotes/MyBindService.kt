package com.kovalsikoski.johan.mynotes

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*


class MyBindService: Service() {

    internal inner class MyBinder : Binder() {
        val service: MyBindService
            get() = this@MyBindService
    }

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }

    fun generateNumber(): Int = Random().nextInt(100)

}