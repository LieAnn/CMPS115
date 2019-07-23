package com.cmps115.rinder


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


class SettingActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        checkSetting()


        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

    }

    override fun onBackPressed() {
        if (true) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)

        } else {
            super.onBackPressed()
        }
    }


    private fun checkSetting() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val name = sharedPreferences.getString("pricePref", "")
        Log.d("checkSetting", "On Sort ${name}")
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        if (key == "tagsPref") {
            Log.d("checkSetting", "TagsPref value was updated to: " + sharedPreferences.getStringSet(key, setOf("")))
        }
        if (key == "ratingPref") {
            Log.d("checkSetting", "RatingPref value was updated to: " + sharedPreferences.getInt(key, 0))
        }


    }


}

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

    }
}


