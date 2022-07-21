package com.caleblimb.texttiles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


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

    var foundWords : ArrayList<String> = ArrayList<String>()

    lateinit var gameBoard: Array<Tile?>
    lateinit var puzzle: Puzzle

    val puzzleWidth = 5
    val puzzleHeight = 5

//    Seeded board value
    private var seed: Int = 0

    var newTimerInstance:Timer = Timer(30)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        dictionary = Dictionary(this)


        // get the seeded input from the intent
        seed = intent.getStringExtra("seed").toString().toInt() % 99999999


        //Start Timer
        newTimerInstance.startTimer()

        // Array of the board's tiles
        gameBoard = generatePuzzle(puzzleWidth * puzzleHeight, seed)
        puzzle = Puzzle(this, puzzleWidth, puzzleHeight, gameBoard, ::onTileMove, ::updateDisplayTimer)

        val canvas : CanvasManager = CanvasManager(this, puzzle)
        val canvasLayout : ConstraintLayout = findViewById<ConstraintLayout>(R.id.layoutCanvas)
        canvasLayout.addView(canvas)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener() {
            confirmExitGame()
        }

        // For temporary 'End Game' button
        val buttonEndGame = findViewById<Button>(R.id.buttonEndGame)
        buttonEndGame.setOnClickListener() {
            endGame()
            showGameSummary()
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
        println("Pdfhgdfhdfhse")
        findViewById<TextView>(R.id.textViewMoves).text = "Moves: ".plus(moves.toString())
        accumulateFoundWordsFromMovement(getAllWords()) // accumulate the new words into foundWords
        findViewById<TextView>(R.id.textViewWords).text = foundWords.toString()
    }

    private fun updateDisplayTimer()
    {
        var time = newTimerInstance.getTimeRemainingSeconds()
        println(time)
        println("Plkease")
        findViewById<TextView>(R.id.TimeRemaining).text = "Time: ".plus(time.toString())
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

    fun showGameSummary() {
        setContentView(R.layout.activity_summary) // change the screen to the summary

        // for 'Home' button in the summary page
        val buttonHome = findViewById<Button>(R.id.buttonHome)
        buttonHome.setOnClickListener() {
            val intent = Intent(this@Game, MainActivity::class.java)
            startActivity(intent)
        }

        // for 'Quick Restart' button in summary page
        val buttonQuickRestart = findViewById<Button>(R.id.buttonQuickRestart)
        buttonQuickRestart.setOnClickListener() {
            val intent = Intent(this@Game, Game::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.textViewSeed).text = "Seed: ".plus(seed.toString()) // set the textViewSeed to include the seed
        findViewById<TextView>(R.id.textViewWords).text = "Words: ".plus(foundWords.size.toString()) // set the textViewWords to include the size of foundWords
        var pts = 0;
        for (word in foundWords) {
            pts += dictionary.wordPoints(word)
        }
        findViewById<TextView>(R.id.textViewPoints).text = "Points: ".plus(pts.toString()) // set the textViewPoints to include totalPoints
        findViewById<TextView>(R.id.textViewMoves).text = "Moves: ".plus(getMoves().toString()) // set the textViewMoves to include moves
        val wordsLinLay : LinearLayout = findViewById<LinearLayout>(R.id.linearLayoutWords)

        // add a textView for every item in foundWords to the linearLayoutWords
        for (i in foundWords.indices) {
            val textView = TextView(this)
            textView.text = foundWords[i]
            textView.id = i
            textView.textSize = 18f
            wordsLinLay.addView(textView)
        }
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
    private fun accumulateFoundWordsFromMovement(potentialWords : ArrayList<String>) {
        // This function takes the found words from current movement and adds them
        // to the foundWords member.
        for (i in potentialWords) {
            if (!foundWords.contains(i)) { // if the word is not in foundWords
                foundWords.add(i) // add the word to foundWords
            }
        }
    }

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