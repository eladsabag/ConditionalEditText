package com.elad.conditionaledittext

import android.content.Context
import android.media.AudioManager
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.elad.conditionaledittext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainButton.setOnClickListener { handleAuthClick(binding.mainText.text.toString()) }
    }

    private fun handleAuthClick(input: String) {
        if (getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
            toast(R.string.silent_mode)
            return
        }
        if (getAirplaneMode() != 0) {
            toast(R.string.airplane_mode)
            return
        }
        toast(
            if (input.isNotEmpty() && input == getBatteryPercentage()) {
                R.string.authentication_completed
            } else
                R.string.authentication_failed
        )
    }

    private fun toast(stringId: Int) = Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()

    private fun getBatteryPercentage() = (applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager).getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toString()

    private fun getRingerMode() = (getSystemService(Context.AUDIO_SERVICE) as AudioManager).ringerMode

    private fun getAirplaneMode() = Settings.System.getInt(applicationContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0)
}