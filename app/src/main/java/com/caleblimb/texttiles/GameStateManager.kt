package com.caleblimb.texttiles

class GameStateManager (_gameState : GameState){

    // Kotlin uses pass-by-value to pass a reference to the original object,
    // so this GameState object should reference the same GameState object used
    // in the Game class
    val gameState : GameState = _gameState

    // Array that holds words found since the last time update() was called
    var foundWords : Array<String> = emptyArray()





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