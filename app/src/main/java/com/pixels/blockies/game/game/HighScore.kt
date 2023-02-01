package com.pixels.blockies.game.game

import android.content.SharedPreferences

/**
 * The High-Score that is persisted in the SharedPreferences
 */
class HighScore(dataStore: SharedPreferences?) {
    var dataStore: SharedPreferences? = null

    init {
        this.dataStore = dataStore
    }

    fun getScore(): Int {
        return dataStore!!.getInt(KEY, 0)
    }

    fun saveScore(newScore: Int) {
        val oldScore = dataStore!!.getInt(KEY, 0)
        if (newScore > oldScore) {
            val edit = dataStore!!.edit()
            edit.putInt(KEY, newScore)
            edit.commit()
        }
    }

    companion object {
        private const val KEY = "highscore"
    }
}