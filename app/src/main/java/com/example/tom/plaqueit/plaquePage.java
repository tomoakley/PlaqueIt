package com.example.tom.plaqueit;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class plaquePage extends AppCompatActivity {

    Bundle bundle;
    String title;
    String description;
    TextView plaqueTitle;
    TextView plaqueDesc;
    Toolbar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaque_page);

        bundle = getIntent().getExtras();
        title = bundle.getString("title");
        description = bundle.getString("description");

        plaqueTitle = (TextView) findViewById(R.id.plaque_title);
        plaqueDesc = (TextView) findViewById(R.id.plaque_description);
        appBar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(appBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        plaqueTitle.setText(title);
        plaqueDesc.setText(description);

    }
}
