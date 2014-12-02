package com.pixels.blockies.app;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.pixels.blockies.app.draws.DrawingView;
import com.pixels.blockies.app.game.BlockMover;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.HighScore;

/**
 * Entry point of the app - The GameActivity
 */
public class GameActivity extends Activity {
    DrawingView drawView = null;
    BlockMover mover = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GameContext.HIGH_SCORE = new HighScore(getDataStore());

        drawView = new DrawingView(this);
        mover = new BlockMover();
        mover.start();
        drawView.setBlockMover(mover);
        setContentView(drawView);
    }

    private SharedPreferences getDataStore(){
        SharedPreferences prefs = this.getSharedPreferences("blockiesDataStore", Context.MODE_PRIVATE);
        return prefs;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onPause(){
        super.onPause();
        mover.pauseMoving();
    }

    @Override
    public void onResume(){
        super.onResume();
        mover.resumeMoving();
    }
}
