package com.mikhaellopez.circularprogressbarsample

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.larswerkman.lobsterpicker.OnColorListener
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.mikhaellopez.circularprogressbarsample.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var b:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Set Init progress with animation
        b.circularProgressBar.setProgressWithAnimation(65f, 1000) // =1s

        // Update circularProgressBar
        b.seekBarProgress.onProgressChanged { b.circularProgressBar.progress = it }
        b.seekBarStartAngle.onProgressChanged { b.circularProgressBar.startAngle = it }
        b.seekBarStrokeWidth.onProgressChanged { b.circularProgressBar.progressBarWidth = it }
        b.seekBarBackgroundStrokeWidth.onProgressChanged { b.circularProgressBar.backgroundProgressBarWidth = it }
        b.shadeSlider.onColorChanged {
            b.circularProgressBar.progressBarColor = it
            b.circularProgressBar.backgroundProgressBarColor = adjustAlpha(it, 0.3f)
        }
        b.switchRoundBorder.onCheckedChange { b.circularProgressBar.roundBorder = it }
        b.switchProgressDirection.onCheckedChange {
            b.circularProgressBar.progressDirection =
                    if (it) CircularProgressBar.ProgressDirection.TO_RIGHT
                    else CircularProgressBar.ProgressDirection.TO_LEFT
        }

        // Indeterminate Mode
        b.switchIndeterminateMode.onCheckedChange { b.circularProgressBar.indeterminateMode = it }
        b.circularProgressBar.onIndeterminateModeChangeListener = { b.switchIndeterminateMode.isChecked = it }
    }

    //region Extensions
    private fun SeekBar.onProgressChanged(onProgressChanged: (Float) -> Unit) {
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onProgressChanged(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Nothing
            }
        })
    }

    private fun LobsterShadeSlider.onColorChanged(onColorChanged: (Int) -> Unit) {
        addOnColorListener(object : OnColorListener {
            override fun onColorChanged(color: Int) {
                onColorChanged(color)
            }

            override fun onColorSelected(color: Int) {
                // Nothing
            }
        })
    }

    private fun SwitchCompat.onCheckedChange(onCheckedChange: (Boolean) -> Unit) {
        setOnCheckedChangeListener { _, isChecked -> onCheckedChange(isChecked) }
    }
    //endregion

    /**
     * Transparent the given progressBarColor by the factor
     * The more the factor closer to zero the more the progressBarColor gets transparent
     *
     * @param color  The progressBarColor to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted progressBarColor
     */
    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

}