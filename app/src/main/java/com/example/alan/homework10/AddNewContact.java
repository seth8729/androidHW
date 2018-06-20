package com.example.alan.homework10;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewContact extends Fragment {

    EditText edtName, edtPhoneNumber;
    Spinner spinPhoneNumberType;

    public AddNewContact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        edtName = view.findViewById(R.id.edtName);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        spinPhoneNumberType = view.findViewById(R.id.spinPhoneNumberType);
    }

    // Get content values for database
    public ContentValues getContentValues() {
        ContentValues newData = new ContentValues();
        newData.put("name", edtName.getText().toString());
        newData.put("phoneNumber", edtPhoneNumber.getText().toString());
        newData.put("phoneType", spinPhoneNumberType.getSelectedItem().toString());
        return newData;
    }
}
