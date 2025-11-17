package com.example.project6

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.Scanner

class ServerTaskSelect : Thread {

    private var result = ""
    private var activity: MainActivity

    constructor(activity: MainActivity) {
        this.activity = activity
    }


    override fun run() {
        super.run()

        try {
            val url = URL(MainActivity.URL_ALL);
            val stream = url.openStream()
            val scan = Scanner(stream)
            while (scan.hasNext()) {
                result += scan.nextLine()
            }
        } catch (e: Exception) {
            result = e.message.toString()
            Log.w("MainActivity", "e: ${e.message}")
        }

        activity.runOnUiThread { activity.loadData(parseJson(result)) }
    }

    fun parseJson(json: String): Array<Candy> {
        var result: String = ""
        try {
            val json = JSONArray(json)

            return Array<Candy>(json.length(), { i ->
                val obj = json.get(i)
                if (obj is JSONObject) {
                    Candy(obj.getInt("id"), obj.getString("name"), obj.getDouble("price"))
                } else {
                    throw JSONException("Expected JSON object")
                }
            })

        } catch (e: JSONException) {
            result = e.message.toString()
            Log.w("MainActivity", "Error: $result")
        }
        throw JSONException("Expected JSON list")
    }

}