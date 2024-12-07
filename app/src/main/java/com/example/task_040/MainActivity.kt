package com.example.task_040

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.task_040.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null

    private val songList = mutableListOf(
        R.raw.signal0,R.raw.signal1,R.raw.signal2,R.raw.signal3,R.raw.signal4,
    )

    private var songDataIndex = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playSound()

        binding.nextFAB.setOnClickListener{
            if (songDataIndex +1 <= songList.size-1)
            {
                ++songDataIndex
                mediaPlayerClear()
            }
        }

        binding.prevFAB.setOnClickListener{
            if (songDataIndex -1 >= 0)
            {
                --songDataIndex
                mediaPlayerClear()
            }
        }
    }

    private fun playSound() {
        binding.playFAB.setOnClickListener{
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, songList[songDataIndex])
                initializeSeekbar()
            }
            mediaPlayer?.start()
        }
        binding.pauseFAB.setOnClickListener{
            if (mediaPlayer != null) mediaPlayer?.pause()
        }
        binding.stopFAB.setOnClickListener{
            mediaPlayerClear()
        }

        binding.seekbarSB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged (seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun mediaPlayerClear() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun initializeSeekbar() {
        binding.seekbarSB.max = mediaPlayer!!.duration
        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    binding.seekbarSB.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding.seekbarSB.progress = 0
                }
            }
        },0)
    }
}