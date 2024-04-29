package com.example.honkaistarrailapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class wishCountPredictor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wish_count_predictor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the calculate button by its ID
        val calculateButton = findViewById<Button>(R.id.button5)

        // Set an OnClickListener for the button
        calculateButton.setOnClickListener {
            calculate() // Call the calculate function when the button is clicked
        }
    }

    private fun calculate() {
        // Get all the values from the form
        val wishesSaved = findViewById<EditText>(R.id.savedWishesVar).text.toString().toIntOrNull() ?: 0 // number of wishes the user has currently saved
        val daysLeft = findViewById<EditText>(R.id.daysUntilupdateEndVar).text.toString().toIntOrNull() ?: 0 // number of days left on the update
        val updateCount = findViewById<EditText>(R.id.updatesUntilCharacterVar).text.toString().toIntOrNull() ?: 0 // how many updates until the characters banner

        // Get the selected radio button for updateHalf
        val updateHalfRadioGroup = findViewById<RadioGroup>(R.id.updateHalfRadioGroup)
        val selectedRadioButtonId = updateHalfRadioGroup.checkedRadioButtonId
        val updateHalf = if (selectedRadioButtonId == R.id.firstHalfRadioButton) {
            75
        } else {
            0
        }

        // Get the selected radio button for payment option
        val paidRadioGroup = findViewById<RadioGroup>(R.id.paidRadioGroup)
        val selectedRadioButtonId2 = paidRadioGroup.checkedRadioButtonId
        val paidWishes = when (selectedRadioButtonId2) {
            R.id.freeToPlayRadioButton -> 0
            R.id.battlePassRadioButton -> 8
            R.id.welkinRadioButton -> 23
            R.id.welkinBattlePassRadioButton -> 31
            else -> 0 // Default to 0 if no option is selected
        }


        val wishesPerUpdate = 101 + paidWishes

        val totalWishesSaved = wishesSaved + (updateCount * wishesPerUpdate) + ((wishesPerUpdate / 40) * daysLeft) - updateHalf

        val charactersGuaranteed = totalWishesSaved / 150.toDouble()

        // output to webpage
        findViewById<TextView>(R.id.wishes).text = totalWishesSaved.toString()
        // findViewById<TextView>(R.id.characters).text = charactersGuaranteed.toString()
    }
}