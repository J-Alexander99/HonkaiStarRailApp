package com.example.honkaistarrailapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.ceil

class trailblazeCalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trailblaze_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Define a function to calculate time required
    fun calculate() {
        val switchState = switchCheckbox.isChecked

        // Variables to store form data
        val totalCommon = totalCommonEditText.text.toString().toInt()
        val totalUncommon = totalUncommonEditText.text.toString().toInt()
        val totalRare = totalRareEditText.text.toString().toInt()
        val characterLevelHave = characterLevelHaveEditText.text.toString().toInt()
        val characterSkillHave = characterSkillHaveEditText.text.toString().toInt()
        val characterTalentHave = characterTalentHaveEditText.text.toString().toInt()
        val characterUltimateHave = characterUltimateHaveEditText.text.toString().toInt()
        val characterBasicHave = characterBasicHaveEditText.text.toString().toInt()

        // Variables to check if additional skills are active
        val additionalSkill1Have = additionalSkill1HaveCheckbox.isChecked.then { additionalSkill1HaveEditText.text.toString().toInt() }.otherwise { 0 }
        val additionalSkill2Have = additionalSkill2HaveCheckbox.isChecked.then { additionalSkill2HaveEditText.text.toString().toInt() }.otherwise { 0 }
        val additionalSkill3Have = additionalSkill3HaveCheckbox.isChecked.then { additionalSkill3HaveEditText.text.toString().toInt() }.otherwise { 0 }

        // Variables to check if stat buffs are active
        val statBuffsHave = List(10) { i ->
            when {
                statBuffHaveCheckboxes[i].isChecked -> statBuffHaveEditTexts[i].text.toString().toInt()
                else -> 0
            }
        }

        // Log the values to check if they are saved correctly
        println("Total Common: $totalCommon")
        println("Total Uncommon: $totalUncommon")
        println("Total Rare: $totalRare")
        println("Character Level Have: $characterLevelHave")
        println("Character Skill Have: $characterSkillHave")
        println("Character Talent Have: $characterTalentHave")
        println("Character Ultimate Have: $characterUltimateHave")
        println("Character Basic Have: $characterBasicHave")
        println("Additional Skill 1 Have: $additionalSkill1Have")
        println("Additional Skill 2 Have: $additionalSkill2Have")
        println("Additional Skill 3 Have: $additionalSkill3Have")
        println("Stat Buffs Have: $statBuffsHave")

        // Calculate total have
        var totalHave = characterSkillHave + characterTalentHave + characterUltimateHave +
                characterBasicHave + additionalSkill1Have + additionalSkill2Have + additionalSkill3Have +
                statBuffsHave.sum()

        println("Total Have: $totalHave")

        // Variables for needed data
        val characterLevelNeed = characterLevelNeedEditText.text.toString().toInt()
        val characterSkillNeed = characterSkillNeedEditText.text.toString().toInt()
        val characterTalentNeed = characterTalentNeedEditText.text.toString().toInt()
        val characterUltimateNeed = characterUltimateNeedEditText.text.toString().toInt()
        val characterBasicNeed = characterBasicNeedEditText.text.toString().toInt()

        // Variables to check if additional skills are active
        val additionalSkill1Need = additionalSkill1NeedCheckbox.isChecked.then { additionalSkill1NeedEditText.text.toString().toInt() }.otherwise { 0 }
        val additionalSkill2Need = additionalSkill2NeedCheckbox.isChecked.then { additionalSkill2NeedEditText.text.toString().toInt() }.otherwise { 0 }
        val additionalSkill3Need = additionalSkill3NeedCheckbox.isChecked.then { additionalSkill3NeedEditText.text.toString().toInt() }.otherwise { 0 }

        // Variables to check if stat buffs are active
        val statBuffsNeed = List(10) { i ->
            when {
                statBuffNeedCheckboxes[i].isChecked -> statBuffNeedEditTexts[i].text.toString().toInt()
                else -> 0
            }
        }

        // Log the values to check if they are saved correctly
        println("Character Level Need: $characterLevelNeed")
        println("Character Skill Need: $characterSkillNeed")
        println("Character Talent Need: $characterTalentNeed")
        println("Character Ultimate Need: $characterUltimateNeed")
        println("Character Basic Need: $characterBasicNeed")
        println("Additional Skill 1 Need: $additionalSkill1Need")
        println("Additional Skill 2 Need: $additionalSkill2Need")
        println("Additional Skill 3 Need: $additionalSkill3Need")
        println("Stat Buffs Need: $statBuffsNeed")

        // Calculate total need
        val totalNeed = characterLevelNeed + characterSkillNeed + characterTalentNeed +
                characterUltimateNeed + characterBasicNeed + additionalSkill1Need + additionalSkill2Need +
                additionalSkill3Need + statBuffsNeed.sum()

        println("Total Need: $totalNeed")

        // Calculate missing
        val totalMissing = totalNeed - totalHave

        // Constants for calculations
        val averageDrops = 5.56
        val timeToPullDrops = 3600
        val timeToPullMats = 3600 * 4
        val matDrops = 5
        val characterLevelMissing = characterLevelNeed - characterLevelHave

        // Calculate time required
        val matDropsTime = (characterLevelMissing.toDouble() / matDrops) * timeToPullMats
        val traceTime = (characterLevelMissing.toDouble() / averageDrops) * timeToPullDrops
        val totalTimeRequired = matDropsTime + traceTime

        // Calculate time components
        val overallDays = totalTimeRequired.toInt() / (3600 * 24)
        val overallHours = (totalTimeRequired.toInt() % (3600 * 24)) / 3600
        val overallMinutes = ((totalTimeRequired.toInt() % (3600 * 24)) % 3600) / 60
        val overallSeconds = (((totalTimeRequired.toInt() % (3600 * 24)) % 3600) % 60)

        // Display the result
        calculateResultsTextView.text = "Overall leveling up your character will take:"
        calculateResults2TextView.text = "$overallDays days, $overallHours hours, $overallMinutes minutes, and $overallSeconds seconds"
    }


}