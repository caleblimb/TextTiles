/**
 * Author: Caleb Limb
 * Purpose: Handle language dictionaries and word related functions such as
 * checking if a given String is a word or not.
*/
package com.caleblimb.texttiles

import android.content.Context
import java.io.IOException
import java.io.InputStream

class Dictionary(context: Context) {
    private val fileName: String = "dictionaries/english3.txt"
    private val words: Set<String>

    init {
        /* Convert file to String*/
        var rawText: String = ""
        val delimiter = "\n"
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            rawText = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        /* Convert String to Set */
        words = rawText.lowercase().split(delimiter).toSet()
    }

    fun isWord(word: String): Boolean {
        return (word.lowercase() in words)
    }

}