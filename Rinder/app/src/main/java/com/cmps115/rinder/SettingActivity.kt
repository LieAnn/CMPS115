package com.cmps115.rinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

import android.content.Intent;
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.SeekBar

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        //*********User input from settings page is stored here***************************//

        //User clicked done to exit settings menu
        val clickedDone = findViewById<Button>(R.id.exit_settings)

        //Price tags set by user are stored here
        val price1 = findViewById<Switch>(R.id.price_1)
        val price2 = findViewById<Switch>(R.id.price_2)
        val price3 = findViewById<Switch>(R.id.price_3)
        val price4 = findViewById<Switch>(R.id.price_4)

        //Cuisine tags set by user are stored here
        val tag1 = findViewById<CheckBox>(R.id.checkbox_1)
        val tag2 = findViewById<CheckBox>(R.id.checkbox_2)
        val tag3 = findViewById<CheckBox>(R.id.checkbox_3)
        val tag4 = findViewById<CheckBox>(R.id.checkbox_4)
        val tag5 = findViewById<CheckBox>(R.id.checkbox_5)
        val tag6 = findViewById<CheckBox>(R.id.checkbox_6)
        val tag7 = findViewById<CheckBox>(R.id.checkbox_7)
        val tag8 = findViewById<CheckBox>(R.id.checkbox_8)
        val tag9 = findViewById<CheckBox>(R.id.checkbox_9)
        val tag10 = findViewById<CheckBox>(R.id.checkbox_10)
        val tag11 = findViewById<CheckBox>(R.id.checkbox_11)
        val tag12 = findViewById<CheckBox>(R.id.checkbox_12)

        //Star rating set by user is stored here
        val rating = findViewById<RatingBar>(R.id.ratingBar) //as RatingBar

        //Max distance set by user is stored here
        val distance = findViewById<SeekBar>(R.id.distance_bar) //as SeekBar

        //Opening time set by user is stored here
        var startTime = findViewById<EditText>(R.id.start_time)
        var endTime = findViewById<EditText>(R.id.end_time)
        val startAM = findViewById<CheckBox>(R.id.start_am)
        val endAM = findViewById<CheckBox>(R.id.end_am)
        val startPM = findViewById<CheckBox>(R.id.start_pm)
        val endPM = findViewById<CheckBox>(R.id.end_pm)

        //Features I haven't added yet
        val button1 = findViewById<Button>(R.id.button_1)
        val button2 = findViewById<Button>(R.id.button_2)


        //*********Performing actions using user input***************************//

        //Create intent object for activity

        //Store selected price tags as strings
        price1.setOnClickListener {
            var p1 = "$"
        }
        price2.setOnClickListener {
            var p2 = "$$"
        }
        price3.setOnClickListener {
            var p3 = "$$$"

        }
        price4.setOnClickListener {
            var p4 = "$$$$"

        }

        //Store selected cuisine tags as strings
        tag1.setOnClickListener {
            var t1 = "American"

        }
        tag2.setOnClickListener {
            var t2 = "Chinese"

        }
        tag3.setOnClickListener {
            var t3 = "Takeout"

        }
        tag4.setOnClickListener {
            var t4 = "Vegetarian"

        }
        tag5.setOnClickListener {
            var t5 = "Vegan"

        }
        tag6.setOnClickListener {
            var t6 = "Mexican"

        }
        tag7.setOnClickListener {
            var t7 = "Noodles"

        }
        tag8.setOnClickListener {
            var t8 = "Full bar"

        }
        tag9.setOnClickListener {
            var t9 = "Baguette"

        }
        tag10.setOnClickListener {
            var t10 = "Trendy"

        }
        tag11.setOnClickListener {
            var t11 = "New"

        }
        tag12.setOnClickListener {
            var t12 = "Nut free"

        }


        //Store rating as a float value
        rating.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                var getrating = rating.getRating()

            }
        }

        //Store distance as an int value
        distance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //textView_progress.text = progress.toString()
                var distance_ = distance.progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //Store the start time as a string
        startTime.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var starttime = startTime.text.toString()
                //"Start_Time" is the ID, starttime is the associated data
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //Store the end time as a string
        endTime.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var endtime = endTime.text.toString()
                //"Start_Time" is the ID, starttime is the associated data
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //Save START_AM as a string
        startAM.setOnClickListener {
            var am1 = "AM"

        }
        //Save START_PM as a string
        startPM.setOnClickListener {
            var pm1 = "PM"

        }
        //Save END_AM as a string
        endAM.setOnClickListener {
            var am2 = "AM"

        }
        //Save END_PM as a string
        endPM.setOnClickListener {
            var pm2 = "PM"

        }

        //Action to perform when user clicks done (launch SettingsActivity)
        clickedDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}
