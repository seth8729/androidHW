package com.example.alan.homework8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_MUSIC = Menu.FIRST,
                             MENU_PLAY_MUSIC = Menu.FIRST + 1,
                             MENU_STOP_PLAYING_MUSIC = Menu.FIRST + 2,
                             MENU_ABOUT = Menu.FIRST + 3,
                             MENU_EXIT = Menu.FIRST + 4;

    private ConstraintLayout consLayout;
    private Spinner spinItem;
    private EditText edtDate;
    private EditText edtCost;

    private StringBuilder stringBuilder;
    private Formatter formatter;
    private ArrayList<String> dataList;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consLayout = findViewById(R.id.consLayout);
        spinItem = findViewById(R.id.spinItem);
        edtDate = findViewById(R.id.edtDate);
        edtDate.setEnabled(false);
        edtCost = findViewById(R.id.edtCost);

        registerForContextMenu(consLayout);

        ((DatePicker) findViewById(R.id.dpkDataPicker)).setOnDateChangedListener(dpkDatePicker_OnDateChanged);
        ((CalendarView) findViewById(R.id.cldCalendar)).setOnDateChangeListener(cldCalendar_OnDateChanged);
        findViewById(R.id.btnInput).setOnClickListener(btnInput_OnClick);
        findViewById(R.id.btnRecord).setOnClickListener(btnRecord_OnClick);

        stringBuilder = new StringBuilder();
        formatter = new Formatter(stringBuilder, Locale.TAIWAN);
        dataList = new ArrayList<>();
        number = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂BGM").setIcon(android.R.drawable.ic_media_ff);

        subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂 BGM:(talk love)");

        subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 1, "停止播放背景音樂BGM");

        menu.add(0, MENU_ABOUT, 1, "關於這個背景音樂程式...").setIcon(android.R.drawable.ic_dialog_info);

        menu.add(0, MENU_EXIT, 2, "結束end").setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_PLAY_MUSIC:
                Intent it = new Intent(MainActivity.this, MediaPlayService.class);
                startService(it);
                return true;
            case MENU_STOP_PLAYING_MUSIC:
                it = new Intent(MainActivity.this, MediaPlayService.class);
                stopService(it);
                return true;
            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("關於這個程式")
                        .setMessage("Homework9 程式 (BGM：talk love)")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();

                return true;
            case MENU_EXIT:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == consLayout) {
            if (menu.size() == 0) {
                SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂BGM");
                subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂BGM");
                subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 1, "停止播放背景音樂BGM");
                menu.add(0, MENU_ABOUT, 1, "關於這個程式...");
                menu.add(0, MENU_EXIT, 2, "結束");
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onOptionsItemSelected(item);
        return super.onContextItemSelected(item);
    }

    private final DatePicker.OnDateChangedListener dpkDatePicker_OnDateChanged = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            edtDate.setText(date);
        }
    };

    private final CalendarView.OnDateChangeListener cldCalendar_OnDateChanged = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String date = year + "/" + (month + 1) + "/" + dayOfMonth;
            edtDate.setText(date);
        }
    };

    private final View.OnClickListener btnInput_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String itemName = spinItem.getSelectedItem().toString();
            String date = edtDate.getText().toString();
            String cost = edtCost.getText().toString();
            stringBuilder.delete(0, stringBuilder.length());
            formatter.format("項目%d  %10s  %10s  %5s", number++, date, itemName, cost);
            dataList.add(stringBuilder.toString());
            Toast.makeText(MainActivity.this, cost, Toast.LENGTH_SHORT).show();
        }
    };

    private final View.OnClickListener btnRecord_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            intent.putStringArrayListExtra("dataList", dataList);
            startActivity(intent);
        }
    };
}
