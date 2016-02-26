package com.example.tom.plaqueit;

import com.google.gson.Gson;

import java.io.IOException;
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
 * Created by Tom on 07/02/16.
 */

class JsonObject {
    boolean status;
    int id;
}

public class ClientServerInterface {

    OkHttpClient client = new OkHttpClient();
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    boolean completed;
    int actionID;
    boolean actionStatus;

    public void setStatus(boolean status) {
        actionStatus = status;
    }
    public boolean getStatus() {
        return actionStatus;
    }

    public void setCompleted(boolean isCompleted) {
        completed = isCompleted;
    }
    public boolean getCompleted() {
        return completed;
    }

    public void setId(int actionid) {
        actionID = actionid;
    }
    public int getId() {
        return actionID;
    }

    Future<Boolean> returnBoolean(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Boolean>() {
            JsonObject json;
            @Override
            public Boolean call() throws Exception {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            setStatus(false);
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            setCompleted(false);
                            System.out.println("response: " + response.body().charStream());
                            json = gson.fromJson(response.body().charStream(), JsonObject.class);
                            setStatus(json.status);
                            setCompleted(true);
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

    Future<Integer> returnInt(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Integer>() {
            JsonObject json;
            @Override
            public Integer call() throws Exception {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            setStatus(false);
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            setCompleted(false);
                            json = gson.fromJson(response.body().charStream(), JsonObject.class);
                            setId(json.id);
                            setCompleted(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(false);
                }
                while (!getCompleted()) {
                }
                System.out.println("callback method: " + getId());
                return getId();
            }
        });
    }

    void getRequest(String servlet, String action, HashMap<String, String> parameters) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://134.83.83.25:47313/tom/" + servlet).newBuilder();
        urlBuilder.addQueryParameter("action", action);
        for (HashMap.Entry<String, String> parameter : parameters.entrySet()) {
            urlBuilder.addQueryParameter(parameter.getKey(), parameter.getValue());
        }
        String stringUrl = urlBuilder.build().toString();
        System.out.println("url to request is " + stringUrl);
        Request request = new Request.Builder()
                .url(stringUrl)
                .build();
        switch (action) {
            case "checkemail": case "favourite": case "unfavourite":case "checkfavourite": // methods which return boolean values
                Future<Boolean> futureStatus = returnBoolean(request);
                boolean completedStatus = futureStatus.get();
                if (action.equals("checkemail")) {
                    LoginActivity.setLoginStatus(completedStatus);
                } else if (action.equals("favourite") || action.equals("unfavourite") || action.equals("checkfavourite")) {
                    PlaquePage.setFavourite(completedStatus);
                }
                break;
            case "login": // methods which return int values
                Future<Integer> futureId = returnInt(request);
                int completeId = futureId.get();
                System.out.println("completeId: " + completeId);
                LoginActivity.setUserId(completeId);
                break;
            default:
                break;
        }
    }
}
