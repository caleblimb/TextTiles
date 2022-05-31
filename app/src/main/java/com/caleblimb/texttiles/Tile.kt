/**
 * Author: Caleb Limb
 * Purpose: Data Class to Handle Individual Tiles
 */
package com.caleblimb.texttiles
import android.content.Context

// Todo: Add coordinates for each tile
class Tile(val value: Char, val points: Int, val sprite: Char) {

    // Static map that translates the sprite to the resource id for its corresponding image
    companion object {
        val spriteMap = mapOf<Char, Int>(
            'a' to R.drawable.a,
            'A' to R.drawable.a,
            'b' to R.drawable.b,
            'B' to R.drawable.b,
            'c' to R.drawable.c,
            'C' to R.drawable.c,
            'd' to R.drawable.d,
            'D' to R.drawable.d,
            'e' to R.drawable.e,
            'E' to R.drawable.e,
            'f' to R.drawable.f,
            'F' to R.drawable.f,
            'g' to R.drawable.g,
            'G' to R.drawable.g,
            'h' to R.drawable.h,
            'H' to R.drawable.h,
            'i' to R.drawable.i,
            'I' to R.drawable.i,
            'j' to R.drawable.j,
            'J' to R.drawable.j,
            'k' to R.drawable.k,
            'K' to R.drawable.k,
            'l' to R.drawable.l,
            'L' to R.drawable.l,
            'm' to R.drawable.m,
            'M' to R.drawable.m,
            'n' to R.drawable.n,
            'N' to R.drawable.n,
            'o' to R.drawable.o,
            'O' to R.drawable.o,
            'p' to R.drawable.p,
            'P' to R.drawable.p,
            'q' to R.drawable.q,
            'Q' to R.drawable.q,
            'r' to R.drawable.r,
            'R' to R.drawable.r,
            's' to R.drawable.s,
            'S' to R.drawable.s,
            't' to R.drawable.t,
            'T' to R.drawable.t,
            'u' to R.drawable.u,
            'U' to R.drawable.u,
            'v' to R.drawable.v,
            'V' to R.drawable.v,
            'w' to R.drawable.w,
            'W' to R.drawable.w,
            'x' to R.drawable.x,
            'X' to R.drawable.x,
            'y' to R.drawable.y,
            'Y' to R.drawable.y,
            'z' to R.drawable.z,
            'Z' to R.drawable.z,
        )
    }

    fun getID() : Int {
        return if (spriteMap[sprite] == null)
            R.drawable.blank
        else spriteMap[sprite]!!
    }
}