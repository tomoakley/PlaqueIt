package com.example.tom.plaqueit;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tom on 26/02/16.
 */
public class User {
    int id;
    String email;
    String firstName;
    String lastName;
    // String gender;
    static User newUser;
    static boolean completed = false;

    User(int id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        // this.gender = gender;
    }

    private static void setCompleted(boolean isCompleted) {
        completed = isCompleted;
    }
    private static boolean getCompleted() {
        return completed;
    }

    public static Future<HashMap> setUserDetails(int userId, ArrayList<String> parameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://134.83.83.25:47313/tom/User").newBuilder();
        urlBuilder.addQueryParameter("action", "getdetails");
        urlBuilder.addQueryParameter("userid", Integer.toString(userId));
        for (String param : parameters) {
            urlBuilder.addQueryParameter("params", param);
            /* this adds multiple Parameters under the "param" key;
             * the URL will be ?action=details&userid=1&param=email&param=name.
             * The params will then be added into an array ['email', 'name'],
             * which will be passed into the method where the SQL can get these values and return them. */
        }
        String stringUrl = urlBuilder.build().toString();
        final Request request = new Request.Builder()
                .url(stringUrl)
                .build();
        final Gson gson = new Gson();
        final ExecutorService pool = Executors.newFixedThreadPool(10);
        final OkHttpClient client = new OkHttpClient();
        return pool.submit(new Callable<HashMap>() {
            HashMap userDetails = new HashMap<>();
            @Override
            public HashMap call() throws Exception {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            userDetails = gson.fromJson(response.body().charStream(), HashMap.class);
                            setCompleted(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (!getCompleted()) {

                }
                System.out.println("userDetails: " + userDetails);
                return userDetails;
            }
        });
    }

    public static void setUser(User user) {
        newUser = user;
    }

    public static User getUser() {
        return newUser;
    }

}
