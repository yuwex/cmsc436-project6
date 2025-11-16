package com.example.project6

import android.provider.ContactsContract
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL

class ServerTaskInsert : Thread {

    lateinit var candy : Candy
    var result = ""

    constructor(candy: Candy) {
        this.candy = candy
    }

    override fun run() {
        super.run()
        try {
            var url = URL(MainActivity.URL_INSERT)

            var connection = url.openConnection()
            connection.doOutput = true
            var os = connection.outputStream
            var osw = OutputStreamWriter(os)

            var data = "id=${candy.getId()}&name=${candy.getName()}&price=${candy.getPrice()}"
            osw.write(data)
            osw.flush()

            // validate output
            var iStream : InputStream = connection.inputStream
            var isr : InputStreamReader = InputStreamReader( iStream )
            var br : BufferedReader = BufferedReader( isr )
            var line : String? = br.readLine()
            while( line != null ) {
                result += line
                line = br.readLine()
            }
            Log.i("Insert", result)

        } catch (e: Exception) {
           Log.e("Insert", "e: " + e.message.toString())
        }
    }

}