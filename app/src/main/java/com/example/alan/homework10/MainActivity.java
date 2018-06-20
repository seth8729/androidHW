package com.example.alan.homework10;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Database settings
    // public static final String DB_FILE = "contact.db", DB_TABLE = "contact";
    // public static SQLiteDatabase sdbContact;

    // ContactProvider (Database)
    public static ContentResolver mContRes;

    // Fragments
    private AddNewContact addNewContact;
    private SearchContact searchContact;

    // Pagers
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // 設定 ViewPager 和 Pager Adapter。
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // 設定 ViewPager 給 TabLayout，就會顯示 tab pages。
        TabLayout tabLayout = findViewById(R.id.tblTabLine);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Close keyboard when user click TabLayout
                InputMethodManager imm = ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE));
                imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        // Initial Fragments
        addNewContact = new AddNewContact();
        searchContact = new SearchContact();

        // Setting up Database
        mContRes = getContentResolver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.itemSearch).getActionView();
        searchView.setOnQueryTextListener(searchView_OnQuery);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemAddContact) {
            ContentValues data = addNewContact.getContentValues();
            mContRes.insert(ContactProvider.CONTENT_URI, data);
            searchContact.updateListData();
            Toast.makeText(this, "資料已成功加入至資料庫中！", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //contactProvider.shutdown();
    }

    // On user searching data
    private final SearchView.OnQueryTextListener searchView_OnQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Cursor cursor = null;
            if (!query.equals("")) {
                cursor = mContRes.query(ContactProvider.CONTENT_URI,
                        new String[]{"_id", "name", "phoneNumber", "phoneType"},
                        "name=" + "\"" + query + "\"",
                        null, null);
            }

            if (cursor == null)
                return false;

            // If couldn't find data, then show the message
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "找不到目標資料！", Toast.LENGTH_LONG).show();
                searchContact.setListHighlight();
            }
            else {
                ArrayList<Integer> dataList = new ArrayList<>();
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    dataList.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
                searchContact.setListHighlight(dataList);
            }

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) { return false; }
    };

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            // 根據目前tab標籤頁的位置，傳回對應的fragment物件
            switch (position) {
                case 0:
                    fragment = addNewContact;
                    break;
                case 1:
                    fragment = searchContact;
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add New Contact";
                case 1:
                    return "Search Contact";
                default:
                    return null;
            }
        }
    }
}
