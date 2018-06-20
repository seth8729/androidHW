package com.example.alan.homework10;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchContact extends Fragment {

    private ListView lsvContactList;

    private HighlightAdapter<String> dataAdapter;

    public SearchContact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        lsvContactList = view.findViewById(R.id.lsvContactList);
        lsvContactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lsvContactList.clearChoices();

        dataAdapter = new HighlightAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        lsvContactList.setAdapter(dataAdapter);

        // Init datas by database
        Cursor cursor = MainActivity.sdbContact.rawQuery("select * from " + MainActivity.DB_TABLE,null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String data = "Name: " + cursor.getString(1) +
                              ", PhoneNumber: " + cursor.getString(2) +
                              ", PhoneType: " + cursor.getString(3);
                dataAdapter.add(data);
                cursor.moveToNext();
            }
            cursor.close();
        }
        dataAdapter.notifyDataSetChanged();
    }

    // Add new data to list
    public void addDataToList(String data) {
        dataAdapter.add(data);
        dataAdapter.notifyDataSetChanged();
        lsvContactList.clearChoices();
        lsvContactList.requestLayout();
    }

    // Clear all highlight
    public void setListHighlight() {
        dataAdapter.setHighlightList();
    }

    // Set highlights in list by data
    public void setListHighlight(ArrayList<String> list) {
        ArrayList<Integer> indexList = new ArrayList<>();
        int length = dataAdapter.getCount();

        for (int i = 0; i < length; i++) {
            if (list.contains(dataAdapter.getItem(i))) {
                indexList.add(i);
            }
        }

        dataAdapter.setHighlightList(indexList);
    }
}

class HighlightAdapter<T> extends ArrayAdapter<T> {

    private ArrayList<Integer> indexList;

    public HighlightAdapter(Context context, int resource) {
        super(context, resource);
        indexList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final View renderer = super.getView(position, convertView, parent);
        if (indexList.contains(position)) {
            renderer.setBackgroundResource(R.color.selected);
        }
        else {
            renderer.setBackgroundResource(R.color.normal);
        }
        return renderer;
    }

    public void setHighlightList() { indexList.clear(); }

    public void setHighlightList(ArrayList<Integer> newIndexList) {
        indexList = newIndexList;
    }
}
