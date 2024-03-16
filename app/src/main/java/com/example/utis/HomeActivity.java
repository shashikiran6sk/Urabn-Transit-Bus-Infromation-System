package com.example.utis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private ImageView notificationImageView, aboutUsImageView;
    private Spinner departureSpinner, arrivalSpinner;
    private Button timingButton,scheduleButton,back2;;
    private TextView timelyTextView;

    private static final String DATABASE_NAME = "my_transport_schedules.db";
    private static final String TABLE_NAME = "routes";
    private static final String COLUMN_ROUTE = "route";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_SOURCE_TIME = "source_time";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DESTINATION_TIME = "destination_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get references to views
        notificationImageView = findViewById(R.id.notification);
        aboutUsImageView = findViewById(R.id.AboutUs);
        departureSpinner = findViewById(R.id.departure);
        arrivalSpinner = findViewById(R.id.arrival);
        timingButton = findViewById(R.id.timing);
        scheduleButton=findViewById(R.id.schedule);
        back2 = findViewById(R.id.button);

        // Inflate the layout containing the timelyTextView
        View timeLayout = getLayoutInflater().inflate(R.layout.activity_time, null);
        // Find the timelyTextView within the inflated layout
        timelyTextView = timeLayout.findViewById(R.id.timely);

        // Set click listeners
        notificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Admin.class);
                startActivity(intent);
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        aboutUsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        timingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNearestTime();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule();
            }
        });

        // Initialize database operations
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase(); // Open database for writing

            // Insert data into the database
            insertData(db, "T10DB", "VELLORE", "21:50:00", "APPUTHURAI", "22:00:00");
            insertData(db, "T11 G", "VELLORE", "07:00:00", "A.K.PADAVEDU", "08:20:00");
            insertData(db, "T11 G", "VELLORE", "14:20:00", "A.K.PADAVEDU", "15:40:00");
            insertData(db, "T11 G", "VELLORE", "18:45:00", "A.K.PADAVEDU", "20:05:00");
            insertData(db, "T 3AA", "VELLORE", "05:20:00", "ADUKAMPARAI", "05:50:00");
            insertData(db, "T11 G", "VELLORE", "12:45:00", "ADUKAMPARAI", "13:15:00");
            insertData(db, "T11 G", "VELLORE", "17:30:00", "ADUKAMPARAI", "18:00:00");
            insertData(db, "T11BA", "VELLORE", "09:35:00", "ADUKAMPARAI", "10:05:00");
            insertData(db, "T11BA", "VELLORE", "20:00:00", "ADUKAMPARAI", "20:30:00");
            insertData(db, "T22 A", "VELLORE", "06:25:00", "ADUKAMPARAI", "06:55:00");
            insertData(db, "T P", "VELLORE", "09:42:00", "ADUKAMPARAI G.H.", "10:20:00");
            insertData(db, "T 2 CU", "VELLORE", "10:40:00", "ADUKAMPARAI G.H.", "11:00:00");
            insertData(db, "T16 A", "VELLORE", "05:45:00", "ADUKAMPARAI G.H.", "06:15:00");
            insertData(db, "T16FB", "VELLORE", "11:30:00", "ADUKAMPARAI G.H.", "12:00:00");
            insertData(db, "T17KA", "VELLORE", "11:00:00", "ADUKKAMPARAI", "11:30:00");
            insertData(db, "T13 A", "VELLORE", "20:00:00", "AGARAM", "21:20:00");
            insertData(db, "T19 A", "VELLORE", "05:45:00", "AGARAMCHERI", "06:40:00");
            insertData(db, "B51 A", "VELLORE", "16:55:00", "AMATHUR", "17:05:00");
            insertData(db, "T 2KA", "VELLORE", "05:05:00", "AMIRTHI", "06:10:00");

            insertData(db, "T11 G", "VELLORE", "14:20:00", "A.K.PADAVEDU", "15:40:00");
            insertData(db, "T11 G", "VELLORE", "18:45:00", "A.K.PADAVEDU", "20:05:00");
            insertData(db, "T 3AA", "VELLORE", "05:20:00", "ADUKAMPARAI", "05:50:00");
            insertData(db, "T11 G", "VELLORE", "12:45:00", "ADUKAMPARAI", "13:15:00");
            insertData(db, "T11 G", "VELLORE", "17:30:00", "ADUKAMPARAI", "18:00:00");
            insertData(db, "T11BA", "VELLORE", "09:35:00", "ADUKAMPARAI", "10:05:00");
            insertData(db, "T11BA", "VELLORE", "20:00:00", "ADUKAMPARAI", "20:30:00");
            insertData(db, "T22 A", "VELLORE", "06:25:00", "ADUKAMPARAI", "06:55:00");
            insertData(db, "T P", "VELLORE", "09:42:00", "ADUKAMPARAI G.H.", "10:20:00");
            insertData(db, "T 2 CU", "VELLORE", "10:40:00", "ADUKAMPARAI G.H.", "11:00:00");
            insertData(db, "T16 A", "VELLORE", "05:45:00", "ADUKAMPARAI G.H.", "06:15:00");
            insertData(db, "T16FB", "VELLORE", "11:30:00", "ADUKAMPARAI G.H.", "12:00:00");
            insertData(db, "T17KA", "VELLORE", "11:00:00", "ADUKKAMPARAI", "11:30:00");
            insertData(db, "T13 A", "VELLORE", "20:00:00", "AGARAM", "21:20:00");
            insertData(db, "T19 A", "VELLORE", "05:45:00", "AGARAMCHERI", "06:40:00");
            insertData(db, "B51 A", "VELLORE", "16:55:00", "AMATHUR", "17:05:00");
            insertData(db, "T 2KA", "VELLORE", "05:05:00", "AMIRTHI", "06:10:00");
            insertData(db,"T18 A","VELLORE","06:40:00","YELAGIRI","08:10:00");
            insertData(db,"T18 A","VELLORE","10:20:00","YELAGIRI","11:50:00");
            insertData(db,"T18 A","VELLORE","14:30:00","YELAGIRI","16:00:00");
            insertData(db,"T18 A","VELLORE","17:40:00","YELAGIRI","19:10:00");
            insertData(db,"T18 A","VELLORE","20:50:00","YELAGIRI","22:20:00");
            insertData(db, "T 9 B", "VELLORE", "09:50:00", "PALLIPATTU", "10:40:00");
            insertData(db, "T 9 B", "VELLORE", "14:30:00", "PALLIPATTU", "15:20:00");
            insertData(db, "T 9 B", "VELLORE", "18:30:00", "PALLIPATTU", "19:20:00");
            insertData(db, "T 9 B", "VELLORE", "21:20:00", "PALLIPATTU", "22:10:00");
            insertData(db, "T11 D", "VELLORE", "06:30:00", "PALLIPATTU", "08:10:00");
            insertData(db, "T11 D", "VELLORE", "10:10:00", "PALLIPATTU", "11:50:00");
            insertData(db, "T11 D", "VELLORE", "14:00:00", "PALLIPATTU", "15:40:00");
            insertData(db, "T11 D", "VELLORE", "17:20:00", "PALLIPATTU", "19:00:00");
            insertData(db, "T11 D", "VELLORE", "20:50:00", "PALLIPATTU", "22:30:00");
            insertData(db, "T 5 B", "VELLORE", "07:00:00", "PALLIPATTU", "07:45:00");
            insertData(db, "T 5 B", "VELLORE", "09:30:00", "PALLIPATTU", "10:15:00");
            insertData(db, "T 5 B", "VELLORE", "12:50:00", "PALLIPATTU", "13:35:00");
            insertData(db, "T 5 B", "VELLORE", "16:00:00", "PALLIPATTU", "16:45:00");
            insertData(db, "T 5 B", "VELLORE", "18:30:00", "PALLIPATTU", "19:15:00");
            insertData(db, "T 5 B", "VELLORE", "21:15:00", "PALLIPATTU", "22:00:00");
            insertData(db, "T 1 B", "VELLORE", "07:00:00", "PALLIPATTU", "07:55:00");
            insertData(db, "T 1 B", "VELLORE", "09:40:00", "PALLIPATTU", "10:35:00");
            insertData(db, "T 1 B", "VELLORE", "14:20:00", "PALLIPATTU", "15:15:00");
            insertData(db, "T 1 B", "VELLORE", "17:30:00", "PALLIPATTU", "18:25:00");
            insertData(db, "T 1 B", "VELLORE", "20:30:00", "PALLIPATTU", "21:25:00");
            insertData(db, "T 3 C", "VELLORE", "06:40:00", "PANAPAKKAM", "07:40:00");
            insertData(db, "T 3 C", "VELLORE", "09:50:00", "PANAPAKKAM", "10:50:00");
            insertData(db, "T 3 C", "VELLORE", "13:30:00", "PANAPAKKAM", "14:30:00");
            insertData(db, "T 3 C", "VELLORE", "16:20:00", "PANAPAKKAM", "17:20:00");
            insertData(db, "T 3 C", "VELLORE", "18:50:00", "PANAPAKKAM", "19:50:00");
            insertData(db, "T 3 C", "VELLORE", "21:20:00", "PANAPAKKAM", "22:20:00");
            insertData(db, "T 2 D", "VELLORE", "05:30:00", "PANAPPATTU", "06:00:00");
            insertData(db, "T 2 D", "VELLORE", "09:40:00", "PANAPPATTU", "10:10:00");
            insertData(db, "T 2 D", "VELLORE", "14:00:00", "PANAPPATTU", "14:30:00");
            insertData(db, "T 2 D", "VELLORE", "17:10:00", "PANAPPATTU", "17:40:00");
            insertData(db, "T 2 D", "VELLORE", "19:30:00", "PANAPPATTU", "20:00:00");
            insertData(db, "T 2 D", "VELLORE", "21:50:00", "PANAPPATTU", "22:20:00");
            insertData(db, "T 1 C", "VELLORE", "07:10:00", "PANAPPATTU", "07:45:00");
            insertData(db, "T 1 C", "VELLORE", "09:20:00", "PANAPPATTU", "09:55:00");
            insertData(db, "T 1 C", "VELLORE", "13:00:00", "PANAPPATTU", "13:35:00");
            insertData(db, "T 1 C", "VELLORE", "15:50:00", "PANAPPATTU", "16:25:00");
            insertData(db, "T 1 C", "VELLORE", "19:00:00", "PANAPPATTU", "19:35:00");
            insertData(db, "T 1 C", "VELLORE", "22:10:00", "PANAPPATTU", "22:45:00");

            insertData(db,"T10DB","KATPADI","07:30:00","66 PUTHUR","08:10:00");
            insertData(db,"T10DB","KATPADI","09:15:00","66 PUTHUR","09:45:00");
            insertData(db,"T10DB","KATPADI","16:15:00","66 PUTHUR","16:35:00");
            insertData(db,"T10DB","KATPADI","17:00:00","66 PUTHUR","17:30:00");
            insertData(db,"T 2AA","KATPADI","14:38:00","ADUKAMPARAI G.H.","15:21:00");
            insertData(db,"T 1GB","KATPADI","05:00:00","ADUKAMPARAI G.H.","05:50:00");
            insertData(db,"T 1GB","KATPADI","09:25:00","ADUKAMPARAI G.H.","10:25:00");
            insertData(db,"T 1GB","KATPADI","11:40:00","ADUKAMPARAI G.H.","12:40:00");
            insertData(db,"T 1GB","KATPADI","14:00:00","ADUKAMPARAI G.H.","15:00:00");
            insertData(db,"T 1GB","KATPADI","16:20:00","ADUKAMPARAI G.H.","17:20:00");
            insertData(db,"T 1GB","KATPADI","18:40:00","ADUKAMPARAI G.H.","19:40:00");
            insertData(db,"T Q","KATPADI","20:23:00","ADUKAMPARAI G.H.","21:40:00");
            insertData(db,"T12 A","KATPADI","16:10:00","ARCOT","17:10:00");
            insertData(db,"T10AC","KATPADI","16:30:00","ARCOT","17:30:00");
            insertData(db,"T10AC","KATPADI","19:10:00","ARCOT","20:10:00");
            insertData(db,"T10AB","KATPADI","07:40:00","ARCOT","08:55:00");
            insertData(db,"T10AB","KATPADI","10:20:00","ARCOT","11:30:00");
            insertData(db,"T10AB","KATPADI","16:00:00","ARCOT","17:05:00");
            insertData(db,"T10 M","KATPADI","06:10:00","ARCOT","07:10:00");
            insertData(db,"T10DA","KATPADI","08:05:00","AUXILIM COLLEGE","08:15:00");
            insertData(db,"T20 R","KATPADI","07:35:00","BAGAYAM","08:15:00");
            insertData(db,"T20 R","KATPADI","14:00:00","BAGAYAM","14:40:00");
            insertData(db,"T 2AA","KATPADI","13:03:00","BAGAYAM","13:48:00");
            insertData(db,"T 2 CU","KATPADI","12:05:00","BAGAYAM","12:50:00");
            insertData(db,"T 1GA","KATPADI","06:38:00","BAGAYAM","07:38:00");
            insertData(db,"T 1GA","KATPADI","08:54:00","BAGAYAM","09:54:00");
            insertData(db,"T 1GA","KATPADI","10:59:00","BAGAYAM","11:59:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","BAGAYAM","14:03:00");
            insertData(db,"T 1GA","KATPADI","15:33:00","BAGAYAM","16:33:00");
            insertData(db,"T 1GA","KATPADI","17:38:00","BAGAYAM","18:38:00");
            insertData(db,"T 1GA","KATPADI","19:58:00","BAGAYAM","20:58:00");
            insertData(db,"T 1GA","KATPADI","22:23:00","BAGAYAM","23:23:00");
            insertData(db,"T20 S","KATPADI","08:23:00","BRAHMANAGIRI","09:03:00");
            insertData(db,"T20 S","KATPADI","12:10:00","BRAHMANAGIRI","12:50:00");
            insertData(db,"T20 S","KATPADI","17:15:00","BRAHMANAGIRI","17:55:00");
            insertData(db,"T20 S","KATPADI","21:40:00","BRAHMANAGIRI","22:20:00");
            insertData(db,"T 2AA","KATPADI","15:55:00","CHETTITHANGAL","16:27:00");
            insertData(db,"T 1GA","KATPADI","06:18:00","CHETTITHANGAL","06:48:00");
            insertData(db,"T 1GA","KATPADI","09:13:00","CHETTITHANGAL","09:43:00");
            insertData(db,"T 1GA","KATPADI","11:28:00","CHETTITHANGAL","11:58:00");
            insertData(db,"T 1GA","KATPADI","13:58:00","CHETTITHANGAL","14:28:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","CHETTITHANGAL","16:33:00");
            insertData(db,"T 1GA","KATPADI","18:23:00","CHETTITHANGAL","18:53:00");
            insertData(db,"T 1GA","KATPADI","20:43:00","CHETTITHANGAL","21:13:00");
            insertData(db,"T 1GA","KATPADI","23:08:00","CHETTITHANGAL","23:38:00");
            insertData(db,"T20 R","KATPADI","07:20:00","CHETTITHANGAL","07:52:00");
            insertData(db,"T20 R","KATPADI","13:45:00","CHETTITHANGAL","14:17:00");
            insertData(db,"T 1GA","KATPADI","07:18:00","CHITTOOR","08:18:00");
            insertData(db,"T 1GA","KATPADI","12:38:00","CHITTOOR","13:38:00");
            insertData(db,"T 1GA","KATPADI","16:28:00","CHITTOOR","17:28:00");
            insertData(db,"T 1GA","KATPADI","19:48:00","CHITTOOR","20:48:00");
            insertData(db,"T 2 CU","KATPADI","11:15:00","GANDHI NAGAR","11:45:00");
            insertData(db,"T20 R","KATPADI","09:30:00","GANDHI NAGAR","10:10:00");
            insertData(db,"T20 R","KATPADI","15:55:00","GANDHI NAGAR","16:35:00");
            insertData(db,"T 1GA","KATPADI","07:53:00","GANDHI NAGAR","08:53:00");
            insertData(db,"T 1GA","KATPADI","12:23:00","GANDHI NAGAR","13:23:00");
            insertData(db,"T 1GA","KATPADI","15:38:00","GANDHI NAGAR","16:38:00");
            insertData(db,"T 1GA","KATPADI","18:13:00","GANDHI NAGAR","19:13:00");
            insertData(db,"T 1GA","KATPADI","21:33:00","GANDHI NAGAR","22:33:00");
            insertData(db,"T20 R","KATPADI","08:40:00","HOLY CROSS","09:20:00");
            insertData(db,"T20 R","KATPADI","15:00:00","HOLY CROSS","15:40:00");
            insertData(db,"T20 R","KATPADI","18:30:00","HOLY CROSS","19:10:00");
            insertData(db,"T20 R","KATPADI","21:20:00","HOLY CROSS","22:00:00");
            insertData(db,"T 2AA","KATPADI","14:20:00","IRAL","14:50:00");
            insertData(db,"T20 R","KATPADI","06:35:00","IRAL","07:05:00");
            insertData(db,"T20 R","KATPADI","13:00:00","IRAL","13:30:00");
            insertData(db,"T20 R","KATPADI","16:20:00","IRAL","16:50:00");
            insertData(db,"T20 R","KATPADI","18:45:00","IRAL","19:15:00");
            insertData(db,"T20 R","KATPADI","20:55:00","IRAL","21:25:00");
            insertData(db,"T20 S","KATPADI","09:30:00","IRAL","10:10:00");
            insertData(db,"T20 S","KATPADI","14:50:00","IRAL","15:30:00");
            insertData(db,"T20 S","KATPADI","19:10:00","IRAL","19:50:00");
            insertData(db,"T 2AA","KATPADI","08:30:00","IRAL","09:00:00");
            insertData(db,"T20 S","KATPADI","07:20:00","IRAL","08:00:00");
            insertData(db,"T 1GA","KATPADI","05:48:00","JOLARPETTAI","07:18:00");
            insertData(db,"T 1GA","KATPADI","08:43:00","JOLARPETTAI","10:13:00");
            insertData(db,"T 1GA","KATPADI","11:58:00","JOLARPETTAI","13:28:00");
            insertData(db,"T 1GA","KATPADI","14:58:00","JOLARPETTAI","16:28:00");
            insertData(db,"T 1GA","KATPADI","17:03:00","JOLARPETTAI","18:33:00");
            insertData(db,"T 1GA","KATPADI","19:23:00","JOLARPETTAI","20:53:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","JOLARPETTAI","23:18:00");
            insertData(db,"T 2 CU","KATPADI","05:55:00","JOLARPETTAI","06:25:00");
            insertData(db,"T20 R","KATPADI","08:05:00","JOLARPETTAI","08:35:00");
            insertData(db,"T20 R","KATPADI","12:30:00","JOLARPETTAI","13:00:00");
            insertData(db,"T20 R","KATPADI","15:50:00","JOLARPETTAI","16:20:00");
            insertData(db,"T20 R","KATPADI","18:15:00","JOLARPETTAI","18:45:00");
            insertData(db,"T20 R","KATPADI","20:25:00","JOLARPETTAI","20:55:00");
            insertData(db,"T20 S","KATPADI","09:05:00","JOLARPETTAI","09:45:00");
            insertData(db,"T20 S","KATPADI","14:25:00","JOLARPETTAI","15:05:00");
            insertData(db,"T20 S","KATPADI","18:45:00","JOLARPETTAI","19:25:00");
            insertData(db,"T 2AA","KATPADI","05:55:00","KANNEKAL","06:30:00");
            insertData(db,"T20 R","KATPADI","09:05:00","KANNEKAL","09:40:00");
            insertData(db,"T20 R","KATPADI","14:25:00","KANNEKAL","15:00:00");
            insertData(db,"T20 R","KATPADI","18:45:00","KANNEKAL","19:20:00");
            insertData(db,"T20 S","KATPADI","07:55:00","KANNEKAL","08:30:00");
            insertData(db,"T20 S","KATPADI","13:15:00","KANNEKAL","13:50:00");
            insertData(db,"T20 S","KATPADI","17:35:00","KANNEKAL","18:10:00");
            insertData(db,"T 2 CU","KATPADI","08:00:00","KODURU","08:45:00");
            insertData(db,"T20 R","KATPADI","07:05:00","KODURU","07:50:00");
            insertData(db,"T20 R","KATPADI","13:15:00","KODURU","14:00:00");
            insertData(db,"T20 R","KATPADI","16:35:00","KODURU","17:20:00");
            insertData(db,"T20 R","KATPADI","18:55:00","KODURU","19:40:00");
            insertData(db,"T20 R","KATPADI","20:55:00","KODURU","21:40:00");
            insertData(db,"T20 S","KATPADI","09:55:00","KODURU","10:40:00");
            insertData(db,"T20 S","KATPADI","15:05:00","KODURU","15:50:00");
            insertData(db,"T20 S","KATPADI","19:25:00","KODURU","20:10:00");
            insertData(db,"T 2 CU","KATPADI","08:20:00","KONDAPURAM","09:00:00");
            insertData(db,"T20 R","KATPADI","07:35:00","KONDAPURAM","08:15:00");
            insertData(db,"T20 R","KATPADI","13:45:00","KONDAPURAM","14:25:00");
            insertData(db,"T20 R","KATPADI","17:05:00","KONDAPURAM","17:45:00");
            insertData(db,"T20 R","KATPADI","19:25:00","KONDAPURAM","20:05:00");
            insertData(db,"T20 R","KATPADI","21:35:00","KONDAPURAM","22:15:00");
            insertData(db,"T20 S","KATPADI","09:25:00","KONDAPURAM","10:05:00");
            insertData(db,"T20 S","KATPADI","14:35:00","KONDAPURAM","15:15:00");
            insertData(db,"T20 S","KATPADI","18:55:00","KONDAPURAM","19:35:00");
            insertData(db,"T 2AA","KATPADI","11:40:00","KRISHNAPURAM","12:20:00");
            insertData(db,"T20 R","KATPADI","09:55:00","KRISHNAPURAM","10:35:00");
            insertData(db,"T20 R","KATPADI","15:15:00","KRISHNAPURAM","15:55:00");
            insertData(db,"T20 R","KATPADI","19:35:00","KRISHNAPURAM","20:15:00");
            insertData(db,"T20 S","KATPADI","08:25:00","KRISHNAPURAM","09:05:00");
            insertData(db,"T20 S","KATPADI","13:45:00","KRISHNAPURAM","14:25:00");
            insertData(db,"T20 S","KATPADI","18:05:00","KRISHNAPURAM","18:45:00");
            insertData(db,"T20 S","KATPADI","21:05:00","KRISHNAPURAM","21:45:00");
            insertData(db,"T 2 CU","KATPADI","06:10:00","KUPPAM","06:50:00");
            insertData(db,"T20 R","KATPADI","07:20:00","KUPPAM","08:00:00");
            insertData(db,"T20 R","KATPADI","13:30:00","KUPPAM","14:10:00");
            insertData(db,"T20 R","KATPADI","16:50:00","KUPPAM","17:30:00");
            insertData(db,"T20 R","KATPADI","19:10:00","KUPPAM","19:50:00");
            insertData(db,"T20 R","KATPADI","21:10:00","KUPPAM","21:50:00");
            insertData(db,"T20 S","KATPADI","08:00:00","KUPPAM","08:40:00");
            insertData(db,"T20 S","KATPADI","14:10:00","KUPPAM","14:50:00");
            insertData(db,"T20 S","KATPADI","18:30:00","KUPPAM","19:10:00");
            insertData(db,"T20 S","KATPADI","21:30:00","KUPPAM","22:10:00");
            insertData(db,"T 2 CU","KATPADI","09:40:00","MADANAPALLE","10:10:00");
            insertData(db,"T20 R","KATPADI","07:55:00","MADANAPALLE","08:25:00");
            insertData(db,"T20 R","KATPADI","14:05:00","MADANAPALLE","14:35:00");
            insertData(db,"T20 R","KATPADI","17:25:00","MADANAPALLE","17:55:00");
            insertData(db,"T20 R","KATPADI","19:45:00","MADANAPALLE","20:15:00");
            insertData(db,"T20 R","KATPADI","21:55:00","MADANAPALLE","22:25:00");
            insertData(db,"T20 S","KATPADI","08:25:00","MADANAPALLE","08:55:00");
            insertData(db,"T20 S","KATPADI","13:35:00","MADANAPALLE","14:05:00");
            insertData(db,"T20 S","KATPADI","17:55:00","MADANAPALLE","18:25:00");
            insertData(db,"T20 S","KATPADI","21:05:00","MADANAPALLE","21:35:00");
            insertData(db,"T 2 CU","KATPADI","08:10:00","MELVISHARAM","08:45:00");
            insertData(db,"T20 R","KATPADI","07:25:00","MELVISHARAM","08:00:00");
            insertData(db,"T20 R","KATPADI","13:35:00","MELVISHARAM","14:10:00");
            insertData(db,"T20 R","KATPADI","16:55:00","MELVISHARAM","17:30:00");
            insertData(db,"T20 R","KATPADI","19:15:00","MELVISHARAM","19:50:00");
            insertData(db,"T20 R","KATPADI","21:15:00","MELVISHARAM","21:50:00");
            insertData(db,"T20 S","KATPADI","08:05:00","MELVISHARAM","08:40:00");
            insertData(db,"T20 S","KATPADI","14:15:00","MELVISHARAM","14:50:00");
            insertData(db,"T20 S","KATPADI","18:35:00","MELVISHARAM","19:10:00");
            insertData(db,"T20 S","KATPADI","21:35:00","MELVISHARAM","22:10:00");
            insertData(db,"T20 R","KATPADI","08:45:00","NATRAMPALLI","09:15:00");
            insertData(db,"T20 R","KATPADI","15:05:00","NATRAMPALLI","15:35:00");
            insertData(db,"T20 R","KATPADI","18:25:00","NATRAMPALLI","18:55:00");
            insertData(db,"T20 R","KATPADI","21:35:00","NATRAMPALLI","22:05:00");
            insertData(db,"T20 S","KATPADI","09:15:00","NATRAMPALLI","09:45:00");
            insertData(db,"T20 S","KATPADI","14:35:00","NATRAMPALLI","15:05:00");
            insertData(db,"T20 S","KATPADI","18:55:00","NATRAMPALLI","19:25:00");
            insertData(db,"T20 S","KATPADI","21:55:00","NATRAMPALLI","22:25:00");
            insertData(db,"T 1GA","KATPADI","06:43:00","PACHACHAMPALLI","07:43:00");
            insertData(db,"T 1GA","KATPADI","11:03:00","PACHACHAMPALLI","12:03:00");
            insertData(db,"T 1GA","KATPADI","15:28:00","PACHACHAMPALLI","16:28:00");
            insertData(db,"T 1GA","KATPADI","18:38:00","PACHACHAMPALLI","19:38:00");
            insertData(db,"T 1GA","KATPADI","21:58:00","PACHACHAMPALLI","22:58:00");
            insertData(db,"T20 R","KATPADI","09:10:00","PACHACHAMPALLI","09:40:00");
            insertData(db,"T20 R","KATPADI","15:30:00","PACHACHAMPALLI","16:00:00");
            insertData(db,"T20 R","KATPADI","18:50:00","PACHACHAMPALLI","19:20:00");
            insertData(db,"T20 R","KATPADI","21:50:00","PACHACHAMPALLI","22:20:00");
            insertData(db,"T20 S","KATPADI","08:00:00","PACHACHAMPALLI","08:30:00");
            insertData(db,"T20 S","KATPADI","14:20:00","PACHACHAMPALLI","14:50:00");
            insertData(db,"T20 S","KATPADI","18:40:00","PACHACHAMPALLI","19:10:00");
            insertData(db,"T20 S","KATPADI","21:40:00","PACHACHAMPALLI","22:10:00");
            insertData(db,"T20 R","KATPADI","09:35:00","PALLIKONDA","10:10:00");
            insertData(db,"T20 R","KATPADI","14:55:00","PALLIKONDA","15:30:00");
            insertData(db,"T20 R","KATPADI","19:15:00","PALLIKONDA","19:50:00");
            insertData(db,"T20 S","KATPADI","08:35:00","PALLIKONDA","09:10:00");
            insertData(db,"T20 S","KATPADI","13:55:00","PALLIKONDA","14:30:00");
            insertData(db,"T20 S","KATPADI","18:15:00","PALLIKONDA","18:50:00");
            insertData(db,"T20 S","KATPADI","21:15:00","PALLIKONDA","21:50:00");
            insertData(db,"T 2 CU","KATPADI","07:15:00","PALMANER","08:15:00");
            insertData(db,"T20 R","KATPADI","06:05:00","PALMANER","07:05:00");
            insertData(db,"T20 R","KATPADI","12:45:00","PALMANER","13:45:00");
            insertData(db,"T20 R","KATPADI","15:05:00","PALMANER","16:05:00");
            insertData(db,"T20 R","KATPADI","17:35:00","PALMANER","18:35:00");
            insertData(db,"T20 R","KATPADI","20:25:00","PALMANER","21:25:00");
            insertData(db,"T20 S","KATPADI","06:30:00","PALMANER","07:30:00");
            insertData(db,"T20 S","KATPADI","13:05:00","PALMANER","14:05:00");
            insertData(db,"T20 S","KATPADI","15:25:00","PALMANER","16:25:00");
            insertData(db,"T20 S","KATPADI","17:55:00","PALMANER","18:55:00");
            insertData(db,"T20 S","KATPADI","20:45:00","PALMANER","21:45:00");
            insertData(db,"T 1GA","KATPADI","06:13:00","PILER","07:13:00");
            insertData(db,"T 1GA","KATPADI","08:48:00","PILER","09:48:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","PILER","14:03:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","PILER","17:03:00");
            insertData(db,"T 1GA","KATPADI","18:28:00","PILER","19:28:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","PILER","22:48:00");
            insertData(db,"T20 R","KATPADI","09:00:00","PILER","09:30:00");
            insertData(db,"T20 R","KATPADI","15:20:00","PILER","15:50:00");
            insertData(db,"T20 R","KATPADI","18:40:00","PILER","19:10:00");
            insertData(db,"T20 R","KATPADI","21:40:00","PILER","22:10:00");
            insertData(db,"T20 S","KATPADI","08:20:00","PILER","08:50:00");
            insertData(db,"T20 S","KATPADI","14:40:00","PILER","15:10:00");
            insertData(db,"T20 S","KATPADI","19:00:00","PILER","19:30:00");
            insertData(db,"T 2 CU","KATPADI","07:55:00","PULLAMPET","08:35:00");
            insertData(db,"T20 R","KATPADI","06:05:00","PULLAMPET","06:45:00");
            insertData(db,"T20 R","KATPADI","12:45:00","PULLAMPET","13:25:00");
            insertData(db,"T20 R","KATPADI","15:05:00","PULLAMPET","15:45:00");
            insertData(db,"T20 R","KATPADI","17:35:00","PULLAMPET","18:15:00");
            insertData(db,"T20 R","KATPADI","20:25:00","PULLAMPET","21:05:00");
            insertData(db,"T20 S","KATPADI","06:30:00","PULLAMPET","07:10:00");
            insertData(db,"T20 S","KATPADI","13:05:00","PULLAMPET","13:45:00");
            insertData(db,"T20 S","KATPADI","15:25:00","PULLAMPET","16:05:00");
            insertData(db,"T20 S","KATPADI","17:55:00","PULLAMPET","18:35:00");
            insertData(db,"T20 S","KATPADI","20:45:00","PULLAMPET","21:25:00");
            insertData(db,"T 1GA","KATPADI","06:13:00","RAYALACHERUVU","07:33:00");
            insertData(db,"T 1GA","KATPADI","08:48:00","RAYALACHERUVU","10:08:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","RAYALACHERUVU","14:23:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","RAYALACHERUVU","17:23:00");
            insertData(db,"T 1GA","KATPADI","18:28:00","RAYALACHERUVU","19:48:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","RAYALACHERUVU","23:08:00");
            insertData(db,"T20 R","KATPADI","09:00:00","RAYALACHERUVU","10:20:00");
            insertData(db,"T20 R","KATPADI","15:20:00","RAYALACHERUVU","16:40:00");
            insertData(db,"T20 R","KATPADI","18:40:00","RAYALACHERUVU","20:00:00");
            insertData(db,"T20 R","KATPADI","21:40:00","RAYALACHERUVU","23:00:00");
            insertData(db,"T20 S","KATPADI","08:20:00","RAYALACHERUVU","09:40:00");
            insertData(db,"T18 A","VELLORE","06:40:00","YELAGIRI","08:10:00");
            insertData(db,"T18 A","VELLORE","10:20:00","YELAGIRI","11:50:00");
            insertData(db,"T18 A","VELLORE","14:30:00","YELAGIRI","16:00:00");
            insertData(db,"T18 A","VELLORE","17:40:00","YELAGIRI","19:10:00");
            insertData(db,"T18 A","VELLORE","20:50:00","YELAGIRI","22:20:00");
            insertData(db, "T 9 B", "VELLORE", "09:50:00", "PALLIPATTU", "10:40:00");
            insertData(db, "T 9 B", "VELLORE", "14:30:00", "PALLIPATTU", "15:20:00");
            insertData(db, "T 9 B", "VELLORE", "18:30:00", "PALLIPATTU", "19:20:00");
            insertData(db, "T 9 B", "VELLORE", "21:20:00", "PALLIPATTU", "22:10:00");
            insertData(db, "T11 D", "VELLORE", "06:30:00", "PALLIPATTU", "08:10:00");
            insertData(db, "T11 D", "VELLORE", "10:10:00", "PALLIPATTU", "11:50:00");
            insertData(db, "T11 D", "VELLORE", "14:00:00", "PALLIPATTU", "15:40:00");
            insertData(db, "T11 D", "VELLORE", "17:20:00", "PALLIPATTU", "19:00:00");
            insertData(db, "T11 D", "VELLORE", "20:50:00", "PALLIPATTU", "22:30:00");
            insertData(db, "T 5 B", "VELLORE", "07:00:00", "PALLIPATTU", "07:45:00");
            insertData(db, "T 5 B", "VELLORE", "09:30:00", "PALLIPATTU", "10:15:00");
            insertData(db, "T 5 B", "VELLORE", "12:50:00", "PALLIPATTU", "13:35:00");
            insertData(db, "T 5 B", "VELLORE", "16:00:00", "PALLIPATTU", "16:45:00");
            insertData(db, "T 5 B", "VELLORE", "18:30:00", "PALLIPATTU", "19:15:00");
            insertData(db, "T 5 B", "VELLORE", "21:15:00", "PALLIPATTU", "22:00:00");
            insertData(db, "T 1 B", "VELLORE", "07:00:00", "PALLIPATTU", "07:55:00");
            insertData(db, "T 1 B", "VELLORE", "09:40:00", "PALLIPATTU", "10:35:00");
            insertData(db, "T 1 B", "VELLORE", "14:20:00", "PALLIPATTU", "15:15:00");
            insertData(db, "T 1 B", "VELLORE", "17:30:00", "PALLIPATTU", "18:25:00");
            insertData(db, "T 1 B", "VELLORE", "20:30:00", "PALLIPATTU", "21:25:00");
            insertData(db, "T 3 C", "VELLORE", "06:40:00", "PANAPAKKAM", "07:40:00");
            insertData(db, "T 3 C", "VELLORE", "09:50:00", "PANAPAKKAM", "10:50:00");
            insertData(db, "T 3 C", "VELLORE", "13:30:00", "PANAPAKKAM", "14:30:00");
            insertData(db, "T 3 C", "VELLORE", "16:20:00", "PANAPAKKAM", "17:20:00");
            insertData(db, "T 3 C", "VELLORE", "18:50:00", "PANAPAKKAM", "19:50:00");
            insertData(db, "T 3 C", "VELLORE", "21:20:00", "PANAPAKKAM", "22:20:00");
            insertData(db, "T 2 D", "VELLORE", "05:30:00", "PANAPPATTU", "06:00:00");
            insertData(db, "T 2 D", "VELLORE", "09:40:00", "PANAPPATTU", "10:10:00");
            insertData(db, "T 2 D", "VELLORE", "14:00:00", "PANAPPATTU", "14:30:00");
            insertData(db, "T 2 D", "VELLORE", "17:10:00", "PANAPPATTU", "17:40:00");
            insertData(db, "T 2 D", "VELLORE", "19:30:00", "PANAPPATTU", "20:00:00");
            insertData(db, "T 2 D", "VELLORE", "21:50:00", "PANAPPATTU", "22:20:00");
            insertData(db, "T 1 C", "VELLORE", "07:10:00", "PANAPPATTU", "07:45:00");
            insertData(db, "T 1 C", "VELLORE", "09:20:00", "PANAPPATTU", "09:55:00");
            insertData(db, "T 1 C", "VELLORE", "13:00:00", "PANAPPATTU", "13:35:00");
            insertData(db, "T 1 C", "VELLORE", "15:50:00", "PANAPPATTU", "16:25:00");
            insertData(db, "T 1 C", "VELLORE", "19:00:00", "PANAPPATTU", "19:35:00");
            insertData(db, "T 1 C", "VELLORE", "22:10:00", "PANAPPATTU", "22:45:00");

            insertData(db,"T10DB","KATPADI","07:30:00","66 PUTHUR","08:10:00");
            insertData(db,"T10DB","KATPADI","09:15:00","66 PUTHUR","09:45:00");
            insertData(db,"T10DB","KATPADI","16:15:00","66 PUTHUR","16:35:00");
            insertData(db,"T10DB","KATPADI","17:00:00","66 PUTHUR","17:30:00");
            insertData(db,"T 2AA","KATPADI","14:38:00","ADUKAMPARAI G.H.","15:21:00");
            insertData(db,"T 1GB","KATPADI","05:00:00","ADUKAMPARAI G.H.","05:50:00");
            insertData(db,"T 1GB","KATPADI","09:25:00","ADUKAMPARAI G.H.","10:25:00");
            insertData(db,"T 1GB","KATPADI","11:40:00","ADUKAMPARAI G.H.","12:40:00");
            insertData(db,"T 1GB","KATPADI","14:00:00","ADUKAMPARAI G.H.","15:00:00");
            insertData(db,"T 1GB","KATPADI","16:20:00","ADUKAMPARAI G.H.","17:20:00");
            insertData(db,"T 1GB","KATPADI","18:40:00","ADUKAMPARAI G.H.","19:40:00");
            insertData(db,"T Q","KATPADI","20:23:00","ADUKAMPARAI G.H.","21:40:00");
            insertData(db,"T12 A","KATPADI","16:10:00","ARCOT","17:10:00");
            insertData(db,"T10AC","KATPADI","16:30:00","ARCOT","17:30:00");
            insertData(db,"T10AC","KATPADI","19:10:00","ARCOT","20:10:00");
            insertData(db,"T10AB","KATPADI","07:40:00","ARCOT","08:55:00");
            insertData(db,"T10AB","KATPADI","10:20:00","ARCOT","11:30:00");
            insertData(db,"T10AB","KATPADI","16:00:00","ARCOT","17:05:00");
            insertData(db,"T10 M","KATPADI","06:10:00","ARCOT","07:10:00");
            insertData(db,"T10DA","KATPADI","08:05:00","AUXILIM COLLEGE","08:15:00");
            insertData(db,"T20 R","KATPADI","07:35:00","BAGAYAM","08:15:00");
            insertData(db,"T20 R","KATPADI","14:00:00","BAGAYAM","14:40:00");
            insertData(db,"T 2AA","KATPADI","13:03:00","BAGAYAM","13:48:00");
            insertData(db,"T 2 CU","KATPADI","12:05:00","BAGAYAM","12:50:00");
            insertData(db,"T 1GA","KATPADI","06:38:00","BAGAYAM","07:38:00");
            insertData(db,"T 1GA","KATPADI","08:54:00","BAGAYAM","09:54:00");
            insertData(db,"T 1GA","KATPADI","10:59:00","BAGAYAM","11:59:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","BAGAYAM","14:03:00");
            insertData(db,"T 1GA","KATPADI","15:33:00","BAGAYAM","16:33:00");
            insertData(db,"T 1GA","KATPADI","17:38:00","BAGAYAM","18:38:00");
            insertData(db,"T 1GA","KATPADI","19:58:00","BAGAYAM","20:58:00");
            insertData(db,"T 1GA","KATPADI","22:23:00","BAGAYAM","23:23:00");
            insertData(db,"T20 S","KATPADI","08:23:00","BRAHMANAGIRI","09:03:00");
            insertData(db,"T20 S","KATPADI","12:10:00","BRAHMANAGIRI","12:50:00");
            insertData(db,"T20 S","KATPADI","17:15:00","BRAHMANAGIRI","17:55:00");
            insertData(db,"T20 S","KATPADI","21:40:00","BRAHMANAGIRI","22:20:00");
            insertData(db,"T 2AA","KATPADI","15:55:00","CHETTITHANGAL","16:27:00");
            insertData(db,"T 1GA","KATPADI","06:18:00","CHETTITHANGAL","06:48:00");
            insertData(db,"T 1GA","KATPADI","09:13:00","CHETTITHANGAL","09:43:00");
            insertData(db,"T 1GA","KATPADI","11:28:00","CHETTITHANGAL","11:58:00");
            insertData(db,"T 1GA","KATPADI","13:58:00","CHETTITHANGAL","14:28:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","CHETTITHANGAL","16:33:00");
            insertData(db,"T 1GA","KATPADI","18:23:00","CHETTITHANGAL","18:53:00");
            insertData(db,"T 1GA","KATPADI","20:43:00","CHETTITHANGAL","21:13:00");
            insertData(db,"T 1GA","KATPADI","23:08:00","CHETTITHANGAL","23:38:00");
            insertData(db,"T20 R","KATPADI","07:20:00","CHETTITHANGAL","07:52:00");
            insertData(db,"T20 R","KATPADI","13:45:00","CHETTITHANGAL","14:17:00");
            insertData(db,"T 1GA","KATPADI","07:18:00","CHITTOOR","08:18:00");
            insertData(db,"T 1GA","KATPADI","12:38:00","CHITTOOR","13:38:00");
            insertData(db,"T 1GA","KATPADI","16:28:00","CHITTOOR","17:28:00");
            insertData(db,"T 1GA","KATPADI","19:48:00","CHITTOOR","20:48:00");
            insertData(db,"T 2 CU","KATPADI","11:15:00","GANDHI NAGAR","11:45:00");
            insertData(db,"T20 R","KATPADI","09:30:00","GANDHI NAGAR","10:10:00");
            insertData(db,"T20 R","KATPADI","15:55:00","GANDHI NAGAR","16:35:00");
            insertData(db,"T 1GA","KATPADI","07:53:00","GANDHI NAGAR","08:53:00");
            insertData(db,"T 1GA","KATPADI","12:23:00","GANDHI NAGAR","13:23:00");
            insertData(db,"T 1GA","KATPADI","15:38:00","GANDHI NAGAR","16:38:00");
            insertData(db,"T 1GA","KATPADI","18:13:00","GANDHI NAGAR","19:13:00");
            insertData(db,"T 1GA","KATPADI","21:33:00","GANDHI NAGAR","22:33:00");
            insertData(db,"T20 R","KATPADI","08:40:00","HOLY CROSS","09:20:00");
            insertData(db,"T20 R","KATPADI","15:00:00","HOLY CROSS","15:40:00");
            insertData(db,"T20 R","KATPADI","18:30:00","HOLY CROSS","19:10:00");
            insertData(db,"T20 R","KATPADI","21:20:00","HOLY CROSS","22:00:00");
            insertData(db,"T 2AA","KATPADI","14:20:00","IRAL","14:50:00");
            insertData(db,"T20 R","KATPADI","06:35:00","IRAL","07:05:00");
            insertData(db,"T20 R","KATPADI","13:00:00","IRAL","13:30:00");
            insertData(db,"T20 R","KATPADI","16:20:00","IRAL","16:50:00");
            insertData(db,"T20 R","KATPADI","18:45:00","IRAL","19:15:00");
            insertData(db,"T20 R","KATPADI","20:55:00","IRAL","21:25:00");
            insertData(db,"T20 S","KATPADI","09:30:00","IRAL","10:10:00");
            insertData(db,"T20 S","KATPADI","14:50:00","IRAL","15:30:00");
            insertData(db,"T20 S","KATPADI","19:10:00","IRAL","19:50:00");
            insertData(db,"T 2AA","KATPADI","08:30:00","IRAL","09:00:00");
            insertData(db,"T20 S","KATPADI","07:20:00","IRAL","08:00:00");
            insertData(db,"T 1GA","KATPADI","05:48:00","JOLARPETTAI","07:18:00");
            insertData(db,"T 1GA","KATPADI","08:43:00","JOLARPETTAI","10:13:00");
            insertData(db,"T 1GA","KATPADI","11:58:00","JOLARPETTAI","13:28:00");
            insertData(db,"T 1GA","KATPADI","14:58:00","JOLARPETTAI","16:28:00");
            insertData(db,"T 1GA","KATPADI","17:03:00","JOLARPETTAI","18:33:00");
            insertData(db,"T 1GA","KATPADI","19:23:00","JOLARPETTAI","20:53:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","JOLARPETTAI","23:18:00");
            insertData(db,"T 2 CU","KATPADI","05:55:00","JOLARPETTAI","06:25:00");
            insertData(db,"T20 R","KATPADI","08:05:00","JOLARPETTAI","08:35:00");
            insertData(db,"T20 R","KATPADI","12:30:00","JOLARPETTAI","13:00:00");
            insertData(db,"T20 R","KATPADI","15:50:00","JOLARPETTAI","16:20:00");
            insertData(db,"T20 R","KATPADI","18:15:00","JOLARPETTAI","18:45:00");
            insertData(db,"T20 R","KATPADI","20:25:00","JOLARPETTAI","20:55:00");
            insertData(db,"T20 S","KATPADI","09:05:00","JOLARPETTAI","09:45:00");
            insertData(db,"T20 S","KATPADI","14:25:00","JOLARPETTAI","15:05:00");
            insertData(db,"T20 S","KATPADI","18:45:00","JOLARPETTAI","19:25:00");
            insertData(db,"T 2AA","KATPADI","05:55:00","KANNEKAL","06:30:00");
            insertData(db,"T20 R","KATPADI","09:05:00","KANNEKAL","09:40:00");
            insertData(db,"T20 R","KATPADI","14:25:00","KANNEKAL","15:00:00");
            insertData(db,"T20 R","KATPADI","18:45:00","KANNEKAL","19:20:00");
            insertData(db,"T20 S","KATPADI","07:55:00","KANNEKAL","08:30:00");
            insertData(db,"T20 S","KATPADI","13:15:00","KANNEKAL","13:50:00");
            insertData(db,"T20 S","KATPADI","17:35:00","KANNEKAL","18:10:00");
            insertData(db,"T 2 CU","KATPADI","08:00:00","KODURU","08:45:00");
            insertData(db,"T20 R","KATPADI","07:05:00","KODURU","07:50:00");
            insertData(db,"T20 R","KATPADI","13:15:00","KODURU","14:00:00");
            insertData(db,"T20 R","KATPADI","16:35:00","KODURU","17:20:00");
            insertData(db,"T20 R","KATPADI","18:55:00","KODURU","19:40:00");
            insertData(db,"T20 R","KATPADI","20:55:00","KODURU","21:40:00");
            insertData(db,"T20 S","KATPADI","09:55:00","KODURU","10:40:00");
            insertData(db,"T20 S","KATPADI","15:05:00","KODURU","15:50:00");
            insertData(db,"T20 S","KATPADI","19:25:00","KODURU","20:10:00");
            insertData(db,"T 2 CU","KATPADI","08:20:00","KONDAPURAM","09:00:00");
            insertData(db,"T20 R","KATPADI","07:35:00","KONDAPURAM","08:15:00");
            insertData(db,"T20 R","KATPADI","13:45:00","KONDAPURAM","14:25:00");
            insertData(db,"T20 R","KATPADI","17:05:00","KONDAPURAM","17:45:00");
            insertData(db,"T20 R","KATPADI","19:25:00","KONDAPURAM","20:05:00");
            insertData(db,"T20 R","KATPADI","21:35:00","KONDAPURAM","22:15:00");
            insertData(db,"T20 S","KATPADI","09:25:00","KONDAPURAM","10:05:00");
            insertData(db,"T20 S","KATPADI","14:35:00","KONDAPURAM","15:15:00");
            insertData(db,"T20 S","KATPADI","18:55:00","KONDAPURAM","19:35:00");
            insertData(db,"T 2AA","KATPADI","11:40:00","KRISHNAPURAM","12:20:00");
            insertData(db,"T20 R","KATPADI","09:55:00","KRISHNAPURAM","10:35:00");
            insertData(db,"T20 R","KATPADI","15:15:00","KRISHNAPURAM","15:55:00");
            insertData(db,"T20 R","KATPADI","19:35:00","KRISHNAPURAM","20:15:00");
            insertData(db,"T20 S","KATPADI","08:25:00","KRISHNAPURAM","09:05:00");
            insertData(db,"T20 S","KATPADI","13:45:00","KRISHNAPURAM","14:25:00");
            insertData(db,"T20 S","KATPADI","18:05:00","KRISHNAPURAM","18:45:00");
            insertData(db,"T20 S","KATPADI","21:05:00","KRISHNAPURAM","21:45:00");
            insertData(db,"T 2 CU","KATPADI","06:10:00","KUPPAM","06:50:00");
            insertData(db,"T20 R","KATPADI","07:20:00","KUPPAM","08:00:00");
            insertData(db,"T20 R","KATPADI","13:30:00","KUPPAM","14:10:00");
            insertData(db,"T20 R","KATPADI","16:50:00","KUPPAM","17:30:00");
            insertData(db,"T20 R","KATPADI","19:10:00","KUPPAM","19:50:00");
            insertData(db,"T20 R","KATPADI","21:10:00","KUPPAM","21:50:00");
            insertData(db,"T20 S","KATPADI","08:00:00","KUPPAM","08:40:00");
            insertData(db,"T20 S","KATPADI","14:10:00","KUPPAM","14:50:00");
            insertData(db,"T20 S","KATPADI","18:30:00","KUPPAM","19:10:00");
            insertData(db,"T20 S","KATPADI","21:30:00","KUPPAM","22:10:00");
            insertData(db,"T 2 CU","KATPADI","09:40:00","MADANAPALLE","10:10:00");
            insertData(db,"T20 R","KATPADI","07:55:00","MADANAPALLE","08:25:00");
            insertData(db,"T20 R","KATPADI","14:05:00","MADANAPALLE","14:35:00");
            insertData(db,"T20 R","KATPADI","17:25:00","MADANAPALLE","17:55:00");
            insertData(db,"T20 R","KATPADI","19:45:00","MADANAPALLE","20:15:00");
            insertData(db,"T20 R","KATPADI","21:55:00","MADANAPALLE","22:25:00");
            insertData(db,"T20 S","KATPADI","08:25:00","MADANAPALLE","08:55:00");
            insertData(db,"T20 S","KATPADI","13:35:00","MADANAPALLE","14:05:00");
            insertData(db,"T20 S","KATPADI","17:55:00","MADANAPALLE","18:25:00");
            insertData(db,"T20 S","KATPADI","21:05:00","MADANAPALLE","21:35:00");
            insertData(db,"T 2 CU","KATPADI","08:10:00","MELVISHARAM","08:45:00");
            insertData(db,"T20 R","KATPADI","07:25:00","MELVISHARAM","08:00:00");
            insertData(db,"T20 R","KATPADI","13:35:00","MELVISHARAM","14:10:00");
            insertData(db,"T20 R","KATPADI","16:55:00","MELVISHARAM","17:30:00");
            insertData(db,"T20 R","KATPADI","19:15:00","MELVISHARAM","19:50:00");
            insertData(db,"T20 R","KATPADI","21:15:00","MELVISHARAM","21:50:00");
            insertData(db,"T20 S","KATPADI","08:05:00","MELVISHARAM","08:40:00");
            insertData(db,"T20 S","KATPADI","14:15:00","MELVISHARAM","14:50:00");
            insertData(db,"T20 S","KATPADI","18:35:00","MELVISHARAM","19:10:00");
            insertData(db,"T20 S","KATPADI","21:35:00","MELVISHARAM","22:10:00");
            insertData(db,"T20 R","KATPADI","08:45:00","NATRAMPALLI","09:15:00");
            insertData(db,"T20 R","KATPADI","15:05:00","NATRAMPALLI","15:35:00");
            insertData(db,"T20 R","KATPADI","18:25:00","NATRAMPALLI","18:55:00");
            insertData(db,"T20 R","KATPADI","21:35:00","NATRAMPALLI","22:05:00");
            insertData(db,"T20 S","KATPADI","09:15:00","NATRAMPALLI","09:45:00");
            insertData(db,"T20 S","KATPADI","14:35:00","NATRAMPALLI","15:05:00");
            insertData(db,"T20 S","KATPADI","18:55:00","NATRAMPALLI","19:25:00");
            insertData(db,"T20 S","KATPADI","21:55:00","NATRAMPALLI","22:25:00");
            insertData(db,"T 1GA","KATPADI","06:43:00","PACHACHAMPALLI","07:43:00");
            insertData(db,"T 1GA","KATPADI","11:03:00","PACHACHAMPALLI","12:03:00");
            insertData(db,"T 1GA","KATPADI","15:28:00","PACHACHAMPALLI","16:28:00");
            insertData(db,"T 1GA","KATPADI","18:38:00","PACHACHAMPALLI","19:38:00");
            insertData(db,"T 1GA","KATPADI","21:58:00","PACHACHAMPALLI","22:58:00");
            insertData(db,"T20 R","KATPADI","09:10:00","PACHACHAMPALLI","09:40:00");
            insertData(db,"T20 R","KATPADI","15:30:00","PACHACHAMPALLI","16:00:00");
            insertData(db,"T20 R","KATPADI","18:50:00","PACHACHAMPALLI","19:20:00");
            insertData(db,"T20 R","KATPADI","21:50:00","PACHACHAMPALLI","22:20:00");
            insertData(db,"T20 S","KATPADI","08:00:00","PACHACHAMPALLI","08:30:00");
            insertData(db,"T20 S","KATPADI","14:20:00","PACHACHAMPALLI","14:50:00");
            insertData(db,"T20 S","KATPADI","18:40:00","PACHACHAMPALLI","19:10:00");
            insertData(db,"T20 S","KATPADI","21:40:00","PACHACHAMPALLI","22:10:00");
            insertData(db,"T20 R","KATPADI","09:35:00","PALLIKONDA","10:10:00");
            insertData(db,"T20 R","KATPADI","14:55:00","PALLIKONDA","15:30:00");
            insertData(db,"T20 R","KATPADI","19:15:00","PALLIKONDA","19:50:00");
            insertData(db,"T20 S","KATPADI","08:35:00","PALLIKONDA","09:10:00");
            insertData(db,"T20 S","KATPADI","13:55:00","PALLIKONDA","14:30:00");
            insertData(db,"T20 S","KATPADI","18:15:00","PALLIKONDA","18:50:00");
            insertData(db,"T20 S","KATPADI","21:15:00","PALLIKONDA","21:50:00");
            insertData(db,"T 2 CU","KATPADI","07:15:00","PALMANER","08:15:00");
            insertData(db,"T20 R","KATPADI","06:05:00","PALMANER","07:05:00");
            insertData(db,"T20 R","KATPADI","12:45:00","PALMANER","13:45:00");
            insertData(db,"T20 R","KATPADI","15:05:00","PALMANER","16:05:00");
            insertData(db,"T20 R","KATPADI","17:35:00","PALMANER","18:35:00");
            insertData(db,"T20 R","KATPADI","20:25:00","PALMANER","21:25:00");
            insertData(db,"T20 S","KATPADI","06:30:00","PALMANER","07:30:00");
            insertData(db,"T20 S","KATPADI","13:05:00","PALMANER","14:05:00");
            insertData(db,"T20 S","KATPADI","15:25:00","PALMANER","16:25:00");
            insertData(db,"T20 S","KATPADI","17:55:00","PALMANER","18:55:00");
            insertData(db,"T20 S","KATPADI","20:45:00","PALMANER","21:45:00");
            insertData(db,"T 1GA","KATPADI","06:13:00","PILER","07:13:00");
            insertData(db,"T 1GA","KATPADI","08:48:00","PILER","09:48:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","PILER","14:03:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","PILER","17:03:00");
            insertData(db,"T 1GA","KATPADI","18:28:00","PILER","19:28:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","PILER","22:48:00");
            insertData(db,"T20 R","KATPADI","09:00:00","PILER","09:30:00");
            insertData(db,"T20 R","KATPADI","15:20:00","PILER","15:50:00");
            insertData(db,"T20 R","KATPADI","18:40:00","PILER","19:10:00");
            insertData(db,"T20 R","KATPADI","21:40:00","PILER","22:10:00");
            insertData(db,"T20 S","KATPADI","08:20:00","PILER","08:50:00");
            insertData(db,"T20 S","KATPADI","14:40:00","PILER","15:10:00");
            insertData(db,"T20 S","KATPADI","19:00:00","PILER","19:30:00");
            insertData(db,"T 2 CU","KATPADI","07:55:00","PULLAMPET","08:35:00");
            insertData(db,"T20 R","KATPADI","06:05:00","PULLAMPET","06:45:00");
            insertData(db,"T20 R","KATPADI","12:45:00","PULLAMPET","13:25:00");
            insertData(db,"T20 R","KATPADI","15:05:00","PULLAMPET","15:45:00");
            insertData(db,"T20 R","KATPADI","17:35:00","PULLAMPET","18:15:00");
            insertData(db,"T20 R","KATPADI","20:25:00","PULLAMPET","21:05:00");
            insertData(db,"T20 S","KATPADI","06:30:00","PULLAMPET","07:10:00");
            insertData(db,"T20 S","KATPADI","13:05:00","PULLAMPET","13:45:00");
            insertData(db,"T20 S","KATPADI","15:25:00","PULLAMPET","16:05:00");
            insertData(db,"T20 S","KATPADI","17:55:00","PULLAMPET","18:35:00");
            insertData(db,"T20 S","KATPADI","20:45:00","PULLAMPET","21:25:00");
            insertData(db,"T 1GA","KATPADI","06:13:00","RAYALACHERUVU","07:33:00");
            insertData(db,"T 1GA","KATPADI","08:48:00","RAYALACHERUVU","10:08:00");
            insertData(db,"T 1GA","KATPADI","13:03:00","RAYALACHERUVU","14:23:00");
            insertData(db,"T 1GA","KATPADI","16:03:00","RAYALACHERUVU","17:23:00");
            insertData(db,"T 1GA","KATPADI","18:28:00","RAYALACHERUVU","19:48:00");
            insertData(db,"T 1GA","KATPADI","21:48:00","RAYALACHERUVU","23:08:00");
            insertData(db,"T20 R","KATPADI","09:00:00","RAYALACHERUVU","10:20:00");
            insertData(db,"T20 R","KATPADI","15:20:00","RAYALACHERUVU","16:40:00");
            insertData(db,"T20 R","KATPADI","18:40:00","RAYALACHERUVU","20:00:00");
            insertData(db,"T20 R","KATPADI","21:40:00","RAYALACHERUVU","23:00:00");
            insertData(db,"T20 S","KATPADI","08:20:00","RAYALACHERUVU","09:40:00");
            insertData(db, "T20 S", "KATPADI", "08:20:00", "RAYALACHERUVU", "09:40:00");
            insertData(db, "T 1G", "KATPADI", "20:25:00", "VANIYAMBADI", "21:50:00");

        } catch (SQLiteException e) {
            Log.e("DatabaseError", e.getMessage());
            Toast.makeText(this, "Error accessing database", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        // Set up the spinners
        ArrayAdapter<String> departureAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"KATPADI", "VELLORE"});
        departureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureSpinner.setAdapter(departureAdapter);

        ArrayAdapter<String> arrivalAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);
        arrivalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Set<String> uniqueDestinations = new HashSet<>();

        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DESTINATION}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String destination = cursor.getString(0);
                    uniqueDestinations.add(destination);
                } while (cursor.moveToNext());
            }
            cursor.close();

            List<String> sortedDestinations = new ArrayList<>(uniqueDestinations);
            Collections.sort(sortedDestinations);

            arrivalAdapter.addAll(sortedDestinations);
            arrivalSpinner.setAdapter(arrivalAdapter);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    private void insertData(SQLiteDatabase db, String route, String source, String sourceTime, String destination, String destinationTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE, route);
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_SOURCE_TIME, sourceTime);
        values.put(COLUMN_DESTINATION, destination);
        values.put(COLUMN_DESTINATION_TIME, destinationTime);
        db.insert(TABLE_NAME, null, values);
    }

    private void showNearestTime() {
        try {
            String departure = departureSpinner.getSelectedItem().toString();
            String source = arrivalSpinner.getSelectedItem().toString();
            String currentTime = getCurrentTime();
            String nearestSourceTime = queryNearestSourceTime(source,departure, currentTime);

            timelyTextView.setText("nearestSourceTime");
//            System.out.println(nearestSourceTime);
            Intent intent = new Intent(HomeActivity.this,TimeActivity.class);
            intent.putExtra("message",nearestSourceTime);
            startActivity(intent);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void schedule() {
        try {
            String departure = departureSpinner.getSelectedItem().toString();
            String source = arrivalSpinner.getSelectedItem().toString();

            q1schedule(source,departure);

            }catch(Exception e){
            System.out.println(e);
        }
    }

    private void qschedule(String source, String departure) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> timingsList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ?",
                    new String[]{departure, source});
            if (cursor.moveToFirst()) {
                int routeIndex = cursor.getColumnIndex(COLUMN_ROUTE);
                int sourceTimeIndex = cursor.getColumnIndex(COLUMN_SOURCE_TIME);
                int destinationTimeIndex = cursor.getColumnIndex(COLUMN_DESTINATION_TIME);
                do {
                    String timing = "ROUTE: " + cursor.getString(routeIndex) + "\n" +
                            "SOURCE TIME: " + cursor.getString(sourceTimeIndex) + "\n" +
                            "DESTINATION TIME: " + cursor.getString(destinationTimeIndex);
                    timingsList.add(timing);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        if (!timingsList.isEmpty()) {
            Intent intent = new Intent(HomeActivity.this, Schedule.class);
            intent.putStringArrayListExtra("timingsList", (ArrayList<String>) timingsList);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No schedule found for the selected source and destination.", Toast.LENGTH_SHORT).show();
        }
    }

    private void q1schedule(String source, String departure) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> timingsList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ?",
                    new String[]{departure, source});
            if (cursor.moveToFirst()) {
                int routeIndex = cursor.getColumnIndex(COLUMN_ROUTE);
                int sourceTimeIndex = cursor.getColumnIndex(COLUMN_SOURCE_TIME);
                int destinationTimeIndex = cursor.getColumnIndex(COLUMN_DESTINATION_TIME);
                do {
                    String timing = "ROUTE: " + cursor.getString(routeIndex) + "\n" +
                            "SOURCE TIME: " + cursor.getString(sourceTimeIndex) + "\n" +
                            "DESTINATION TIME: " + cursor.getString(destinationTimeIndex);
                    timingsList.add(timing);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        if (!timingsList.isEmpty()) {
            Intent intent = new Intent(HomeActivity.this, Schedule.class);
            intent.putStringArrayListExtra("timingsList", (ArrayList<String>) timingsList);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No schedule found for the selected source and destination.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("Range")
    private String queryNearestSourceTime(String arrival,String departure, String currentTime) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String nearestSourceTime = "";

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ? AND " + COLUMN_SOURCE_TIME + " > ? ORDER BY " + COLUMN_SOURCE_TIME + " ASC LIMIT 1",
                    new String[]{departure, arrival, currentTime});
            if (cursor.moveToFirst()) {
                nearestSourceTime = "TABLE_NAME: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
                        "ROUTE: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
                        "SOURCE: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)) + "\n" +
                        "SOURCE TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_TIME)) + "\n" +
                        "DESTINATION: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)) + "\n" +
                        "DESTINATION TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION_TIME));
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return nearestSourceTime;
    }

    @SuppressLint("Range")
    private String queryShedule(String arrival,String departure) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String nearestSourceTime = "";

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ? AND " + COLUMN_SOURCE_TIME + " > ? ORDER BY " + COLUMN_SOURCE_TIME + " ASC LIMIT 1",
                    new String[]{departure, arrival});
            if (cursor.moveToFirst()) {
                nearestSourceTime = "TABLE_NAME: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
                        "ROUTE: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
                        "SOURCE: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)) + "\n" +
                        "SOURCE TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_TIME)) + "\n" +
                        "DESTINATION: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)) + "\n" +
                        "DESTINATION TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION_TIME));
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return nearestSourceTime;
    }

//    private String queryNearestSourceTime1(String arrival, String departure, String currentTime) {
//        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String nearestSourceTime = "";
//
//        try {
//            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
//                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ? AND " + COLUMN_SOURCE_TIME + " > ? ORDER BY " + COLUMN_SOURCE_TIME + " ASC LIMIT 1",
//                    new String[]{departure, arrival, currentTime});
//            if (cursor.moveToFirst()) {
//                nearestSourceTime = "TABLE_NAME: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
//                        "ROUTE: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
//                        "SOURCE: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)) + "\n" +
//                        "SOURCE TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_TIME)) + "\n" +
//                        "DESTINATION: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)) + "\n" +
//                        "DESTINATION TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION_TIME));
//            }
//            cursor.close();
//        } catch (SQLiteException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return nearestSourceTime;
//    }

//    private String queryNearestSourceTime2(String arrival, String departure, String currentTime) {
//        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String nearestSourceTime = "";
//
//        try {
//            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
//                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ? AND " + COLUMN_SOURCE_TIME + " > ? ORDER BY " + COLUMN_SOURCE_TIME + " ASC LIMIT 1",
//                    new String[]{departure, arrival, currentTime});
//            if (cursor.moveToFirst()) {
//                nearestSourceTime = "TABLE_NAME: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
//                        "ROUTE: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
//                        "SOURCE: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)) + "\n" +
//                        "SOURCE TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_TIME)) + "\n" +
//                        "DESTINATION: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)) + "\n" +
//                        "DESTINATION TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION_TIME));
//            }
//            cursor.close();
//        } catch (SQLiteException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return nearestSourceTime;
//    }


    private static class MyDatabaseHelper extends SQLiteOpenHelper {

        public MyDatabaseHelper(HomeActivity context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ROUTE + " TEXT, " +
                    COLUMN_SOURCE + " TEXT, " +
                    COLUMN_SOURCE_TIME + " TEXT, " +
                    COLUMN_DESTINATION + " TEXT, " +
                    COLUMN_DESTINATION_TIME + " TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database upgrades here
        }
    }
}
