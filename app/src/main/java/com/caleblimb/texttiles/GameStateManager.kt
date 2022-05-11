package com.caleblimb.texttiles

class GameStateManager (_gameState : GameState){

    // Kotlin uses pass-by-value to pass a reference to the original object,
    // so this GameState object should reference the same GameState object used
    // in the Game class
    val gameState : GameState = _gameState

    // Array that holds words found since the last time update() was called
    var foundWords : Array<String> = emptyArray()

    val tier1 : Int = 8
    val tier2 : Int = 5
    val tier3 : Int = 3
    val tier4 : Int = 1
    val letterValues : HashMap<String, Int> = hashMapOf(
        // Points will be assigned by several tiers of letters
        // Data on letter frequency in the latin alphabet found at:
        // https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html

        // Tier 1:
        "J" to tier1,
        "Q" to tier1,
        "X" to tier1,
        "Z" to tier1,
        "K" to tier1,
        "V" to tier1,

        // Tier 2:
        "B" to tier2,
        "F" to tier2,
        "G" to tier2,
        "P" to tier2,
        "W" to tier2,
        "Y" to tier2,

        // Tier 3:
        "C" to tier3,
        "D" to tier3,
        "H" to tier3,
        "L" to tier3,
        "M" to tier3,
        "R" to tier3,
        "U" to tier3,


        // Tier 4
        "A" to tier4,
        "E" to tier4,
        "I" to tier4,
        "N" to tier4,
        "O" to tier4,
        "S" to tier4,
        "T" to tier4,
    )



    // Static method to update the properties of the GameState
    companion object {
        fun update() {
            findWords()
            assignPoints()
            assessEndCondition()
        }

        // Finds words on the board that have been formed
        private fun findWords() {
            // Todo: Fill this out
        }

        // Assigns points based on the words the player formed in the last turn
        private fun assignPoints() {
            // Todo: Fill this out
        }

        // Determine if the game should continue to run or not
        private fun assessEndCondition() {
            // Todo: Fill this out
            // if end condition == true
            //     gameState.endGame()
        }
    }


}