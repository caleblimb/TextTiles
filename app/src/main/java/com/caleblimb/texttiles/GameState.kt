package com.caleblimb.texttiles

// Holds the current state of the game
class GameState (_board : Array<Tile?>, _gameName : String = "Text Tiles"){

    // Name of the game mode
    private val gameName : String = _gameName

    // Array of the board's tiles
    private var board : Array<Tile?> = _board

    // Points earned through the entire game
    private var totalPoints : Int = 0

    // Points earned during the last turn
    private var recentPoints : Int = 0

    // Number of moves throughout the game
    private var moves : Int = 0

    // If the game should end or not
    private var endGame : Boolean = false





    /* SETTERS */
    fun updateBoard() {
        // Todo: Fill this out
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

    fun getBoard() : Array<Tile?> {
        return board
    }

    fun getTotalPoints() : Int {
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