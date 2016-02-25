package com.example.tom.plaqueit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    TextView email;
    TextView password;
    Button loginBtn;
    Button skipBtn;
    String emailValue;
    String passwordValue;
    String stage = "email";

    static boolean loginStatus;
    static int userId;

    Context context;
    SessionManager session;

    HashMap<String, String> requestParameters = new HashMap<>();

    public static void setLoginStatus(boolean newLoginStatus) {
        loginStatus = newLoginStatus;
    }
    private boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setUserId(int newUserId) {
        userId = newUserId;
    }
    private int getUserId() {
        return userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();
        session = new SessionManager(context);

        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        skipBtn = (Button) findViewById(R.id.skip_btn);
        email.requestFocus();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stage) {
                    case "email":
                        emailValue = email.getText().toString();
                        if (isNotNull(emailValue)) {
                            try {
                                boolean doesUserExist = checkEmail(emailValue);
                                if (doesUserExist) {
                                    password.setVisibility(View.VISIBLE);
                                    email.setVisibility(View.GONE);
                                    stage = "loginPassword";
                                } else {
                                    // stage = "register1"; enable when we do register
                                    Toast.makeText(LoginActivity.this, "You need to register before signing in", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Something went wrong. Try again.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a valid email / password", Toast.LENGTH_LONG).show();
                            loginBtn.setText("Login / Sign up");
                        }
                        break;
                    case "loginPassword":
                        passwordValue = password.getText().toString();
                        if (isNotNull(passwordValue)) {
                            try {
                                int loggedInID = checkPassword(emailValue);
                                if (loggedInID != 0) {
                                    System.out.println("userId = " + loggedInID);
                                    session.createUserSession(loggedInID);
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                    stage = "loginSuccess";
                                } else {
                                    Toast.makeText(LoginActivity.this, "Password / email combination incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Something went wrong. Try again.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "register1":
                        break;
                    default:
                        break;
                }
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Login skipped - some features may be unavailable", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkEmail(String emailValue) throws Exception {
        ClientServerInterface serverInstance = new ClientServerInterface();
        boolean response = false;
        requestParameters.put("email", emailValue);
        try {
            serverInstance.getRequest("User", "checkemail", requestParameters);
            response = getLoginStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public int checkPassword(String emailValue) {
        ClientServerInterface serverInstance = new ClientServerInterface();
        int userID = 0;
        requestParameters.put("email", emailValue);
        try {
            serverInstance.getRequest("User", "login", requestParameters);
            System.out.println("checkPassword: " + getUserId());
            userID = getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }

    public static boolean isNotNull(String txt){
        return txt != null && txt.trim().length() > 0;
    }

}
