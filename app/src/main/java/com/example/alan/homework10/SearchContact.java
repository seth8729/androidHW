package com.example.alan.homework10;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchContact extends Fragment {

    private ListView lsvContactList;

    private HighlightAdapter<String> dataAdapter;

    // AlertDialogs
    AlertDialog actionAskingDialog;
    AlertDialog confirmDeleteDialog;
    AlertDialog dataModifyDialog;

    // 2 EditText and 1 Spinner in dataModifyDialog
    EditText edtName_Modify, edtPhoneNumber_Modify;
    Spinner spinPhoneType_Modify;

    public SearchContact() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Construct confirmDeleteDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("確認刪除");
        builder.setMessage("確認刪除此筆資料？");
        builder.setPositiveButton("刪除", eventDeleteEntry);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        confirmDeleteDialog = builder.create();

        // Construct dataModifyDialog
        View view = View.inflate(getContext(), R.layout.data_modify_dialog, null);
        edtName_Modify = view.findViewById(R.id.edtName_Modify);
        edtPhoneNumber_Modify = view.findViewById(R.id.edtPhoneNumber_Modify);
        spinPhoneType_Modify = view.findViewById(R.id.spinPhoneType_Modify);
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("修改資料");
        builder.setView(view);
        builder.setPositiveButton("完成", eventModifyData);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dataModifyDialog = builder.create();

        // Construct actionAskingDialog
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("資料動作");
        builder.setMessage("請選擇要對資料進行的動作。");
        builder.setPositiveButton("修改", eventModifyAction);
        builder.setNeutralButton("刪除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDeleteDialog.show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        actionAskingDialog = builder.create();
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
        lsvContactList.setOnItemLongClickListener(lsvContactList_OnLongClick);

        dataAdapter = new HighlightAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        lsvContactList.setAdapter(dataAdapter);

        // Init datas by database
        Cursor cursor = MainActivity.mContRes.query(ContactProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // Get data iterator
            while (!cursor.isAfterLast()) {
                dataAdapter.addData(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3));
                cursor.moveToNext();
            }
            cursor.close();
        }
        dataAdapter.notifyDataSetChanged();
    }

    // When button 'Done' in dataModifyDialog is pressed
    private final DialogInterface.OnClickListener eventModifyData = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            data.name = edtName_Modify.getText().toString();
            data.phoneNumber = edtPhoneNumber_Modify.getText().toString();
            data.phoneType = spinPhoneType_Modify.getSelectedItem().toString();
            MainActivity.mContRes.update(ContactProvider.CONTENT_URI, data.getContentValues(),
                    "_id = " + String.valueOf(data.id), null);
            dataAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "哦耶~資料更新成功！", Toast.LENGTH_LONG).show();
        }
    };

    // When button 'Modify' in actionAskingDialog is pressed
    private final DialogInterface.OnClickListener eventModifyAction = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            edtName_Modify.setText(data.name);
            edtPhoneNumber_Modify.setText(data.phoneNumber);
            String[] phoneType = getResources().getStringArray(R.array.phone_type);
            if (data.phoneType.equals(phoneType[0])) {
                spinPhoneType_Modify.setSelection(0);
            }
            else if (data.phoneType.equals(phoneType[1])) {
                spinPhoneType_Modify.setSelection(1);
            }
            else {
                spinPhoneType_Modify.setSelection(2);
            }
            dataModifyDialog.show();
        }
    };

    // When button 'Delete' in confirmDeleteDialog is pressed
    private final DialogInterface.OnClickListener eventDeleteEntry = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            MainActivity.mContRes.delete(ContactProvider.CONTENT_URI, "_id = " + data.id, null);
            dataAdapter.removeLongPressedData();
            Toast.makeText(getContext(), "哦耶~資料刪除成功！哦耶~", Toast.LENGTH_LONG).show();
        }
    };

    // When the data is pressed
    private final ListView.OnItemLongClickListener lsvContactList_OnLongClick = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            actionAskingDialog.show();
            return true;
        }
    };

    // Update list
    public void updateListData() {
        Cursor cursor = MainActivity.mContRes.query(ContactProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            dataAdapter.clearAllData();
            // Get data iterator
            while (!cursor.isAfterLast()) {
                dataAdapter.addData(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                cursor.moveToNext();
            }
            cursor.close();
            dataAdapter.notifyDataSetChanged();
            dataAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getContext(), "無法更新清單GG！", Toast.LENGTH_LONG).show();
        }
    }

    // Clear all highlight
    public void setListHighlight() {
        dataAdapter.setHighlightList();
    }

    // Set highlights in list by data
    public void setListHighlight(ArrayList<Integer> list) {
        dataAdapter.setHighlightList(list);
    }
}

