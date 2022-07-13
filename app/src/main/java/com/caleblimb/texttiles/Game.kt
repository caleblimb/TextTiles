package com.caleblimb.texttiles

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.NumberFormatException



import kotlin.random.Random


class Game : AppCompatActivity() {
    lateinit var dictionary: Dictionary
//    Bag of possible tiles
    private var bag: TileBag = TileBag()
    // Name of the game mode
    private val gameName : String = "Text Tiles"

    // Points earned through the entire game
    private var totalPoints : Int = 0

    // Points earned during the last turn
    private var recentPoints : Int = 0

    // Number of moves throughout the game
    private var moves : Int = 0

    // If the game should end or not
    private var endGame : Boolean = false


    lateinit var gameBoard: Array<Tile?>
    lateinit var puzzle: Puzzle

    val puzzleWidth = 5
    val puzzleHeight = 5

//    Seeded board value
    private var seed: Int = 0

    private var newTimerInstance:Timer = Timer(30)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        dictionary = Dictionary(this)


        // get the seeded input from the intent
        //seed = intent.getStringExtra("seed").toString().toInt() % 99999999


        //Start Timer
        newTimerInstance.startTimer()

        // Array of the board's tiles
        gameBoard = generatePuzzle(puzzleWidth * puzzleHeight, seed)
        puzzle = Puzzle(this, puzzleWidth, puzzleHeight, gameBoard, ::onTileMove)

        val canvas : CanvasManager = CanvasManager(this, puzzle)
        val canvasLayout : ConstraintLayout = findViewById<ConstraintLayout>(R.id.layoutCanvas)
        canvasLayout.addView(canvas)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener() {
            confirmExitGame()
        }

        // Override back button
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    confirmExitGame()
                }
            }
        )
    }

//    Generate a seeded list using a random number
    private fun getRandomList(random: Random, size: Int): List<Int> =
        List(size) { random.nextInt(0, bag.contents.size - 1) }

    private fun onTileMove() {
        incMove()
        findViewById<TextView>(R.id.textViewMoves).text = "Moves: ".plus(moves.toString())
        findViewById<TextView>(R.id.textViewWords).text = getAllWords().toString()
        //findViewById<TextView>(R.id.TimeRemaining).text = "Time: "
    }

    private fun timerUpdate()
    {
        val time = 0
       // findViewById<TextView>(R.id.TimeRemaining).text = "Time: "

        //print(newTimerInstance.getTimeRemainingSeconds())
    }

    private fun getAllWords(): ArrayList<String> {
        val words: ArrayList<String> = ArrayList()

        for (y in 0 until puzzleHeight) {
            for (x in 0 until puzzleWidth) {
                var word: String = ""
                for (h in x until puzzleWidth) {
                    word += puzzle.getTile(x + h, y)?.value ?: ' '
                    if (word.length >=3 && dictionary.isWord(word)) {
                        words.add(word)
                    }
                }
                word = ""
                for (v in y until puzzleHeight) {
                    word += puzzle.getTile(x, y + v)?.value ?: ' '
                    if (word.length >=3 && dictionary.isWord(word)) {
                        words.add(word)
                    }
                }
            }
        }

        return words
    }


    private fun generatePuzzle(size: Int, seed: Int): Array<Tile?> {
//        Generate a bag of tiles to pull from
        bag.fillBag()
        var newPuzzle: Array<Tile?> = Array<Tile?>(size) { null }


        val randSeed = Random(seed)
        var toPull = getRandomList(randSeed, size - 1)

//        Use this to see generated list
//        Log.d("-------------seeded list", toPull.toString())

        // loop through adding the numbers from the list to pull out the same tiles
        // This relies heavily on the fact that the order of the seeded list and the tiles in the contents will always be the same
        var accumulator = 0

        for (i in toPull.indices) {
            accumulator += toPull[i]

            if (accumulator >= bag.contents.size)
                accumulator -= bag.contents.size

            newPuzzle[i] = bag.pullTile(accumulator)
        }

        return newPuzzle
    }

    fun confirmExitGame() {
        val alert = AlertDialog.Builder(this@Game)

        alert.setTitle("Quit Game")
        alert.setMessage("Are you sure you want to leave?")

        alert.setPositiveButton("Continue") { _dialog, _which ->
        }
        alert.setNegativeButton("Quit") { _dialog, _which ->
            finish()
        }
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    /* SETTERS */
    fun addPoints(newPoints : Int) {
        recentPoints = newPoints
        totalPoints += newPoints
    }

    fun incMove() {
        moves += 1
    }

    fun endGame() {
        endGame = true
    }

    /* GETTERS */
    fun getGameName() : String {
        return gameName
    }

    fun getTotalPoints(tile : List<Tile>) : Int {

        return totalPoints
    }

    fun getRecentPoints() : Int {
        return recentPoints
    }

    fun getMoves() : Int {
        return moves
    }

    fun getEndGame() : Boolean {
        return endGame
    }
}