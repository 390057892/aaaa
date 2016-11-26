package com.example.administrator.mylianxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = (com.example.administrator.mylianxi.menu) findViewById(R.id.menu);

    }

    public void togglemenu(View view){
        menu.toggle();
    }
}
