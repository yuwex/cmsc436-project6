package com.example.project6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.project6.databinding.ActivityMainBinding
import android.widget.GridLayout
import androidx.appcompat.widget.AppCompatButton
import android.content.Context
import android.util.AttributeSet



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var buttonWidth : Int = 0
    private var total : Double = 0.0
    private lateinit var scrollView : ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        scrollView = findViewById(R.id.scroll_view)

        bringMenuToFront()
    }

    override fun onResume() {
        super.onResume()

        val task = ServerTaskSelect(this)
        task.start()
    }

    fun bringMenuToFront( ) {
        val parent : ViewParent = scrollView.parent
        val drawerLayout : DrawerLayout = parent as DrawerLayout

        for ( i in 0..<drawerLayout.childCount) {
            val view : View = drawerLayout.getChildAt( i )

            if (view is CoordinatorLayout) {
                view.bringToFront()
            }
        }
    }

    fun loadData(candies: Array<Candy>) {
        MainActivity.candies = ArrayList(candies.toList())
        Log.w("MainActivity", "Candies: ${candies.contentToString()}")

        buildCandyButtons()
    }

    private fun buildCandyButtons() {
        total = 0.0

        scrollView.removeAllViews()

        val gridLayout = GridLayout(this).apply {
            columnCount = 2
            rowCount = (candies.size + 1) / 2
            setPadding(0, 200, 0, 0)
        }


        scrollView.post {
            val availableWidth = scrollView.width
            buttonWidth = availableWidth / 2

            for (candy in candies) {
                Log.w("ManActivity", candy.toString())
                val button = CandyButton(this, candy)

                val params = GridLayout.LayoutParams().apply {
                    width = buttonWidth
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    setMargins(8, 8, 8, 8)
                }
                button.layoutParams = params

                button.setOnClickListener({
                    total += candy.getPrice()
                    toastTotal()
                })

                gridLayout.addView(button)
            }


            scrollView.addView(gridLayout)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun toastTotal() {
        Toast.makeText(this, "\$$total", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_reset) {
            total = 0.0
            toastTotal()
            return true
        } else if (id == R.id.action_insert) {
            startActivity(Intent(this, InsertActivity::class.java))
            return true
        } else {
            startActivity(Intent(this, DeleteActivity::class.java))
            return true
        }
    }

    companion object {
        const val URL_ALL : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/selectAll.php"
        const val URL_DELETE : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/delete.php"
        const val URL_INSERT : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/insert.php"
        var candies : ArrayList<Candy> = ArrayList<Candy>( )
    }
}

class CandyButton : AppCompatButton {

    lateinit var candy: Candy

    constructor(context: Context, candy: Candy) : super(context) {
        bindCandy(candy)
    }

    fun bindCandy(c: Candy) {
        candy = c
        text = "${candy.getName()}\n$${String.format("%.2f", candy.getPrice())}"
    }

}
