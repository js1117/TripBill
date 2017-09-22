package com.shinhan.tripbill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void note1(View view) {
        Intent intent = new Intent(MainActivity.this, TripNote1Activity.class);
        startActivity(intent);
    }
    public void note2(View view) {
        Intent intent = new Intent(MainActivity.this, TripNote2Activity.class);
        startActivity(intent);
    }
}
