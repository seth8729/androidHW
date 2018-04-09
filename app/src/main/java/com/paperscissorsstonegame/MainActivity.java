package com.paperscissorsstonegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtComPlay, mTxtResult;
    private Button mBtnScissors, mBtnStone, mBtnPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtComPlay = (TextView)findViewById(R.id.txtComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mBtnScissors = (Button)findViewById(R.id.btnScissors);
        mBtnStone = (Button)findViewById(R.id.btnStone);
        mBtnPaper = (Button)findViewById(R.id.btnPaper);

        mBtnScissors.setOnClickListener(btnScissorsOnClick);
        mBtnStone.setOnClickListener(btnStoneOnClick);
        mBtnPaper.setOnClickListener(btnPaperOnClick);
    }

    private View.OnClickListener btnScissorsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            AI ms = new AI();

            String winner = ms.whoWin(1);
            mTxtComPlay.setText(ms.getComputerChoice());
            mTxtResult.setText(getString(R.string.result) + winner);
        }
    };

    private View.OnClickListener btnStoneOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            AI ms = new AI();

            String winner = ms.whoWin(2);
            mTxtComPlay.setText(ms.getComputerChoice());
            mTxtResult.setText(getString(R.string.result) + winner);
        }
    };

    private View.OnClickListener btnPaperOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            AI ms = new AI();

            String winner = ms.whoWin(3);
            mTxtComPlay.setText(ms.getComputerChoice());
            mTxtResult.setText(getString(R.string.result) + winner);
        }
    };
}
