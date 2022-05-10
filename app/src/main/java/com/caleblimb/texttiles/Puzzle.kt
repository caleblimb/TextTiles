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
        Tile('a', 'A'),
        Tile('b', 'B'),
        Tile('c', 'C'),
        Tile('d', 'D'),
        Tile('e', 'E'),
        Tile('f', 'F'),
        Tile('g', 'G'),
        Tile('h', 'H'),
        Tile('i', 'I'),
        Tile('j', 'J'),
        Tile('k', 'K'),
        Tile('l', 'L'),
        Tile('m', 'M'),
        Tile('n', 'N'),
        Tile('o', 'O'),
        Tile('p', 'P'),
        Tile('q', 'Q'),
        Tile('r', 'R'),
        Tile('s', 'S'),
        Tile('t', 'T'),
        Tile('u', 'U'),
        Tile('v', 'V'),
        Tile('w', 'W'),
        Tile('x', 'X'),
        Tile('y', 'Y'),
        Tile('z', 'Z')
    )

    /* Define game board dimensions */
    public var puzzleX: Float = 0f
    public var puzzleY: Float = 0f
    public var puzzleWidth: Float = 0f
    public var puzzleHeight: Float = 0f

    private var puzzlePadding: Float = 0f
    private var puzzleBorder: Float = 0f
    private var puzzleTileWidth: Int = 6
    private var puzzleTileHeight: Int = 6
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
    private var puzzle: Array<Tile?>
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        puzzle = generatePuzzle(puzzleTileWidth * puzzleTileHeight)
    }

    fun scale(x: Float, y: Float, width: Float) {
        puzzleX = x
        puzzleY = y
        puzzleWidth = width
        puzzleHeight = width

        puzzleBorder = puzzleWidth / 50
        puzzlePadding = puzzleBorder / 4
        tileWidth = (puzzleWidth - (puzzleBorder * 2) - (puzzlePadding * 2)) / puzzleTileWidth
        tileHeight = (puzzleHeight - (puzzleBorder * 2) - (puzzlePadding * 2)) / puzzleTileHeight
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
        if (x < puzzleTileWidth - 1) {
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
        if (y < puzzleTileHeight - 1) {
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
            x > puzzleTileWidth - 1 ||
            y < 0 ||
            y > puzzleTileHeight - 1
        ) {
            return null
        }
        return puzzle[((y * puzzleTileWidth.toInt()) + x)]
    }

    private fun setTile(x: Int, y: Int, t: Tile?) {
        if (x < 0 ||
            x > puzzleTileWidth - 1 ||
            y < 0 ||
            y > puzzleTileHeight - 1
        ) {
            return
        }
        puzzle[((y * puzzleTileWidth) + x)] = t
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
        for (y in 0 until puzzleTileHeight) {
            for (x in 0 until puzzleTileWidth) {
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