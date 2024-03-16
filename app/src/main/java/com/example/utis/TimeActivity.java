package com.example.utis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeActivity extends AppCompatActivity {

    public Button back2;

    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        temp=findViewById(R.id.timely);
        back2 = findViewById(R.id.button3);

        Intent intent=getIntent();
        String str=intent.getStringExtra("message");
        temp.setText(str);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}