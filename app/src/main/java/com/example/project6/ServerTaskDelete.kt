package com.example.project6

import android.util.Log
import java.net.URL

class ServerTaskDelete : Thread {

    private var result = ""
    private var activity: DeleteActivity
    private var id = 0

    constructor(activity: DeleteActivity, id: Int) {
        this.activity = activity
        this.id = id
    }


    override fun run() {
        super.run()

        try {
            val url = URL("${MainActivity.URL_DELETE}?id=$id")
            url.openStream()
        } catch (e: Exception) {
            result = e.message.toString()
            Log.w("MainActivity", "e: ${e.message}")
        }

        activity.runOnUiThread { activity.updateView() }
    }



}