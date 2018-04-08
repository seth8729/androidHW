package edu.ntut.user.myhw3;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainActivity self;
    private RadioGroup mRadGrp;
    private RadioButton mRadBtnSex1;
    private RadioButton mRadBtnSex2;
    private Spinner mSpnAge;
    private Button mBtnOK;
    private TextView mTxtSug;
    private CheckBox mChkBoxMusic,mChkBoxSing,mChkBoxDance,mChkBoxTravel,mChkBoxReading,mChkBoxWriting
            ,mChkBoxClimbing,mChkBoxSwim,mChkBoxEating,mChkBoxDrawing;
    private TextView mTxtHab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        self = this;

        mRadBtnSex1 = (RadioButton) findViewById(R.id.radBtnSex1);
        mRadBtnSex2 = (RadioButton) findViewById(R.id.radBtnSex2);
        mSpnAge = (Spinner) findViewById(R.id.SpnAge);
        mRadGrp = (RadioGroup) findViewById(R.id.radGrpSex);
        mBtnOK = (Button) findViewById(R.id.btnOK);
        mTxtSug = (TextView) findViewById(R.id.txtSug);
        mTxtHab = (TextView) findViewById(R.id.txtHab);
        mChkBoxMusic = (CheckBox)findViewById(R.id.cheMus);
        mChkBoxSing = (CheckBox)findViewById(R.id.cheSin);
        mChkBoxDance = (CheckBox)findViewById(R.id.cheDan);
        mChkBoxTravel = (CheckBox)findViewById(R.id.cheTra);
        mChkBoxReading = (CheckBox)findViewById(R.id.cheRed);
        mChkBoxWriting = (CheckBox)findViewById(R.id.cheWri);
        mChkBoxClimbing = (CheckBox)findViewById(R.id.cheClm);
        mChkBoxSwim = (CheckBox)findViewById(R.id.cheSwm);
        mChkBoxEating = (CheckBox)findViewById(R.id.cheEat);
        mChkBoxDrawing = (CheckBox)findViewById(R.id.cheDra);
        mRadGrp.setOnCheckedChangeListener(radOnCheckedChange);
        mBtnOK.setOnClickListener(btnOKOnClick);
    }

    private RadioGroup.OnCheckedChangeListener radOnCheckedChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            RadioButton target = (RadioButton) radioGroup.findViewById(id);
            Resources res = getResources();
            ArrayAdapter<String> adapter;

            if (target == mRadBtnSex1) {
                adapter = new ArrayAdapter<String>(self, android.R.layout.simple_list_item_1, res.getStringArray(R.array.sex_list1));
            }
            else {
                adapter = new ArrayAdapter<String>(self, android.R.layout.simple_list_item_1, res.getStringArray(R.array.sex_list2));
            }

            mSpnAge.setAdapter(adapter);
        }
    };


    private View.OnClickListener btnOKOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MarriageSuggestion ms = new MarriageSuggestion();
            boolean isMale = mRadBtnSex1.isChecked();
            String sexStr = isMale ? "male" : "female";

            if (isMale) {
                switch(mSpnAge.getSelectedItem().toString()) {
                    case "小於30歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 1));
                        break;
                    case "30~40歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 2));
                        break;
                    case "大於40歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 3));
                }
            }
            else {
                switch(mSpnAge.getSelectedItem().toString()) {
                    case "小於28歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 1));
                        break;
                    case "28~35歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 2));
                        break;
                    case "大於35歲":
                        mTxtSug.setText(ms.getSuggestion(sexStr, 3));
                }
            }
            String habitInfo = getString(R.string.habbit);
            if (mChkBoxMusic.isChecked()) {
                habitInfo += mChkBoxMusic.getText().toString() + " ";
            }
            if (mChkBoxSing.isChecked()) {
                habitInfo += mChkBoxSing.getText().toString() + " ";
            }
            if (mChkBoxDance.isChecked()) {
                habitInfo += mChkBoxDance.getText().toString() + " ";
            }
            if (mChkBoxTravel.isChecked()) {
                habitInfo += mChkBoxTravel.getText().toString() + " ";
            }
            if (mChkBoxReading.isChecked()) {
                habitInfo += mChkBoxReading.getText().toString() + " ";
            }
            if (mChkBoxWriting.isChecked()) {
                habitInfo += mChkBoxWriting.getText().toString() + " ";
            }
            if (mChkBoxClimbing.isChecked()) {
                habitInfo += mChkBoxClimbing.getText().toString() + " ";
            }
            if (mChkBoxSwim.isChecked()) {
                habitInfo += mChkBoxSwim.getText().toString() + " ";
            }
            if (mChkBoxEating.isChecked()) {
                habitInfo += mChkBoxEating.getText().toString() + " ";
            }
            if (mChkBoxDrawing.isChecked()) {
                habitInfo += mChkBoxDrawing.getText().toString() + " ";
            }
            mTxtHab.setText(habitInfo);
        }
    };
}
