package com.example.tom.plaqueit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView email;
    Button loginBtn;
    Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (TextView) findViewById(R.id.email);
        loginBtn = (Button) findViewById(R.id.login_btn);
        skipBtn = (Button) findViewById(R.id.skip_btn);
        email.requestFocus();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginUser();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dashboard_view.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Login skipped - some features may be unavailable", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loginUser() throws Exception {
        String emailValue = email.getText().toString();
        if (!Objects.equals(emailValue, "")) {
            ClientServerInterface serverInstance = new ClientServerInterface();
            boolean response;
            try {
                response = serverInstance.getRequest("login", emailValue);
                System.out.println("response is " + response);
                if (response) {
                    Intent intent = new Intent(this, dashboard_view.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email did not match our records", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid email / password", Toast.LENGTH_LONG).show();
        }
    }
}
