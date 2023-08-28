package com.example.snake1

import kotlin.properties.Delegates

class SnakePoints(private var posX: Float, private var posY: Float) {
    public fun setPosX(posX: Float) {
        this.posX = posX
    }

    public fun setPosY(posX: Float) {
        this.posY = posY
    }
}