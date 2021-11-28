package edu.temple.audiobbplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class ControlFragment : Fragment() {

    private var time: Int = 0

    private lateinit var nowPlayingText: TextView
    private lateinit var currentProgress: TextView

    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button

    private lateinit var seekBar: SeekBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout =  inflater.inflate(R.layout.fragment_control, container, false)

        nowPlayingText = layout.findViewById(R.id.nowPlayingTextView)
        playButton = layout.findViewById(R.id.playButton)
        pauseButton = layout.findViewById(R.id.pauseButton)
        stopButton = layout.findViewById(R.id.stopButton)
        seekBar = layout.findViewById(R.id.seekBar)
        currentProgress = layout.findViewById(R.id.currentProgress)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedBookViewModel = ViewModelProvider(requireActivity()).get(SelectedBookViewModel::class.java)

        playButton.setOnClickListener {
            val currentBook = selectedBookViewModel.getSelectedBook().value
            if (currentBook != null) {
                nowPlayingText.text = "Title of Book: " + currentBook.title
                seekBar.max = currentBook.duration
            }
            (activity as ActionsInterface).onClickPlay(time)
        }

        pauseButton.setOnClickListener {
            val currentBook = selectedBookViewModel.getSelectedBook().value
            if (currentBook != null) {
                time = seekBar.progress
            }
            (activity as ActionsInterface).onClickPause()
        }

        stopButton.setOnClickListener {
            seekBar.progress = 0
            time = 0
            (activity as ActionsInterface).onClickStop()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                val currentBook = selectedBookViewModel.getSelectedBook().value

                if (currentBook != null) {
                    currentProgress.text = progress.toString()
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                (activity as ActionsInterface).onClickPause()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val currentBook = selectedBookViewModel.getSelectedBook().value

                if(currentBook != null){
                    time = seekBar.progress
                    currentProgress.text = time.toString()
                    (activity as ActionsInterface).onClickPlay(time)
                }
            }
        })
    }

    interface ActionsInterface {
        fun onClickPlay(currentTime: Int)
        fun onClickPause()
        fun onClickStop()
        fun onClickSeek()
    }
}