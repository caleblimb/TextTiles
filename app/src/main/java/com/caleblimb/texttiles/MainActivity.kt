package com.caleblimb.texttiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.text.isDigitsOnly
import java.lang.NullPointerException
import java.lang.NumberFormatException
import android.util.Log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        buttonStart.setOnClickListener() {
            val intent = Intent(this@MainActivity, Game::class.java)

//            Pass the user inputted seed as well, with some error handling
            var num : String
            val input = findViewById<EditText>(R.id.Seed).text.toString()
            num = if (input == null || input == "") {
//                no seed was typed
                Random.nextInt(0, 99999999).toString()
            } else{
        //      Seed was typed
                input
            }

            var seed : String = num
            seed = (seed.toLong() % 1000000).toString()

            intent.putExtra("seed", seed)

//            Use this to show seed before sending it
//            Log.d("-----------------------", seed)
            startActivity(intent)
        }
    }
}
