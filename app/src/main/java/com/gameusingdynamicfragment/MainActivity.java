package com.gameusingdynamicfragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int miCountSet = 0, miCountPlayerWin = 0, miCountComWin = 0, miCountDraw = 0;

    private Button mBtnRollingDice, mBtnShowResult;
    private ImageView mImgDice;

    private Bundle mBundle = new Bundle();

    private int[] diceImg = new int[]{
            R.drawable.dice01, R.drawable.dice02, R.drawable.dice03,
            R.drawable.dice04, R.drawable.dice05, R.drawable.dice06};

    private boolean isDiceRoll = false;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRollingDice = (Button) findViewById(R.id.btnRollingDice);
        mBtnShowResult = (Button) findViewById(R.id.btnShowRst);
        mImgDice = (ImageView) findViewById(R.id.imgDice);
        intent.setClass(MainActivity.this, Statistics.class);

        mBtnRollingDice.setOnClickListener(btnRollingDiceOnClick);
        mBtnShowResult.setOnClickListener(btnShowResult);
    }

    public void rollingAndRst() {
        int diceNum = (int) (Math.random() * 6);
        String result;
        miCountSet++;

        if (diceNum <= 1) {
            result = getString(R.string.player_win);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            miCountPlayerWin++;
        }
        else if (diceNum <= 3) {
            result = getString(R.string.player_draw);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            miCountDraw++;
            Log.v("drawCount", String.valueOf(miCountDraw));
        }
        else {
            result = getString(R.string.player_lose);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            miCountComWin++;
        }

        mImgDice.setImageDrawable(getResources().getDrawable(diceImg[diceNum]));
    }

    private View.OnClickListener btnRollingDiceOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (isDiceRoll)
                return;
            isDiceRoll = true;

            Resources res = getResources();
            final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgDice.setImageDrawable(animDraw);
            animDraw.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animDraw.stop();
                    rollingAndRst();
                    isDiceRoll = false;
                }
            }, 1000);
        }
    };
    private View.OnClickListener btnShowResult = new View.OnClickListener() {
        public void onClick(View v) {
            mBundle.putInt("Draw'", miCountDraw);
            mBundle.putInt("Set", miCountSet);
            mBundle.putInt("PlayerWin", miCountPlayerWin);
            mBundle.putInt("ComWin", miCountComWin);
            intent.putExtras(mBundle);

            startActivity(intent);
        }
    };

}
