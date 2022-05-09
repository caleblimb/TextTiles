package com.caleblimb.texttiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dictionary = Dictionary(this)
        val result1 = dictionary.isWord("apple")
        val result2= dictionary.isWord("apples")
        val result3 = dictionary.isWord("appler")
        val result4 = dictionary.isWord("appleg")
    }
}
