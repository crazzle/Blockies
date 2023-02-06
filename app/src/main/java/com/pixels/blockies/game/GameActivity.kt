package com.pixels.blockies.game

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Process
import android.view.Window
import android.view.WindowManager
import com.pixels.blockies.game.draws.DrawingView
import com.pixels.blockies.game.game.BlockMover
import com.pixels.blockies.game.game.GameContext
import com.pixels.blockies.game.game.HighScore

/**
 * Entry point of the app - The GameActivity
 */
class GameActivity : Activity() {
    var drawView: DrawingView? = null
    var mover: BlockMover? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        GameContext.HIGH_SCORE = HighScore(dataStore)
        drawView = DrawingView(this)
        mover = BlockMover()
        mover!!.start()
        drawView!!.setBlockMover(mover)
        setContentView(drawView)
    }

    private val dataStore: SharedPreferences
        private get() = getSharedPreferences("blockiesDataStore", MODE_PRIVATE)

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Process.killProcess(Process.myPid())
    }

    public override fun onPause() {
        super.onPause()
        mover!!.pauseMoving()
    }

    public override fun onResume() {
        super.onResume()
        mover!!.resumeMoving()
    }
}