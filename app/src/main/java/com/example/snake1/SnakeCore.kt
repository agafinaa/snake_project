package com.example.snake1

import android.widget.LinearLayout

object SnakeCore {
    var nextMove:() -> Unit = {}
    var isPlay = true
    private val thread: Thread

    init {
        thread = Thread(kotlinx.coroutines.Runnable {
            while (true) {
                Thread.sleep(500)
                if (isPlay) {
                    nextMove()
                }
            }
        })
        thread.start()
    }

    fun startTheGame() {
        isPlay = true
    }
}