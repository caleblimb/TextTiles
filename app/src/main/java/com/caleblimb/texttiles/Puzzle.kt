package com.caleblimb.texttiles

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat

class Puzzle(
    context: Context,
    private var puzzleGridWidth: Int,
    private var puzzleGridHeight: Int,
    private var gameBoard: Array<Tile?>,
    private val onTileMove: () -> Unit
) : View(context) {
    /* Define game board dimensions */
    public var puzzleX: Float = 0f
    public var puzzleY: Float = 0f
    public var puzzleWidth: Float = 0f
    public var puzzleHeight: Float = 0f

    private var puzzlePadding: Float = 0f
    private var puzzleBorder: Float = 0f
    private var puzzleBorderColor =
        ResourcesCompat.getColor(resources, R.color.red, null)
    private var puzzleBackgroundColor =
        ResourcesCompat.getColor(resources, R.color.brown, null)
    private var tileWidth: Float = 0f
    private var tileHeight: Float = 0f
    private var tileMargin: Float = 0f
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint().apply {
        style = Paint.Style.FILL
    }

    fun scale(x: Float, y: Float, width: Float) {
        puzzleX = x
        puzzleY = y
        puzzleWidth = width
        puzzleHeight = width

        puzzleBorder = puzzleWidth / 50
        puzzlePadding = puzzleBorder / 4
        tileWidth = (puzzleWidth - (puzzleBorder * 2) - (puzzlePadding * 2)) / puzzleGridWidth
        tileHeight = (puzzleHeight - (puzzleBorder * 2) - (puzzlePadding * 2)) / puzzleGridHeight
        paint.textSize = tileWidth * 0.8f
        tileMargin = puzzlePadding
    }

    fun touchStart(x: Float, y: Float) {
        val coords = getCoordinatesOfTileAtLocation(x, y)
        moveTile(coords.x, coords.y)
    }

    fun touchMove(x: Float, y: Float) {

    }

    fun touchUp(x: Float, y: Float) {
    }

    private fun swapTile(x: Int, y: Int, newX: Int, newY: Int): Boolean {
        if (getTile(newX, newY) == null) {
            setTile(newX, newY, getTile(x, y))
            setTile(x, y, null)
            return true
        }
        return false
    }

    private fun moveTile(x: Int, y: Int) {
        var moved = false
        //Up
        for (i in y - 1 downTo 0) {
            if (swapTile(x, y - i, x, y - i - 1))
                moved = true
        }
        //Right
        for (i in puzzleGridWidth - x - 2 downTo 0) {
            if (swapTile(x + i, y, x + i + 1, y))
                moved = true
        }
        //Down
        for (i in puzzleGridHeight - y - 2 downTo 0) {
            if (swapTile(x, y + i, x, y + i + 1))
                moved = true
        }
        //Left
        for (i in x - 1 downTo 0) {
            if (swapTile(x - i, y, x - i - 1, y))
                moved = true
        }

        if (moved)
            onTileMove()
    }

    private fun getCoordinatesOfTileAtLocation(x: Float, y: Float): Coordinate {
        val tx: Int = ((x - puzzleBorder - puzzlePadding) / tileWidth).toInt()
        val ty: Int = ((y - puzzleBorder - puzzlePadding) / tileHeight).toInt()
        return Coordinate(tx, ty)
    }

    private fun getTileAtLocation(x: Float, y: Float): Tile? {
        val tCoordinate = getCoordinatesOfTileAtLocation(x, y)
        val tx: Int = tCoordinate.x
        val ty: Int = tCoordinate.y
        return getTile(tx, ty)
    }

    public fun getTile(x: Int, y: Int): Tile? {
        if (x < 0 ||
            x > puzzleGridWidth - 1 ||
            y < 0 ||
            y > puzzleGridHeight - 1
        ) {
            return null
        }
        return gameBoard[((y * puzzleGridWidth) + x)]
    }

    private fun setTile(x: Int, y: Int, t: Tile?) {
        if (x < 0 ||
            x > puzzleGridWidth - 1 ||
            y < 0 ||
            y > puzzleGridHeight - 1
        ) {
            return
        }
        gameBoard[((y * puzzleGridWidth) + x)] = t
    }

    public fun render(): Bitmap {
        if (::bitmap.isInitialized) bitmap.recycle()
        bitmap =
            Bitmap.createBitmap(puzzleWidth.toInt(), puzzleHeight.toInt(), Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)

        canvas.drawColor(puzzleBorderColor)
        paint.color = puzzleBackgroundColor
        canvas.drawRect(
            puzzleBorder,
            puzzleBorder,
            puzzleWidth - puzzleBorder,
            puzzleHeight - puzzleBorder,
            paint
        )

        for (y in 0 until puzzleGridHeight) {
            for (x in 0 until puzzleGridWidth) {
                var t: Tile? = getTile(x, y)
                /* If tile is not null, draw the letter it has */
                t?.let {
                    val tile: Bitmap = BitmapFactory.decodeResource(
                        resources,
                        t.getID()
                    ) // t.getId() gets the id of the corresponding drawable
                    canvas.drawBitmap(
                        tile, null, Rect(
                            // Based this code on the code above.
                            ((x * tileWidth) + ((tileMargin + puzzlePadding + puzzleBorder))).toInt(),
                            ((y * tileHeight) + ((tileMargin + puzzlePadding + puzzleBorder))).toInt(),
                            ((x * tileWidth) + tileWidth + ((-tileMargin + puzzlePadding + puzzleBorder))).toInt(),
                            ((y * tileHeight) + tileHeight + ((-tileMargin + puzzlePadding + puzzleBorder))).toInt(),
                        ), null
                    )
                }
            }
        }
        return bitmap
    }

}