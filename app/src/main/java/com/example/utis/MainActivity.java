package com.example.utis;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the reference to the ImageView within the onCreate method
        ImageView nextPageImageView = findViewById(R.id.nextPage);

        // Set the onClickListener for the ImageView
        nextPageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class); // Use MainActivity.this as the context
                startActivity(intent);
            }
        });
    }
}
