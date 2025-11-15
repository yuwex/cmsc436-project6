package com.example.project6
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_insert)

        // handle adding candies
        val addButton = findViewById<Button>(R.id.add)
        addButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.candy_name).text.toString()
            val priceString = findViewById<EditText>(R.id.candy_price).text.toString()

            if (!name.isEmpty()) {
                try {
                    val price = priceString.toFloat()
                    // COMMUNICATE W SERVER
                    Log.d("InsertActivity", "name: $name price: $price")

                } catch (nfe: NumberFormatException) {
                    Log.d("InsertActivity", "invalid input")
                    // do nothing
                }
            }
        }

        // handle back
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            this.finish()
        }
    }


}