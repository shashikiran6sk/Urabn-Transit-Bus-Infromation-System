
package com.example.utis;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    public Button back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        back2 = findViewById(R.id.button3);

        // Retrieve timings list from intent
        ArrayList<String> timingsList = getIntent().getStringArrayListExtra("timingsList");

        // Initialize ListView
        ListView timingsListView = findViewById(R.id.timingsListView);

        // Create ArrayAdapter to display timings in ListView
        ArrayAdapter<String> timingsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, timingsList);

        // Set the adapter for the ListView
        timingsListView.setAdapter(timingsAdapter);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Schedule.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}