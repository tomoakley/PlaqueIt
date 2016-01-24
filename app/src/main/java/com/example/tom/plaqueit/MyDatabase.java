package com.example.tom.plaqueit;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Tom on 21/01/2016.
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME ="plaqueit.db";
    private static final int DATABASE_VERSION = 2;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade(DATABASE_VERSION);
    }


}
