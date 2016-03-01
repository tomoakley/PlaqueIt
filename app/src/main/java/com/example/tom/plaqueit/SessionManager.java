package com.example.tom.plaqueit;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tom on 20/02/16.
 */
public class SessionManager {
    SharedPreferences user;
    SharedPreferences.Editor editor;
    Context _context;
    final int PRIVATE_MODE = 0;
    User newUser;

    private static final String PREF_NAME = "user";
    private static final String USER_ID = "userid";

    public SessionManager(Context context) {
        this._context = context;
        user = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = user.edit();
    }

    public void createUserSession(int userId) throws ExecutionException, InterruptedException {
        editor.putInt(USER_ID, userId);
        editor.apply();
        try {
            if (userId > 0) {
                ArrayList<String> params = new ArrayList<>();
                params.add("email");
                params.add("firstName");
                params.add("lastName");
                HashMap userDetails = User.setUserDetails(userId, params).get();
                newUser = new User(userId, userDetails.get("email").toString(), userDetails.get("firstName").toString(), userDetails.get("lastName").toString());
                User.setUser(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUserId() {
        return user.getInt(USER_ID, 0);
    }
}
