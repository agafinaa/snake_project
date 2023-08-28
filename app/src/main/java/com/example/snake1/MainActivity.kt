package com.example.snake1

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<RelativeLayout>(R.id.board)
        val border = findViewById<RelativeLayout>(R.id.relativeLayout)
        val bottomBar = findViewById<LinearLayout>(R.id.bottomBar)
        val upButton = findViewById<ImageView>(R.id.up)
        val downButton = findViewById<ImageView>(R.id.down)
        val leftButton = findViewById<ImageView>(R.id.left)
        val rightButton = findViewById<ImageView>(R.id.right)
        val pauseButton = findViewById<ImageView>(R.id.pause)
        val scoreButton = findViewById<Button>(R.id.scoreButton)
        val newGameButton = findViewById<Button>(R.id.newGame)

        val food = ImageView(this)
        val snakePoint = ImageView(this)
        val snakeBody = mutableListOf(snakePoint)
        val handler = Handler()
        var speed = 50L
        var movingDirection = Direction.RIGHT
        var score = 0
        val positionShift = 10
        var isPlay = true

        newGameButton.setOnClickListener {
            border.setBackgroundColor(resources.getColor(R.color.white))
            board.removeAllViews()

            snakePoint.x = 0F
            snakePoint.y = 0F
            snakeBody.clear()
            snakeBody.add(snakePoint)

            movingDirection = Direction.RIGHT
            speed = 50L
            score = 0

            newGameButton.visibility = View.INVISIBLE
            snakePoint.setImageResource(R.drawable.circle_1)
            snakePoint.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(snakePoint)

            //var snakeX = snake.x
            //var snakeY = snake.y
            //snake.x = -400F  что за чертовщина с координатами?
            //snake.y = -400F
            //snake.x = 0F
            //snake.y = 0F
            //snake.x = 400F
            //snake.y = 400F
            val rand = Random()
            var snakeX = snakePoint.x
            var snakeY = snakePoint.y
            food.x = (rand.nextInt(841) - 420).toFloat()  // [-420, 420]
            food.y = (rand.nextInt(841) - 420).toFloat()  // 412dp = 1080 => 1dp = 2.62

            food.setImageResource(R.drawable.food)
            food.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(food)

            /*val rand = Random()

            food.x = (rand.nextInt(841) - 420).toFloat()  // [-420, 420]
            food.y = (rand.nextInt(841) - 420).toFloat()  // 412dp = 1080 => 1dp = 2.62*/

            //food.x = randX.toFloat()
            //food.y = randY.toFloat()
            //food.x = 0F
            //food.y = 0F
            //food.x = 420F
            //food.y = 420F
            //food.x = -400F
            //food.y = -400F


            fun setRandFoodCoordinate() {
                food.x = rand.nextInt(board.height).toFloat()
                food.y = rand.nextInt(board.width).toFloat()
            }

            fun ateFoodOrNot() {
                val measurementError = 50
                val distance = sqrt((snakePoint.x - food.x).pow(2) + (snakePoint.y - food.y).pow(2))

                if (distance < measurementError) {
                    val newSnake = ImageView(this)
                    newSnake.setImageResource(R.drawable.circle_1)
                    newSnake.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    board.addView(newSnake)
                    snakeBody.add(newSnake)

                    setRandFoodCoordinate()

                    speed++
                    score++

                    scoreButton.text = "score : ${score.toString()}"
                }
            }

            val runnable = object : Runnable {
                override fun run() {

                    for (i in snakeBody.size - 1 downTo 1) {
                        snakeBody[i].x = snakeBody[i - 1].x
                        snakeBody[i].y = snakeBody[i - 1].y
                    }

                    when (movingDirection) {
                        Direction.UP -> {
                            snakeY -= positionShift
                            val halfBoard = board.height / 2 - snakePoint.height + 30
                            if (snakeY < -halfBoard) {
                                snakeY = -halfBoard.toFloat()
                                border.setBackgroundColor(resources.getColor(R.color.red))
                                //board.removeView(snakePoint)
                                //board.removeView(food)
                                //board.removeAllViews()
                                newGameButton.visibility = View.VISIBLE
                            }
                            snakePoint.translationY = snakeY
                        }

                        Direction.DOWN -> {
                            snakeY += positionShift
                            val halfBoard = board.height / 2 - snakePoint.height + 30
                            if (snakeY > halfBoard) {
                                snakeY = halfBoard.toFloat()
                                border.setBackgroundColor(resources.getColor(R.color.red))
                                //board.removeView(snakePoint)
                                //board.removeView(food)
                                //board.removeAllViews()
                                newGameButton.visibility = View.VISIBLE
                            }
                            snakePoint.translationY = snakeY
                        }

                        Direction.LEFT -> {
                            snakeX -= positionShift
                            val halfBoard = board.height / 2 - snakePoint.height + 30
                            if (snakeX < -halfBoard) {
                                snakeX = -halfBoard.toFloat()
                                border.setBackgroundColor(resources.getColor(R.color.red))
                                //board.removeView(snakePoint)
                                //board.removeView(food)
                                //board.removeAllViews()
                                newGameButton.visibility = View.VISIBLE
                            }
                            snakePoint.translationX = snakeX
                        }

                        Direction.RIGHT -> {
                            snakeX += positionShift
                            val halfBoard = board.height / 2 - snakePoint.height + 30
                            if (snakeX > halfBoard) {
                                snakeX = halfBoard.toFloat()
                                border.setBackgroundColor(resources.getColor(R.color.red))
                                //board.removeView(snakePoint)
                                //board.removeView(food)
                                //board.removeAllViews()
                                newGameButton.visibility = View.VISIBLE
                            }
                            snakePoint.translationX = snakeX
                        }

                        Direction.PAUSE -> {
                                snakeX += 0
                                snakePoint.translationX = snakeX
                        }
                    }
                    ateFoodOrNot()
                    handler.postDelayed(this, speed)
                }
            }
            handler.postDelayed(runnable, speed)

            pauseButton.setOnClickListener {
                isPlay = !isPlay
                if (isPlay) {
                    movingDirection = Direction.PAUSE
                    pauseButton.setImageResource(R.drawable.play)
                    isPlay = false
                } else {
                    movingDirection = Direction.RIGHT
                    pauseButton.setImageResource(R.drawable.pause)
                    isPlay = true
                }
            }

            upButton.setOnClickListener {
                if (movingDirection != Direction.DOWN) {
                    movingDirection = Direction.UP
                }
            }
            downButton.setOnClickListener {
                if (movingDirection != Direction.UP) {
                    movingDirection = Direction.DOWN
                }
            }
            leftButton.setOnClickListener {
                if (movingDirection != Direction.RIGHT) {
                    movingDirection = Direction.LEFT
                }
            }
            rightButton.setOnClickListener {
                if (movingDirection != Direction.LEFT) {
                    movingDirection = Direction.RIGHT
                }
            }
            pauseButton.setOnClickListener {
                movingDirection = Direction.PAUSE
            }
        }
    }
}

enum class Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    PAUSE
}