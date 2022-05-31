package com.caleblimb.texttiles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat

class Puzzle(context: Context) : View(context) {
    /* Create possible tile types */
    val tiles: Array<Tile> = arrayOf(
        Tile('a', 1, 'A'),
        Tile('b', 1, 'B'),
        Tile('d', 1, 'D'),
        Tile('c', 1, 'C'),
        Tile('e', 1, 'E'),
        Tile('f', 1, 'F'),
        Tile('g', 1, 'G'),
        Tile('h', 1, 'H'),
        Tile('i', 1, 'I'),
        Tile('j', 1, 'J'),
        Tile('k', 1, 'K'),
        Tile('l', 1, 'L'),
        Tile('m', 1, 'M'),
        Tile('n', 1, 'N'),
        Tile('o', 1, 'O'),
        Tile('p', 1, 'P'),
        Tile('q', 1, 'Q'),
        Tile('r', 1, 'R'),
        Tile('s', 1, 'S'),
        Tile('t', 1, 'T'),
        Tile('u', 1, 'U'),
        Tile('v', 1, 'V'),
        Tile('w', 1, 'W'),
        Tile('x', 1, 'X'),
        Tile('y', 1, 'Y'),
        Tile('z', 1, 'Z')
    )

    /* Define game board dimensions */
    public var puzzleX: Float = 0f
    public var puzzleY: Float = 0f
    public var puzzleWidth: Float = 0f
    public var puzzleHeight: Float = 0f

    private var puzzlePadding: Float = 0f
    private var puzzleBorder: Float = 0f
    private var puzzleGridWidth: Int = 5
    private var puzzleGridHeight: Int = 5
    private var puzzleBorderColor =
        ResourcesCompat.getColor(resources, R.color.colorPuzzleBorder, null)
    private var puzzleBackgrounColor =
        ResourcesCompat.getColor(resources, R.color.colorPuzzleBackground, null)
    private var tileWidth: Float = 0f
    private var tileHeight: Float = 0f
    private var tileMargin: Float = 0f
    private val tileColor = ResourcesCompat.getColor(resources, R.color.colorPuzzleTile, null)
    private val tileTextColor =
        ResourcesCompat.getColor(resources, R.color.colorPuzzleTileText, null)
    private var gameBoard: Array<Tile?>
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        gameBoard = generatePuzzle(puzzleGridWidth * puzzleGridHeight)
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

    fun generatePuzzle(size: Int): Array<Tile?> {
        var newPuzzle: Array<Tile?> = Array<Tile?>(size) { null }
        //TODO change this to random Scrabble letter frequency
        for (i in 1 until newPuzzle.size) {
            newPuzzle[i] = tiles[i % tiles.size]
        }
        return newPuzzle
    }

    fun touchStart(x: Float, y: Float) {
        val coords = getCoordinatesOfTileAtLocation(x, y)
        moveTile(coords.x, coords.y)
    }

    fun touchMove(x:Float, y:Float) {

    }

    fun touchUp(x:Float,y:Float) {

    }

    private fun moveTile(x: Int, y: Int) {
        if (x > 0) {
            val left = getTile(x - 1, y)
            left ?: run {
                setTile(x - 1, y, getTile(x, y))
                setTile(x, y, null)
                return
            }
        }
        if (x < puzzleGridWidth - 1) {
            val right = getTile(x + 1, y)
            right ?: run {
                setTile(x + 1, y, getTile(x, y))
                setTile(x, y, null)
                return
            }
        }
        if (y > 0) {
            val up = getTile(x, y - 1)
            up ?: run {
                setTile(x, y - 1, getTile(x, y))
                setTile(x, y, null)
                return
            }
        }
        if (y < puzzleGridHeight - 1) {
            val down = getTile(x, y + 1)
            down ?: run {
                setTile(x, y + 1, getTile(x, y))
                setTile(x, y, null)
                return
            }
        }
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

    private fun getTile(x: Int, y: Int): Tile? {
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

    fun render(): Bitmap {
        if (::bitmap.isInitialized) bitmap.recycle()
        bitmap =
            Bitmap.createBitmap(puzzleWidth.toInt(), puzzleHeight.toInt(), Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)

        canvas.drawColor(puzzleBorderColor)
        paint.color = puzzleBackgrounColor
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
                paint.color = tileColor
                canvas.drawRect(
                    (x * tileWidth) + tileMargin + puzzlePadding + puzzleBorder,
                    (y * tileHeight) + tileMargin + puzzlePadding + puzzleBorder,
                    (x * tileWidth) + tileWidth - tileMargin + puzzlePadding + puzzleBorder,
                    (y * tileHeight) + tileHeight - tileMargin + puzzlePadding + puzzleBorder,
                    paint
                )
                /* If tile is not null, draw the letter it has */
                t?.let {
                    paint.color = tileTextColor
                    canvas.drawText(
                        t.sprite.toString(),
                        (x * tileWidth) + (tileWidth * 0.2f) + puzzlePadding + puzzleBorder,
                        (y * tileHeight) + (paint.textSize * 1.2f),
                        paint
                    )
                }
            }
        }
        return bitmap
    }

}