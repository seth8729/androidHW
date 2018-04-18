package com.example.user.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.AlphaAnimation;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {
    private GridView mGridView;
    private ImageSwitcher mImgSwitcher;

    private Integer[] miImgArr = {
            R.drawable.img01, R.drawable.img02, R.drawable.img03,
            R.drawable.img04, R.drawable.img05, R.drawable.img06,
            R.drawable.img07, R.drawable.img08, R.drawable.img09,
            R.drawable.img10, R.drawable.img11, R.drawable.img12,};

    private Integer[] miThumbImgArr = {
            R.drawable.img01th, R.drawable.img02th, R.drawable.img03th,
            R.drawable.img04th, R.drawable.img05th, R.drawable.img06th,
            R.drawable.img07th, R.drawable.img08th, R.drawable.img09th,
            R.drawable.img10th, R.drawable.img11th, R.drawable.img12th};
    Animation[] List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitcher);

        mImgSwitcher.setFactory(this);	// 主程式類別必須implements ViewSwitcher.ViewFactory
        mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

        ImageAdapter imgAdap = new ImageAdapter(this, miThumbImgArr);

        mGridView = (GridView)findViewById(R.id.gridView);
        mGridView.setAdapter(imgAdap);
        mGridView.setOnItemClickListener(gridViewOnItemClick);
        Animation();
        //mGridView.setOnItemClickListener(NewgridViewOnItemClick);
    }
    protected void Animation(){
        int width = mImgSwitcher.getWidth();
        int height =mImgSwitcher.getHeight();

        Animation Alpha_in = new AlphaAnimation(0,1);
        Alpha_in.setInterpolator(new LinearInterpolator());
        Alpha_in.setDuration(2000);
        Animation Alpha_out = new AlphaAnimation(1,0);
        Alpha_out.setInterpolator(new LinearInterpolator());
        Alpha_out.setDuration(2000);

        Animation Scale_in=new ScaleAnimation(0,1,0,1);
        Scale_in.setInterpolator(new LinearInterpolator());
        Scale_in.setDuration(2000);
        Animation Scale_out=new ScaleAnimation(1,0,1,0);
        Scale_out.setInterpolator(new LinearInterpolator());
        Scale_out.setDuration(2000);

        Animation Translate_in = new TranslateAnimation(0,0,-300,0);
        Translate_in.setInterpolator(new LinearInterpolator());
        Translate_in.setDuration(2000);
        Animation Translate_out = new TranslateAnimation(0,0,0,300);
        Translate_out.setInterpolator(new LinearInterpolator());
        Translate_out.setDuration(2000);

        Animation Rotate_in=new RotateAnimation(0,360);
        Rotate_in.setInterpolator(new LinearInterpolator());
        Rotate_in.setDuration(2000);
        Animation Rotate_out=new RotateAnimation(0,360);
        Rotate_out.setInterpolator(new LinearInterpolator());
        Rotate_out.setDuration(2000);

        List = new Animation[]{
                null,
                Alpha_in,
                Alpha_out,
                Scale_in,
                Scale_out,
                Translate_in,
                Translate_out,
                Rotate_in,
                Rotate_out
        };
    }
    @Override
    public View makeView() {
        ImageView v = new ImageView(this);
        v.setBackgroundColor(0xFF000000);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        v.setBackgroundColor(Color.WHITE);
        return v;
    }


    private AdapterView.OnItemClickListener gridViewOnItemClick = new
            AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    switch ((int)(Math.random()*4 + 1)) {
                        case 1:
                            mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.alpha_in));
                            mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.alpha_out));
                            break;
                        case 2:
                            mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.trans_in));
                            mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.trans_out));
                            break;
                        case 3:
                            mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.scale_rotate_in));
                            mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.scale_rotate_out));
                            break;
                        case 4:
                            mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.scale_rotate_trans_in));
                            mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.scale_rotate_trans_out));
                            break;
                    }
                    mImgSwitcher.setImageResource(miImgArr[position]);
                }
            };
    private AdapterView.OnItemClickListener NewgridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int selection = (int)Math.floor(Math.random() * 4);
            mImgSwitcher.setInAnimation(List[selection * 2]);
            mImgSwitcher.setOutAnimation(List[selection * 2 + 1]);
            mImgSwitcher.setImageResource(miImgArr[position]);
        }
    };
}

