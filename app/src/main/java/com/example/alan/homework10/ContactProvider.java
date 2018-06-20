package com.example.alan.homework10;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class ContactProvider extends ContentProvider {

    private static final String AUTHORITY   = "com.example.ContactProvider";
    private static final String DB_FILE     = "contact.db", DB_TABLE = "contact";
    private static final int URI_ROOT       = 0, DB_TABLE_CONTACT = 1;
    public  static final Uri CONTENT_URI    = Uri.parse("content://" + AUTHORITY + "/" + DB_TABLE);
    private static final UriMatcher sUriMatcher = new UriMatcher(URI_ROOT);
    static {
        sUriMatcher.addURI(AUTHORITY, DB_TABLE, DB_TABLE_CONTACT);
    }
    private SQLiteDatabase sdbContact;

    @Override
    public boolean onCreate() {
        ContactDBOpenHelper contactDBOpenHelper = new ContactDBOpenHelper(getContext(), DB_FILE, null, 1);
        sdbContact = contactDBOpenHelper.getWritableDatabase();

        Cursor cursor = sdbContact.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if(cursor != null) {
            if(cursor.getCount() == 0)	// DB Table not exist, therefore to construct a new one.
                sdbContact.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                                   "_id INTEGER PRIMARY KEY," +
                                   "name TEXT NOT NULL," +
                                   "phoneNumber TEXT," +
                                   "phoneType TEXT);");
            cursor.close();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (sUriMatcher.match(uri) != DB_TABLE_CONTACT) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor c = sdbContact.query(true, DB_TABLE, projection, selection, null, null, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (sUriMatcher.match(uri) != DB_TABLE_CONTACT) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        long rowId = sdbContact.insert(DB_TABLE, null, contentValues);
        Uri insertedRowUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(insertedRowUri, null);
        return insertedRowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return sdbContact.delete(DB_TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return sdbContact.update(DB_TABLE, values, selection, selectionArgs);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if (sdbContact.isOpen())
            sdbContact.close();
    }
}
