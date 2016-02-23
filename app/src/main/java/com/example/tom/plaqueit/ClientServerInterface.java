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

class UserLogin {
    boolean status;
}

class Action {
    int id;
    boolean status;
}

public class ClientServerInterface {

    OkHttpClient client = new OkHttpClient();
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    boolean loginStatus;
    boolean completed;
    int actionID;
    boolean actionStatus;

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

    public void setActionId(int actionid) {
        actionID = actionid;
    }
    public int getActionId() {
        return actionID;
    }

    public void setActionStatus(boolean status) {
        actionStatus = status;
    }
    public boolean getActionStatus() {
        return actionStatus;
    }

    Future<Boolean> checkEmail(final Request request) throws IOException {
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
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            setCompleted(false);
                            login = gson.fromJson(response.body().charStream(), UserLogin.class);
                            setStatus(login.status);
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

    Future<Integer> login(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Integer>() {
            Action action;
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
                            action = gson.fromJson(response.body().charStream(), Action.class);
                            setActionId(action.id);
                            setCompleted(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(false);
                }
                while (!getCompleted()) {
                }
                return getActionId();
            }
        });
    }

    Future<Integer> favourite(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Integer>() {
            Action action;
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
                            action = gson.fromJson(response.body().charStream(), Action.class);
                            setActionId(action.id);
                            setCompleted(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(false);
                }
                while (!getCompleted()) {
                }
                return getActionId();
            }
        });
    }

    Future<Boolean> unfavourite(final Request request) throws IOException {
        final Gson gson = new Gson();
        return pool.submit(new Callable<Boolean>() {
            Action action;
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
                            action = gson.fromJson(response.body().charStream(), Action.class);
                            setActionStatus(action.status);
                            setCompleted(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(false);
                }
                while (!getCompleted()) {
                }
                return getActionStatus();
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
            case "checkemail":
                Future<Boolean> futureLoginStatus = checkEmail(request);
                boolean completedLoginStatus = futureLoginStatus.get();
                LoginActivity.setLoginStatus(completedLoginStatus);
                break;
            case "login":
                Future<Integer> futureUserId = login(request);
                int completeUserId = futureUserId.get();
                LoginActivity.setUserId(completeUserId);
                break;
            case "favourite":
                Future<Integer> futureFavouriteId = favourite(request);
                int completeFavouriteId = futureFavouriteId.get();
                plaquePage.setFavourite(completeFavouriteId);
                break;
            case "unfavourite":
                Future<Boolean> futureFavouriteStatus = unfavourite(request);
                boolean completeFavouriteStatus = futureFavouriteStatus.get();
                System.out.println("favouriteStatus is now " + completeFavouriteStatus);
                plaquePage.setFavourite(completeFavouriteStatus);
                break;
            default:
                break;
        }
    }
}
