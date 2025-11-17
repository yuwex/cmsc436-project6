package com.example.project6

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.title = "Candy Store"
        val relativeLayout = RelativeLayout(this)

        val scrollView = ScrollView(this)

        val radioGroup = RadioGroup(this)
        radioGroup.orientation = LinearLayout.VERTICAL

        for (candy in MainActivity.candies) {
            val btn = RadioButton(this)
            btn.text = candy.toStringNoId()
            btn.tag = candy
            radioGroup.addView(btn)
        }

        radioGroup.setOnCheckedChangeListener { _, id ->
            val btn = findViewById<RadioButton>(id)
            val candyId = (btn.tag as? Candy)!!.getId()
            val task = ServerTaskDelete(this, candyId)
            task.start()

            radioGroup.removeView(btn)
        }

        scrollView.addView(radioGroup)

        val scrollParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        scrollParams.topMargin = 250

        relativeLayout.addView(scrollView, scrollParams)

        val btn = Button(this)
        btn.text = "BACK"
        btn.setOnClickListener { this.finish() }

        val btnParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        btnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        btnParams.bottomMargin = 50
        relativeLayout.addView(btn, btnParams)

        setContentView(relativeLayout)
    }

    fun updateView() {

    }

}