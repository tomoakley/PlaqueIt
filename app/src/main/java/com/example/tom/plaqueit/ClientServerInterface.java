package com.example.tom.plaqueit;

import com.google.gson.Gson;

import java.io.IOException;
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
 * Created by Tom on 07/02/16.
 */

class UserLogin {
    boolean status;
}

public class ClientServerInterface {

    OkHttpClient client = new OkHttpClient();
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    boolean loginStatus;
    boolean completed;

    public void setStatus(boolean status) {
        loginStatus = status;
    }
    public boolean getStatus() {
        return loginStatus;
    }

    public void setCompleted(boolean isCompleted) {
        completed = isCompleted;
    }

    public boolean getCompleted() {
        return completed;
    }

    Future<Boolean> login(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Boolean>() {
            UserLogin login;
            @Override
            public Boolean call() throws Exception {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            setStatus(false);
                            System.out.println("failed in onFailure");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            System.out.println("onResponse");
                            login = gson.fromJson(response.body().charStream(), UserLogin.class);
                            setStatus(login.status);
                            setCompleted(true);
                            System.out.println("login is " + login.status + " but status is " + getStatus());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(false);
                }
                while (!getCompleted()) {
                }
                return getStatus();
            }
        });
    }

    boolean getRequest(String action, String email) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://134.83.83.25:47313/tom/User").newBuilder();
        urlBuilder.addQueryParameter("action", action);
        urlBuilder.addQueryParameter("email", email);
        String stringUrl = urlBuilder.build().toString();
        System.out.println("url to request is " + stringUrl);
        Request request = new Request.Builder()
                .url(stringUrl)
                .build();
        Future<Boolean> contentsFuture = login(request);
        System.out.println("The promise is now " + contentsFuture.get());
        return contentsFuture.get();
    }
}
