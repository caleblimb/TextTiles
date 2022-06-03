package com.caleblimb.texttiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val game : CanvasManager = CanvasManager(this)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        buttonStart.setOnClickListener() {
            setContentView(game)
        }

//        val intent = Intent(this@MainActivity, Game::class.java)
//        val players: MutableList<String> = ArrayList<String>()
//        intent.putStringArrayListExtra("PLAYERS", players as ArrayList<String>)
//        startActivity(intent)
    }
}
