/**
 * Author: Caleb Limb
 * Purpose: Draw the game to the screen and process user inputs
 */
package com.caleblimb.texttiles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat

class Game(context: Context) : View(context) {
    private val primaryColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
    private val secondaryColor = ResourcesCompat.getColor(resources, R.color.colorSecondary, null)
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val paint = Paint().apply {
        color = primaryColor
    }

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    val puzzle: Puzzle = Puzzle(context)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        /* Check if there is already a bitmap to prevent a memory leak */
        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        /* Create a new bitmap and canvas */
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)

        val puzzleMargin: Float = 64f
        val puzzleWidth = width - (puzzleMargin * 2)
        puzzle.scale(
            0 + puzzleMargin,
            height - (puzzleWidth + puzzleMargin),
            puzzleWidth
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x: Float = event.x
        val y: Float = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart(x, y)
            MotionEvent.ACTION_MOVE -> touchMove(x, y)
            MotionEvent.ACTION_UP -> touchUp(x, y)
        }
        return true
    }

    private fun touchStart(x: Float, y: Float) {
        if (isInPuzzle(x, y)) {
            puzzle.touchStart(x - puzzle.puzzleX, y - puzzle.puzzleY)

            // Tell the screen that it needs to re-render
            invalidate()
        }
    }

    private fun touchMove(x: Float, y: Float) {
        if (isInPuzzle(x,y)) {
            puzzle.touchMove(x - puzzle.puzzleX, y - puzzle.puzzleY)
        }
    }

    private fun touchUp(x: Float, y: Float) {
        puzzle.touchUp(x - puzzle.puzzleX, y - puzzle.puzzleY)
    }

    private fun isInPuzzle(x: Float, y: Float): Boolean {
        return (x >= puzzle.puzzleX &&
                x <= puzzle.puzzleX + puzzle.puzzleWidth &&
                y >= puzzle.puzzleY &&
                y <= puzzle.puzzleY + puzzle.puzzleHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas.drawBitmap(puzzle.render(), puzzle.puzzleX, puzzle.puzzleY, null)
    }
}