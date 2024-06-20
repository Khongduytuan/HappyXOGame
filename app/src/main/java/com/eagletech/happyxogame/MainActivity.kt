package com.eagletech.happyxogame

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.eagletech.happyxogame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val combinationList: MutableList<IntArray> = ArrayList()
    private var boxPositions = IntArray(9)
    private var playerTurn = 1
    private var totalSelectedBoxes = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        combinationList.apply {
            add(intArrayOf(0, 1, 2))
            add(intArrayOf(3, 4, 5))
            add(intArrayOf(6, 7, 8))
            add(intArrayOf(0, 3, 6))
            add(intArrayOf(1, 4, 7))
            add(intArrayOf(2, 5, 8))
            add(intArrayOf(2, 4, 6))
            add(intArrayOf(0, 4, 8))
        }
        val getPlayerOneName = intent.getStringExtra("playerOne")
        val getPlayerTwoName = intent.getStringExtra("playerTwo")
        binding.playerOneName.text = getPlayerOneName
        binding.playerTwoName.text = getPlayerTwoName
        val clickListener = View.OnClickListener { view ->
            val position = when (view.id) {
                R.id.image1 -> 0
                R.id.image2 -> 1
                R.id.image3 -> 2
                R.id.image4 -> 3
                R.id.image5 -> 4
                R.id.image6 -> 5
                R.id.image7 -> 6
                R.id.image8 -> 7
                R.id.image9 -> 8
                else -> return@OnClickListener
            }
            if (isBoxSelectable(position)) {
                performAction(view as ImageView, position)
            }
        }
        binding.image1.setOnClickListener(clickListener)
        binding.image2.setOnClickListener(clickListener)
        binding.image3.setOnClickListener(clickListener)
        binding.image4.setOnClickListener(clickListener)
        binding.image5.setOnClickListener(clickListener)
        binding.image6.setOnClickListener(clickListener)
        binding.image7.setOnClickListener(clickListener)
        binding.image8.setOnClickListener(clickListener)
        binding.image9.setOnClickListener(clickListener)
    }

    private fun performAction(imageView: ImageView, selectedBoxPosition: Int) {
        boxPositions[selectedBoxPosition] = playerTurn
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.ximage)
            if (checkResults()) {
                ResultDialog(this@MainActivity, "${binding.playerOneName.text} is a Winner!", this@MainActivity).apply {
                    setCancelable(false)
                    show()
                }
            } else if (totalSelectedBoxes == 9) {
                ResultDialog(this@MainActivity, "Match Draw", this@MainActivity).apply {
                    setCancelable(false)
                    show()
                }
            } else {
                changePlayerTurn(2)
                totalSelectedBoxes++
            }
        } else {
            imageView.setImageResource(R.drawable.oimage)
            if (checkResults()) {
                ResultDialog(this@MainActivity, "${binding.playerTwoName.text} is a Winner!", this@MainActivity).apply {
                    setCancelable(false)
                    show()
                }
            } else if (totalSelectedBoxes == 9) {
                ResultDialog(this@MainActivity, "Match Draw", this@MainActivity).apply {
                    setCancelable(false)
                    show()
                }
            } else {
                changePlayerTurn(1)
                totalSelectedBoxes++
            }
        }
    }

    private fun changePlayerTurn(currentPlayerTurn: Int) {
        playerTurn = currentPlayerTurn
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border)
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box)
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border)
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box)
        }
    }

    private fun checkResults(): Boolean {
        for (combination in combinationList) {
            if (boxPositions[combination[0]] == playerTurn &&
                boxPositions[combination[1]] == playerTurn &&
                boxPositions[combination[2]] == playerTurn) {
                return true
            }
        }
        return false
    }

    private fun isBoxSelectable(boxPosition: Int): Boolean {
        return boxPositions[boxPosition] == 0
    }

    fun restartMatch() {
        boxPositions = IntArray(9)
        playerTurn = 1
        totalSelectedBoxes = 1
        binding.image1.setImageResource(R.drawable.white_box)
        binding.image2.setImageResource(R.drawable.white_box)
        binding.image3.setImageResource(R.drawable.white_box)
        binding.image4.setImageResource(R.drawable.white_box)
        binding.image5.setImageResource(R.drawable.white_box)
        binding.image6.setImageResource(R.drawable.white_box)
        binding.image7.setImageResource(R.drawable.white_box)
        binding.image8.setImageResource(R.drawable.white_box)
        binding.image9.setImageResource(R.drawable.white_box)
    }
}
