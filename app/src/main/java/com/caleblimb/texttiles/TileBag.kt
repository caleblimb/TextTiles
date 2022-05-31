package com.caleblimb.texttiles
import java.util.Random

class TileBag {
    private var contents = mutableListOf<Tile>()

//    empty a used bag and fill it with new tiles based off of the distribution amount
    fun fillBag()
    {


//        letter distribution key: letter, value: number of times a letter is in the bag
        val letterDistribution = mapOf<Char, Int>(
            'A' to 9, 'B' to 2, 'C' to 2, 'D' to 4, 'E' to 1, 'F' to 2, 'G' to 3,
            'H' to 2, 'I' to 9, 'J' to 1, 'K' to 1, 'L' to 4, 'M' to 2, 'N' to 6,
            'O' to 8, 'P' to 2, 'Q' to 1, 'R' to 6, 'S' to 4, 'T' to 6, 'U' to 4,
            'V' to 2, 'W' to 2, 'X' to 1, 'Y' to 2, 'Z' to 1)

        val letterScores = mapOf<Char, Int>(
            'A' to 1, 'B' to 3, 'C' to 3, 'D' to 2, 'E' to 1, 'F' to 4, 'G' to 2,
            'H' to 4, 'I' to 1, 'J' to 8, 'K' to 5, 'L' to 1, 'M' to 3, 'N' to 1,
            'O' to 1, 'P' to 3, 'Q' to 10,'R' to 1, 'S' to 4, 'T' to 1, 'U' to 1,
            'V' to 4, 'W' to 4, 'X' to 8, 'Y' to 4, 'Z' to 10)


//        clear bag if there are contents, so that we can reuse the object if we replay
        if (contents.size > 0 )
        {
            contents = mutableListOf<Tile>()
        }

//        Create a tile and add each letter with its score amount
        for (letter in letterDistribution.keys)
        {
            val score = letterScores[letter]!!
            val distributionNumber = letterDistribution[letter]!!
            val tile = Tile(letter, score, letter)
            for (i in 0 until distributionNumber)
            {
                contents.add(tile)
            }
        }
    }

//  return a random tile and remove it from the bag
    fun pullTile(): Tile
    {
        val random = Random()
        val randIndex = random.nextInt(contents.size)
        return contents.removeAt(randIndex)
    }
}