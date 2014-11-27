package com.pixels.blockies.app.game;

import android.content.SharedPreferences;

/**
 * The High-Score that is persisted in the SharedPreferences
 */
public class HighScore {

    private static final String KEY = "highscore";

    SharedPreferences dataStore = null;

    public HighScore(SharedPreferences dataStore){
        this.dataStore = dataStore;
    }

    public int getScore(){
        return dataStore.getInt(KEY, 0);
    }

    public void saveScore(int newScore){
        int oldScore = dataStore.getInt(KEY, 0);
        if(newScore > oldScore ){
            SharedPreferences.Editor edit = dataStore.edit();
            edit.putInt(KEY, newScore);
            edit.commit();
        }

    }

}
