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

        //toastTotal() // Toast total
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
        if (item.itemId == R.id.action_reset) {
            total = 0.0
        } else if (item.itemId == R.id.action_insert) {
            this.startActivity(Intent(this, InsertActivity::class.java))
        } else { // delete
            this.startActivity(Intent(this, DeleteActivity::class.java))
        }

        return true
    }

    companion object {
        const val URL_ALL : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/selectAll.php"
        const val URL_DELETE : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/delete.php"
        const val URL_INSERT : String = "https://cmsc436-2301.cs.umd.edu/cmsc436/project6/insert.php"
        var candies : ArrayList<Candy> = ArrayList<Candy>( )
    }
}