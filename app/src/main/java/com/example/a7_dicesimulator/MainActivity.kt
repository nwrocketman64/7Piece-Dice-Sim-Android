package com.example.a7_dicesimulator

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the list of dice to select from.
        val dices = arrayOf("d4", "d6", "d8", "d10", "d12", "d20", "dp")

        // Get the drop down menu.
        val menu: Spinner = findViewById(R.id.dice_selection)

        // Create the array adapter.
        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dices
        )

        // Add the array to the menu.
        menu.adapter = arrayAdapter

        // Get the button.
        val rollButton: Button = findViewById(R.id.button)

        // Create an event listener for the button.
        rollButton.setOnClickListener {
            // Get the items from the view
            val diceImage: ImageView = findViewById(R.id.imageView2)
            val menu: Spinner = findViewById(R.id.dice_selection)
            val resultsView: TextView = findViewById(R.id.results)
            val rollSound: MediaPlayer = MediaPlayer.create(this, R.raw.roll)
            val soundControl: Switch = findViewById(R.id.soundControl)

            // Get the dice size.
            val diceSize: String = menu.selectedItem.toString()

            // Calculate the min value.
            val min = when(diceSize) {
                "d4" -> 1
                "d6" -> 1
                "d8" -> 1
                "d10" -> 0
                "d12" -> 1
                "d20" -> 1
                else -> 0
            }

            // Calculate the max value.
            val max = when(diceSize) {
                "d4" -> 4
                "d6" -> 6
                "d8" -> 8
                "d10" -> 9
                "d12" -> 12
                "d20" -> 20
                else -> 9
            }

            // Create the Dice.
            val dice = Dice(min, max)

            // Roll the dice.
            val diceRoll = dice.roll().toString()

            // Render the right image.
            val drawableResource = resources.getIdentifier(
                diceSize + "_" + diceRoll,
                "drawable",
                packageName
            )

            // Update the results text box.
            resultsView.text = "You rolled a " + diceRoll

            // Render the correct image.
            diceImage.setImageResource(drawableResource)
            diceImage.contentDescription = diceRoll

            // Play the roll sound effect.
            if (soundControl.isChecked){
                rollSound.start()
            }
        }
    }
}

// The class represents a dice object.
class Dice(private val min: Int, private  val max: Int){
    // The function rolls the dice.
    fun roll(): Int {
        return (min..max).random()
    }
}