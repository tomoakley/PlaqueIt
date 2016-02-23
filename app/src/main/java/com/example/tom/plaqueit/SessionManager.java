package com.example.tom.plaqueit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tom on 20/02/16.
 */
public class SessionManager {
    SharedPreferences user;
    SharedPreferences.Editor editor;
    Context _context;
    final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "user";
    private static final String USER_ID = "userid";

    public SessionManager(Context context) {
        this._context = context;
        user = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = user.edit();
    }

    public void createUserSession(int userId) {
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public int getUserId() {
        return user.getInt(USER_ID, 0);
    }
}
