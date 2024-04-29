package com.example.honkaistarrailapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.CheckBox
import android.widget.Toast
import android.view.View
import android.widget.TextView

class wishSimulator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wish_simulator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onCalculateButtonClick(view: View) {
        // Gather user input from UI elements
        val warps = findViewById<EditText>(R.id.warps).text.toString().toInt()
        val characterPity = findViewById<EditText>(R.id.characterPity).text.toString().toInt()
        val conePity = findViewById<EditText>(R.id.lightConePity).text.toString().toInt()
        val coneGuaranteed = findViewById<CheckBox>(R.id.lightConeGuarenteed).isChecked
        val characterGuaranteed = findViewById<CheckBox>(R.id.characterGuaranteed).isChecked
        val characterCopies = findViewById<EditText>(R.id.charactersWanted).text.toString().toInt()
        val coneCopies = findViewById<EditText>(R.id.lightConesWanted).text.toString().toInt()
        val numSimulations = 10000

        // Call the function with the gathered user input
        val probability = calculateWarpProbability(warps, characterPity, conePity, coneGuaranteed, characterGuaranteed, characterCopies, coneCopies, numSimulations)

        // Get reference to the TextView
        val probabilityTextView = findViewById<TextView>(R.id.results)

        // Set the probability text
        probabilityTextView.text = "Probability: $probability%"
    }
}

// Function to calculate warp probability
val FIVE_STAR_CHARACTER_CHANCE = 0.006
val FIVE_STAR_CONE_CHANCE = 0.008
val CHARACTER_SOFT_PITY = 74
val CONE_SOFT_PITY = 64
val CHARACTER_PITY = 90
val CONE_PITY = 80
val LIMITED_CONE_CHANCE = 0.75
val LIMITED_CHARACTER_CHANCE = 0.5
val SOFT_PITY_INCREMENT = 0.06

fun calculateWarpProbability(
    warps: Int,
    characterPity: Int,
    conePity: Int,
    coneGuaranteed: Boolean,
    characterGuaranteed: Boolean,
    characterCopies: Int,
    coneCopies: Int,
    numSimulations: Int
): Double {
    var successfulSimulations = 0

    repeat(numSimulations) {
        var warpsLeft = warps
        var charSuccesses = 0
        var coneSuccesses = 0
        var currConePity = conePity
        var currCharPity = characterPity
        var currConeGuaranteed = coneGuaranteed
        var currCharacterGuaranteed = characterGuaranteed

        while (warpsLeft > 0) {
            val randomValue = Math.random()

            // Only simulate character acquisition if characterCopies is greater than 0
            if (charSuccesses < characterCopies) {
                var currFiveStarChance = FIVE_STAR_CHARACTER_CHANCE
                currFiveStarChance += SOFT_PITY_INCREMENT * maxOf(currCharPity - CHARACTER_SOFT_PITY, 0)

                if (randomValue < currFiveStarChance || currCharPity + 1 == CHARACTER_PITY) {
                    if (currCharacterGuaranteed || Math.random() < LIMITED_CHARACTER_CHANCE) {
                        charSuccesses++
                        if (currCharacterGuaranteed) currCharacterGuaranteed = false
                        currCharPity = 0
                    } else {
                        currCharPity = 0
                        currCharacterGuaranteed = true
                    }
                } else {
                    currCharPity++
                }
            }

            // Only decrement warps if characterCopies is greater than 0
            if (charSuccesses < characterCopies) {
                warpsLeft--
            } else {
                // If no light cones are wanted, break out of the loop
                if (coneCopies == 0) {
                    break
                }

                var currFiveStarChance = FIVE_STAR_CONE_CHANCE
                currFiveStarChance += SOFT_PITY_INCREMENT * maxOf(currConePity - CONE_SOFT_PITY, 0)

                if (randomValue < currFiveStarChance || currConePity + 1 == CONE_PITY) {
                    if (currConeGuaranteed || Math.random() < LIMITED_CONE_CHANCE) {
                        coneSuccesses++
                        if (currConeGuaranteed) currConeGuaranteed = false
                        currConePity = 0
                    } else {
                        currConePity = 0
                        currConeGuaranteed = true
                    }
                } else {
                    currConePity++
                }
                warpsLeft--
            }
        }

        if (charSuccesses >= characterCopies && coneSuccesses >= coneCopies) {
            successfulSimulations++
        }
    }

    return (successfulSimulations.toDouble() / numSimulations) * 100
}