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
import java.util.Comparator;
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

            insertData(db,	"201B"	,	"Vellore"	,	"04:00:00"	,	"Cheyyar"	,	"5:40:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"04:15:00"	,	"ATHIYUR"	,	"4:45:00"	);
            insertData(db,	"T F"	,	"VELLORE"	,	"04:15:00"	,	"KATPADI"	,	"4:40:00"	);
            insertData(db,	"T10AB"	,	"VELLORE"	,	"04:20:00"	,	"ARCOT"	,	"5:40:00"	);
            insertData(db,	"T N"	,	"VELLORE"	,	"04:25:00"	,	"BAGAYAM"	,	"4:45:00"	);
            insertData(db,	"T M"	,	"VELLORE"	,	"04:30:00"	,	"BAGAYAM"	,	"4:50:00"	);
            insertData(db,	"T G"	,	"VELLORE"	,	"04:30:00"	,	"KATPADI"	,	"4:51:00"	);
            insertData(db,	"T P"	,	"VELLORE"	,	"04:30:00"	,	"KATPADI"	,	"5:10:00"	);
            insertData(db,	"T 1GB"	,	"VELLORE"	,	"04:30:00"	,	"KATPADI"	,	"4:50:00"	);
            insertData(db,	"T20 R"	,	"VELLORE"	,	"04:30:00"	,	"PONNAIPUDUR"	,	"6:00:00"	);
            insertData(db,	"T 7TB"	,	"VELLORE"	,	"04:30:00"	,	"VILAPAKKAM"	,	"5:40:00"	);
            insertData(db,	"T J"	,	"VELLORE"	,	"04:35:00"	,	"ULLIPUDUR"	,	"5:10:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"04:40:00"	,	"ANAICUT"	,	"5:30:00"	);
            insertData(db,	"T A"	,	"VELLORE"	,	"04:40:00"	,	"BAGAYAM"	,	"5:00:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"04:40:00"	,	"BAGAYAM"	,	"5:25:00"	);
            insertData(db,	"T B"	,	"VELLORE"	,	"04:45:00"	,	"BAGAYAM"	,	"5:05:00"	);
            insertData(db,	"T 1GA"	,	"VELLORE"	,	"04:45:00"	,	"BAGAYAM"	,	"5:05:00"	);
            insertData(db,	"T O"	,	"VELLORE"	,	"04:48:00"	,	"KATPADI"	,	"5:06:00"	);
            insertData(db,	"T C"	,	"VELLORE"	,	"04:50:00"	,	"BAGAYAM"	,	"5:10:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"04:50:00"	,	"G.R.PALAYAM"	,	"5:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"04:50:00"	,	"G.R.PALAYAM"	,	"5:20:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"04:50:00"	,	"REDDIYUR"	,	"5:50:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"04:54:00"	,	"BAGAYAM"	,	"5:40:00"	);
            insertData(db,	"T D"	,	"VELLORE"	,	"04:55:00"	,	"BAGAYAM"	,	"5:15:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"05:00:00"	,	"ARCOT"	,	"5:50:00"	);
            insertData(db,	"T E"	,	"VELLORE"	,	"05:00:00"	,	"BAGAYAM"	,	"5:20:00"	);
            insertData(db,	"T 7BB"	,	"VELLORE"	,	"05:00:00"	,	"CHETTITHANGAL"	,	"6:10:00"	);
            insertData(db,	"T L"	,	"VELLORE"	,	"05:00:00"	,	"KATPADI"	,	"5:25:00"	);
            insertData(db,	"T 2 CU"	,	"VELLORE"	,	"05:00:00"	,	"LATHERI GATE"	,	"5:40:00"	);
            insertData(db,	"103 A"	,	"VELLORE"	,	"05:00:00"	,	"ODUGATHUR"	,	"6:35:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"05:00:00"	,	"SENDRAYANKOTTAI"	,	"6:10:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"05:00:00"	,	"ADUKAMPARAI G.H."	,	"5:50:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"05:05:00"	,	"AMIRTHI"	,	"6:10:00"	);
            insertData(db,	"T10CA"	,	"VELLORE"	,	"05:05:00"	,	"KATPADI"	,	"5:20:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"05:05:00"	,	"MELMANKUPPAM"	,	"6:20:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"05:05:00"	,	"VALLIMALAI"	,	"6:10:00"	);
            insertData(db,	"T I"	,	"VELLORE"	,	"05:08:00"	,	"KATPADI"	,	"5:33:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"05:08:00"	,	"BAGAYAM"	,	"5:53:00"	);
            insertData(db,	"T13CA"	,	"VELLORE"	,	"05:10:00"	,	"KILKOTHUR"	,	"6:35:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"05:10:00"	,	"MULLENDRAM"	,	"6:25:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"05:10:00"	,	"PALAYATHONDANTHULASI"	,	"5:55:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"05:13:00"	,	"BAGAYAM"	,	"5:58:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"05:15:00"	,	"KARUNGALI"	,	"6:15:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"05:15:00"	,	"PASUMATHUR"	,	"6:05:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"05:15:00"	,	"PERIYABODINATHAM"	,	"6:25:00"	);
            insertData(db,	"T 8 E"	,	"VELLORE"	,	"05:15:00"	,	"PUTHUR"	,	"5:50:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"05:20:00"	,	"ADUKAMPARAI"	,	"5:50:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"05:20:00"	,	"ARCOT"	,	"6:10:00"	);
            insertData(db,	"T H"	,	"VELLORE"	,	"05:20:00"	,	"BAGAYAM"	,	"5:40:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"05:20:00"	,	"ERIPUDUR"	,	"6:30:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"05:20:00"	,	"REDDIPALAYAM"	,	"6:20:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"05:20:00"	,	"SENDRAMPALLI"	,	"6:20:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"05:25:00"	,	"KILNAGAR"	,	"6:40:00"	);
            insertData(db,	"T 2BA"	,	"VELLORE"	,	"05:25:00"	,	"MELAKUPPAM"	,	"6:15:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"05:25:00"	,	"LALAPET"	,	"6:30:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"05:27:00"	,	"PERIACHITTERI"	,	"6:07:00"	);
            insertData(db,	"T Q"	,	"VELLORE"	,	"05:30:00"	,	"KATPADI"	,	"5:55:00"	);
            insertData(db,	"T10DA"	,	"VELLORE"	,	"05:30:00"	,	"KATPADI"	,	"5:40:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"05:30:00"	,	"MAHADEVAMALAI"	,	"6:45:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"05:33:00"	,	"BAGAYAM"	,	"6:18:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"05:34:00"	,	"BAGAYAM"	,	"6:17:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"05:35:00"	,	"ARCOT"	,	"6:35:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"05:35:00"	,	"JAMALPURAM"	,	"6:10:00"	);
            insertData(db,	"T10 A"	,	"VELLORE"	,	"05:35:00"	,	"MUTHUKADAI"	,	"6:40:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"05:40:00"	,	"ANANTHAPURAM H.S"	,	"6:45:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"05:40:00"	,	"PUDUPALAYAM"	,	"6:55:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"05:40:00"	,	"BAGAYAM"	,	"6:25:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"05:45:00"	,	"ADUKAMPARAI G.H."	,	"6:15:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"05:45:00"	,	"AGARAMCHERI"	,	"6:40:00"	);
            insertData(db,	"T 7VB"	,	"VELLORE"	,	"05:45:00"	,	"ARCOT"	,	"6:30:00"	);
            insertData(db,	"T20AB"	,	"VELLORE"	,	"05:45:00"	,	"AVANARANGAPALLI"	,	"7:35:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"05:45:00"	,	"KASIMANAGAR"	,	"6:50:00"	);
            insertData(db,	"T 3 A"	,	"VELLORE"	,	"05:45:00"	,	"MELSENGANATHAM"	,	"6:15:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"05:45:00"	,	"BAGAYAM"	,	"6:15:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"05:45:00"	,	"BAGAYAM"	,	"6:30:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"05:45:00"	,	"WALAJA"	,	"6:55:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"05:50:00"	,	"NANJUKONDAPURAM"	,	"6:50:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"05:50:00"	,	"PALLATHUR"	,	"6:50:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"05:50:00"	,	"BAGAYAM"	,	"6:35:00"	);
            insertData(db,	"A25A"	,	"Vellore"	,	"05:55:00"	,	"Arni"	,	"7:05:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"05:55:00"	,	"P.K.PURAM"	,	"7:05:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"05:55:00"	,	"SERKADU"	,	"6:55:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"05:55:00"	,	"VAZIYUR"	,	"7:05:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"05:55:00"	,	"BAGAYAM"	,	"6:40:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"05:55:00"	,	"GUDIYATHAM"	,	"7:25:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"05:55:00"	,	"KALPUDUR"	,	"6:00:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"06:00:00"	,	"BHEL"	,	"7:00:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"06:00:00"	,	"IRUMBULI"	,	"7:10:00"	);
            insertData(db,	"T13CB"	,	"VELLORE"	,	"06:00:00"	,	"KILKOTHUR"	,	"7:50:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"06:00:00"	,	"RAGUNATHAPURAM"	,	"7:00:00"	);
            insertData(db,	"T20MA"	,	"VELLORE"	,	"06:00:00"	,	"THENGAL"	,	"7:25:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"06:00:00"	,	"BAGAYAM"	,	"6:45:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"06:07:00"	,	"BAGAYAM"	,	"6:55:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"06:10:00"	,	"AMMAVARPALLI"	,	"7:20:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"06:10:00"	,	"ANAICUT"	,	"7:05:00"	);
            insertData(db,	"T 2AA"	,	"VELLORE"	,	"06:10:00"	,	"BAGAYAM"	,	"6:30:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"06:10:00"	,	"DURAIMOOLAI"	,	"7:30:00"	);
            insertData(db,	"76 A"	,	"VELLORE"	,	"06:10:00"	,	"GUDIYATHAM"	,	"7:10:00"	);
            insertData(db,	"76 A"	,	"VELLORE"	,	"06:10:00"	,	"GUDIYATHAM"	,	"7:10:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"06:10:00"	,	"KUPPIREDDITHANGAL"	,	"7:00:00"	);
            insertData(db,	"104 B"	,	"VELLORE"	,	"06:10:00"	,	"ODUGATHUR"	,	"7:30:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"06:10:00"	,	"PARADARAMI"	,	"7:20:00"	);
            insertData(db,	"T10 M"	,	"KATPADI"	,	"06:10:00"	,	"ARCOT"	,	"7:10:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"06:10:00"	,	"BAGAYAM"	,	"6:50:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"06:15:00"	,	"KALANGAMEDU"	,	"7:00:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"06:15:00"	,	"OTHIYATHUR"	,	"7:25:00"	);
            insertData(db,	"225A"	,	"Vellore"	,	"06:15:00"	,	"Vazhiyur"	,	"6:55:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"06:17:00"	,	"PERIYAPUDUR"	,	"6:27:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"06:18:00"	,	"BAGAYAM"	,	"7:03:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"06:20:00"	,	"AMIRTHI"	,	"7:20:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"06:20:00"	,	"KOLLAMANGALAM"	,	"7:20:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"06:20:00"	,	"MUTHUKADAI"	,	"7:25:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"06:20:00"	,	"VILAPAKKAM"	,	"7:40:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"06:25:00"	,	"ADUKAMPARAI"	,	"6:55:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"06:25:00"	,	"DEVARISHIKUPPAM"	,	"7:35:00"	);
            insertData(db,	"G 9 A"	,	"KATPADI"	,	"06:25:00"	,	"GUDIYATHAM"	,	"7:30:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"06:28:00"	,	"BAGAYAM"	,	"7:00:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"06:30:00"	,	"ARCOT"	,	"7:20:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"06:30:00"	,	"PASUMATHUR"	,	"7:20:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"06:30:00"	,	"PUNGANOOR"	,	"7:10:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"06:30:00"	,	"SINGIRIKOIL"	,	"7:30:00"	);
            insertData(db,	"225J"	,	"Vellore"	,	"06:30:00"	,	"Thiruvannamalai"	,	"8:30:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"06:34:00"	,	"BAGAYAM"	,	"7:19:00"	);
            insertData(db,	"T 2 CU"	,	"VELLORE"	,	"06:35:00"	,	"AMMAPALAYAM"	,	"7:35:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"06:35:00"	,	"KAVANUR"	,	"7:55:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"06:38:00"	,	"BAGAYAM"	,	"7:38:00"	);
            insertData(db,	"G23 A"	,	"VELLORE"	,	"06:40:00"	,	"PALLATHUR"	,	"7:45:00"	);
            insertData(db,	"T10FA"	,	"VELLORE"	,	"06:40:00"	,	"RANIPET"	,	"8:15:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"06:40:00"	,	"VARADALAMPATTU"	,	"7:40:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"06:45:00"	,	"ANAICUT"	,	"7:30:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"06:45:00"	,	"ARCOTTANKUDISAI"	,	"8:55:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"06:45:00"	,	"KILKOTHUR"	,	"8:20:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"06:45:00"	,	"VAZHAVANKUNDRAM"	,	"7:55:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"06:50:00"	,	"AMIRTHI"	,	"7:50:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"06:50:00"	,	"ATHIYUR"	,	"7:35:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"06:50:00"	,	"KAVANUR R.S"	,	"7:55:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"06:53:00"	,	"BAGAYAM"	,	"7:38:00"	);
            insertData(db,	"T10AA"	,	"VELLORE"	,	"06:55:00"	,	"BHEL"	,	"7:35:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"06:55:00"	,	"MARUHTHAMPAKKAM"	,	"8:10:00"	);
            insertData(db,	"225B"	,	"Vellore"	,	"06:55:00"	,	"Thiruvannamalai"	,	"8:55:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"07:00:00"	,	"A.K.PADAVEDU"	,	"8:20:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"07:00:00"	,	"CHINNAPUTHUR"	,	"8:05:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"07:00:00"	,	"NANJUKONDAPURAM"	,	"8:00:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"07:00:00"	,	"PALAYATHONDANTHULASI"	,	"7:45:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"07:00:00"	,	"RANIPET"	,	"8:05:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"07:00:00"	,	"SENDRAMPALLI"	,	"8:00:00"	);
            insertData(db,	"225C"	,	"Vellore"	,	"07:00:00"	,	"Thiruvannamalai"	,	"9:00:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"07:00:00"	,	"MOOJURPATTU"	,	"8:00:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"07:00:00"	,	"PUTHUR"	,	"8:00:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"07:02:00"	,	"BAGAYAM"	,	"7:50:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"07:04:00"	,	"BAGAYAM"	,	"7:49:00"	);
            insertData(db,	"T P"	,	"VELLORE"	,	"07:05:00"	,	"AUTHUKADAI"	,	"8:05:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"07:05:00"	,	"MUTHUKADAI"	,	"8:00:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"07:07:00"	,	"VILAPAKKAM"	,	"8:30:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"07:07:00"	,	"PERIACHITTERI"	,	"7:47:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"07:10:00"	,	"KALLANKULAM"	,	"8:00:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"07:13:00"	,	"T.E.L."	,	"7:20:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"07:15:00"	,	"PASUMATHUR"	,	"8:10:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"07:15:00"	,	"BAGAYAM"	,	"8:00:00"	);
            insertData(db,	"T 2BA"	,	"VELLORE"	,	"07:20:00"	,	"MELAKUPPAM"	,	"8:10:00"	);
            insertData(db,	"T16FA"	,	"VELLORE"	,	"07:20:00"	,	"PARADARAMI"	,	"8:50:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"07:20:00"	,	"BAGAYAM"	,	"8:05:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"07:25:00"	,	"BAGAYAM"	,	"8:10:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"07:30:00"	,	"66 PUTHUR"	,	"8:10:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"07:30:00"	,	"BAGAYAM"	,	"8:15:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"07:35:00"	,	"AMIRTHI"	,	"8:45:00"	);
            insertData(db,	"T 7VA"	,	"VELLORE"	,	"07:35:00"	,	"ARCOT"	,	"8:25:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"07:35:00"	,	"GURUVARAJAPALAYAM"	,	"8:50:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"07:35:00"	,	"MELPADICHATRAM"	,	"8:55:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"07:35:00"	,	"SENDRAYANKOTTAI"	,	"8:40:00"	);
            insertData(db,	"T20 R"	,	"KATPADI"	,	"07:35:00"	,	"BAGAYAM"	,	"8:15:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"07:35:00"	,	"BAGAYAM"	,	"8:20:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"07:38:00"	,	"BAGAYAM"	,	"8:23:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"07:40:00"	,	"RAGUNATHAPURAM"	,	"8:40:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"07:40:00"	,	"REDDIPALAYAM"	,	"8:40:00"	);
            insertData(db,	"225D"	,	"Vellore"	,	"07:40:00"	,	"Thiruvannamalai"	,	"9:40:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"07:40:00"	,	"VAZIYUR"	,	"8:45:00"	);
            insertData(db,	"T F"	,	"VELLORE"	,	"07:40:00"	,	"VENKATAPURAM"	,	"8:30:00"	);
            insertData(db,	"T10AB"	,	"KATPADI"	,	"07:40:00"	,	"ARCOT"	,	"8:55:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"07:45:00"	,	"ATHIYUR"	,	"8:30:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"07:45:00"	,	"MELMANKUPPAM"	,	"9:00:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"07:45:00"	,	"PERUMALKUPPAM"	,	"8:55:00"	);
            insertData(db,	"34B"	,	"UPPUVELLORE"	,	"07:45:00"	,	"TINDIVANAM"	,	"8:45:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"07:45:00"	,	"VALAYAKARANPATTI"	,	"8:55:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"07:50:00"	,	"ERIPUDUR"	,	"8:55:00"	);
            insertData(db,	"225R"	,	"Vellore"	,	"07:55:00"	,	"Thiruvannamalai"	,	"9:55:00"	);
            insertData(db,	"T 7TB"	,	"VELLORE"	,	"08:00:00"	,	"VILAPAKKAM"	,	"9:10:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"08:00:00"	,	"T.E.L."	,	"8:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"08:05:00"	,	"KALANGAMEDU"	,	"8:50:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"08:05:00"	,	"AUXILIM COLLEGE"	,	"8:15:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"08:05:00"	,	"LALAPET"	,	"9:15:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"08:07:00"	,	"VARADARAJAPURAM"	,	"8:25:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"08:08:00"	,	"BAGAYAM"	,	"8:53:00"	);
            insertData(db,	"T O"	,	"VELLORE"	,	"08:10:00"	,	"AUTHUKADAI"	,	"9:25:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"08:10:00"	,	"CHETTIYANTHAL"	,	"9:30:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"08:10:00"	,	"PALLATHUR"	,	"9:15:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"08:10:00"	,	"SERKADU"	,	"9:10:00"	);
            insertData(db,	"T13CA"	,	"VELLORE"	,	"08:15:00"	,	"KILKOTHUR"	,	"9:45:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"08:15:00"	,	"NANJUKONDAPURAM"	,	"9:25:00"	);
            insertData(db,	"407 A"	,	"VELLORE"	,	"08:15:00"	,	"SHOLINGHUR"	,	"10:15:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"08:15:00"	,	"SRIPURAM"	,	"8:55:00"	);
            insertData(db,	"400 A"	,	"VELLORE"	,	"08:15:00"	,	"THIRUTHANI"	,	"10:45:00"	);
            insertData(db,	"225E"	,	"Vellore"	,	"08:15:00"	,	"Thiruvannamalai"	,	"10:15:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"08:20:00"	,	"ARJUNAPURAM"	,	"9:44:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"08:20:00"	,	"RATHINAGIRI"	,	"8:50:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"08:20:00"	,	"SINGAREDDIYUR"	,	"8:45:00"	);
            insertData(db,	"201A"	,	"Vellore"	,	"08:25:00"	,	"Cheyyar"	,	"10:25:00"	);
            insertData(db,	"B51 A"	,	"VELLORE"	,	"08:25:00"	,	"VIRUDHUNAGAR"	,	"8:55:00"	);
            insertData(db,	"T 7JB"	,	"VELLORE"	,	"08:27:00"	,	"RANIPET"	,	"9:37:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"08:30:00"	,	"ANAICUT TALUK OFFICE"	,	"9:25:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"08:30:00"	,	"DEVARISHIKUPPAM"	,	"9:50:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"08:30:00"	,	"IRUMBULI"	,	"9:30:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"08:30:00"	,	"KUPPIREDDITHANGAL"	,	"9:30:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"08:30:00"	,	"MULLENDRAM"	,	"10:20:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"08:30:00"	,	"PASUMATHUR"	,	"9:20:00"	);
            insertData(db,	"777VA"	,	"Vellore"	,	"08:30:00"	,	"Polur"	,	"12:30:00"	);
            insertData(db,	"225F"	,	"Vellore"	,	"08:30:00"	,	"Thiruvannamalai"	,	"10:30:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"08:30:00"	,	"VANDRANTHANGAL"	,	"8:40:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"08:33:00"	,	"T.E.L."	,	"8:51:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"08:34:00"	,	"BAGAYAM"	,	"9:17:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"08:35:00"	,	"CHINNAPUTHUR"	,	"9:45:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"08:35:00"	,	"MAHADEVAMALAI"	,	"9:55:00"	);
            insertData(db,	"225W"	,	"Vellore"	,	"08:35:00"	,	"Thiruvannamalai"	,	"10:35:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"08:37:00"	,	"ARCOT"	,	"9:30:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"08:38:00"	,	"PERIYAPUDUR"	,	"8:46:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"08:40:00"	,	"ATHIYUR"	,	"9:20:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"08:40:00"	,	"MOTHAKKAL"	,	"9:40:00"	);
            insertData(db,	"103 A"	,	"VELLORE"	,	"08:45:00"	,	"ODUGATHUR"	,	"10:10:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"08:45:00"	,	"SEDUVALAI"	,	"9:15:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"08:45:00"	,	"THIPPASAMUDRAM"	,	"9:55:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"08:45:00"	,	"THONDANTHULASI"	,	"9:25:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"08:45:00"	,	"BAGAYAM"	,	"9:30:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"08:48:00"	,	"BAGAYAM"	,	"9:35:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"08:50:00"	,	"AMMAVARPALLI"	,	"10:00:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"08:50:00"	,	"MUTHUKADAI"	,	"10:00:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"08:50:00"	,	"BAGAYAM"	,	"9:35:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"08:50:00"	,	"PERIACHITTERI"	,	"9:30:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"08:52:00"	,	"BAGAYAM"	,	"9:47:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"08:53:00"	,	"BAGAYAM"	,	"9:35:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"08:54:00"	,	"BAGAYAM"	,	"9:54:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"08:55:00"	,	"BAGAYAM"	,	"9:40:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"09:00:00"	,	"KALPUDUR"	,	"9:30:00"	);
            insertData(db,	"225G"	,	"Vellore"	,	"09:00:00"	,	"Thiruvannamalai"	,	"11:00:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"09:00:00"	,	"VARADALAMPATTU"	,	"10:00:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"09:00:00"	,	"BAGAYAM"	,	"9:45:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"09:00:00"	,	"GUDIYATHAM"	,	"10:15:00"	);
            insertData(db,	"T 2 CU"	,	"VELLORE"	,	"09:05:00"	,	"LATHERI GATE"	,	"9:45:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"09:05:00"	,	"BAGAYAM"	,	"9:50:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"09:07:00"	,	"LAPPAIKRISHNAPURAM"	,	"9:55:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"09:08:00"	,	"BAGAYAM"	,	"9:53:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"09:10:00"	,	"AMIRTHI"	,	"10:20:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"09:10:00"	,	"DURAIMOOLAI"	,	"10:20:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"09:10:00"	,	"MOOLAVALASAI"	,	"10:15:00"	);
            insertData(db,	"225H"	,	"Vellore"	,	"09:10:00"	,	"Thiruvannamalai"	,	"11:10:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"09:10:00"	,	"WALAJA"	,	"10:20:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"09:15:00"	,	"LABAIKRISHNAPURAM"	,	"10:00:00"	);
            insertData(db,	"407 B"	,	"VELLORE"	,	"09:15:00"	,	"THIRUTHANI"	,	"11:50:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"09:15:00"	,	"VILAPAKKAM"	,	"10:20:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"09:15:00"	,	"66 PUTHUR"	,	"9:45:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"09:17:00"	,	"BAGAYAM"	,	"10:02:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"09:20:00"	,	"SINGIRIKOIL"	,	"10:20:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"09:20:00"	,	"SRIPURAM"	,	"10:10:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"09:25:00"	,	"ARCOT"	,	"10:20:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"09:25:00"	,	"KND DEPOT"	,	"9:30:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"09:25:00"	,	"PASUMATHUR"	,	"10:25:00"	);
            insertData(db,	"T20MA"	,	"VELLORE"	,	"09:25:00"	,	"PONNAI"	,	"10:30:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"09:25:00"	,	"R.KRISHNAPURAM"	,	"10:25:00"	);
            insertData(db,	"225I"	,	"Vellore"	,	"09:25:00"	,	"Thiruvannamalai"	,	"11:25:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"09:25:00"	,	"ADUKAMPARAI G.H."	,	"10:25:00"	);
            insertData(db,	"T 2AA"	,	"VELLORE"	,	"09:30:00"	,	"BAGAYAM"	,	"9:55:00"	);
            insertData(db,	"104 B"	,	"VELLORE"	,	"09:30:00"	,	"ODUGATHUR"	,	"10:55:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"09:33:00"	,	"RANIPET"	,	"10:45:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"09:35:00"	,	"ADUKAMPARAI"	,	"10:05:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"09:35:00"	,	"ARCOT"	,	"10:25:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"09:35:00"	,	"ARCOT"	,	"10:30:00"	);
            insertData(db,	"225K"	,	"Vellore"	,	"09:35:00"	,	"Thiruvannamalai"	,	"11:35:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"09:40:00"	,	"ARCOT"	,	"10:35:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"09:40:00"	,	"ARCOT"	,	"10:40:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"09:40:00"	,	"OTHIYATHUR"	,	"10:40:00"	);
            insertData(db,	"T P"	,	"VELLORE"	,	"09:42:00"	,	"ADUKAMPARAI G.H."	,	"10:20:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"09:45:00"	,	"AMIRTHI"	,	"11:00:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"09:45:00"	,	"ARCOTTANKUDISAI"	,	"10:25:00"	);
            insertData(db,	"T F"	,	"VELLORE"	,	"09:45:00"	,	"BAGAYAM"	,	"10:15:00"	);
            insertData(db,	"201B"	,	"Vellore"	,	"09:45:00"	,	"Cheyyar"	,	"11:45:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"09:45:00"	,	"G.R.PALAYAM"	,	"10:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"09:45:00"	,	"G.R.PALAYAM"	,	"10:20:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"09:45:00"	,	"MOOLAKANKUPPAM"	,	"11:05:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"09:45:00"	,	"PUDUPALAYAM"	,	"10:50:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"09:53:00"	,	"BAGAYAM"	,	"10:38:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"10:00:00"	,	"ANAICUT"	,	"10:50:00"	);
            insertData(db,	"T40 B"	,	"VELLORE"	,	"10:00:00"	,	"ARCOT"	,	"11:00:00"	);
            insertData(db,	"T13CB"	,	"VELLORE"	,	"10:00:00"	,	"KILKOTHUR"	,	"11:50:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"10:00:00"	,	"MUTHUKADAI"	,	"11:10:00"	);
            insertData(db,	"T20AB"	,	"VELLORE"	,	"10:00:00"	,	"PONNAI"	,	"11:15:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"10:00:00"	,	"VAZIYUR"	,	"11:05:00"	);
            insertData(db,	"G 9 D"	,	"KATPADI"	,	"10:00:00"	,	"GUDIYATHAM"	,	"11:00:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"10:02:00"	,	"BAGAYAM"	,	"10:42:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"10:05:00"	,	"IRUMBULI"	,	"11:25:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"10:05:00"	,	"REDDIYUR"	,	"11:05:00"	);
            insertData(db,	"7C"	,	"UPPUVELLORE"	,	"10:05:00"	,	"TINDIVANAM"	,	"11:05:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"10:05:00"	,	"MUTHUKADAI"	,	"10:55:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"10:08:00"	,	"PERIACHITTERI"	,	"10:48:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"10:10:00"	,	"JAMALPURAM"	,	"10:45:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"10:15:00"	,	"SENJI"	,	"11:20:00"	);
            insertData(db,	"225V"	,	"Vellore"	,	"10:15:00"	,	"Thiruvannamalai"	,	"12:15:00"	);
            insertData(db,	"T10FA"	,	"VELLORE"	,	"10:15:00"	,	"VALLIMALAI"	,	"11:25:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"10:15:00"	,	"BAGAYAM"	,	"11:00:00"	);
            insertData(db,	"G 9 C"	,	"KATPADI"	,	"10:15:00"	,	"GUDIYATHAM"	,	"11:30:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"10:16:00"	,	"BAGAYAM"	,	"10:47:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"10:20:00"	,	"RAGUNATHAPURAM"	,	"11:20:00"	);
            insertData(db,	"T10AB"	,	"KATPADI"	,	"10:20:00"	,	"ARCOT"	,	"11:30:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"10:20:00"	,	"BAGAYAM"	,	"11:05:00"	);
            insertData(db,	"T 7DA"	,	"VELLORE"	,	"10:25:00"	,	"ARCOT"	,	"11:20:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"10:25:00"	,	"KARUNGALI"	,	"11:25:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"10:25:00"	,	"KONAVATTAM"	,	"10:30:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"10:25:00"	,	"BAGAYAM"	,	"11:10:00"	);
            insertData(db,	"T 7AF"	,	"VELLORE"	,	"10:27:00"	,	"WALAJA"	,	"11:25:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"10:30:00"	,	"AMIRTHI"	,	"11:40:00"	);
            insertData(db,	"T10 A"	,	"VELLORE"	,	"10:30:00"	,	"MUTHUKADAI"	,	"11:35:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"10:30:00"	,	"SERKADU"	,	"11:20:00"	);
            insertData(db,	"9E"	,	"VELLOREGRAMAM"	,	"10:30:00"	,	"ULUNTHURPET"	,	"11:20:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"10:30:00"	,	"BAGAYAM"	,	"11:15:00"	);
            insertData(db,	"T 7VA"	,	"VELLORE"	,	"10:35:00"	,	"ARCOT"	,	"11:30:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"10:35:00"	,	"KANIYAMBADI"	,	"11:15:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"10:35:00"	,	"BAGAYAM"	,	"11:20:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"10:37:00"	,	"KND DEPOT"	,	"11:30:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"10:37:00"	,	"BAGAYAM"	,	"11:22:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"10:38:00"	,	"BAGAYAM"	,	"11:23:00"	);
            insertData(db,	"T 2 CU"	,	"VELLORE"	,	"10:40:00"	,	"ADUKAMPARAI G.H."	,	"11:00:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"10:40:00"	,	"ANAICUT"	,	"11:30:00"	);
            insertData(db,	"83 A"	,	"VELLORE"	,	"10:40:00"	,	"PALAMANER"	,	"13:30:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"10:40:00"	,	"PALLATHUR"	,	"11:40:00"	);
            insertData(db,	"225N"	,	"Vellore"	,	"10:40:00"	,	"Thiruvannamalai"	,	"12:40:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"10:40:00"	,	"WALAJA"	,	"11:55:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"10:43:00"	,	"ARCOT"	,	"11:38:00"	);
            insertData(db,	"T43 A"	,	"VELLORE"	,	"10:43:00"	,	"ARCOT"	,	"11:45:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"10:45:00"	,	"ARCOT"	,	"11:40:00"	);
            insertData(db,	"T O"	,	"VELLORE"	,	"10:45:00"	,	"BAGAYAM"	,	"11:05:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"10:45:00"	,	"KND DEPOT"	,	"10:55:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"10:47:00"	,	"BAGAYAM"	,	"11:35:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"10:50:00"	,	"ANAICUT"	,	"11:40:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"10:50:00"	,	"NANJUKONDAPURAM"	,	"12:00:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"10:50:00"	,	"ODUGATHUR"	,	"12:10:00"	);
            insertData(db,	"T20 R"	,	"VELLORE"	,	"10:50:00"	,	"PONNAIPUDUR"	,	"12:10:00"	);
            insertData(db,	"225O"	,	"Vellore"	,	"10:50:00"	,	"Thiruvannamalai"	,	"12:50:00"	);
            insertData(db,	"T 7TB"	,	"VELLORE"	,	"10:50:00"	,	"VILAPAKKAM"	,	"12:00:00"	);
            insertData(db,	"T 7AD"	,	"VELLORE"	,	"10:50:00"	,	"WALAJA"	,	"12:00:00"	);
            insertData(db,	"G 9MA"	,	"KATPADI"	,	"10:50:00"	,	"GUDIYATHAM"	,	"11:50:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"10:59:00"	,	"BAGAYAM"	,	"11:59:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"11:00:00"	,	"ADUKKAMPARAI"	,	"11:30:00"	);
            insertData(db,	"T7AAA"	,	"VELLORE"	,	"11:00:00"	,	"ARCOT"	,	"11:50:00"	);
            insertData(db,	"201C"	,	"Vellore"	,	"11:00:00"	,	"Cheyyar"	,	"12:45:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"11:00:00"	,	"LAPPAIKRISHNAPURAM"	,	"11:50:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"11:00:00"	,	"MOTHAKKAL"	,	"12:00:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"11:00:00"	,	"WALAJA"	,	"12:20:00"	);
            insertData(db,	"G 9 A"	,	"KATPADI"	,	"11:00:00"	,	"GUDIYATHAM"	,	"12:15:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"11:02:00"	,	"BAGAYAM"	,	"11:47:00"	);
            insertData(db,	"T 7BB"	,	"VELLORE"	,	"11:05:00"	,	"CHETTITHANGAL"	,	"12:25:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"11:05:00"	,	"KND DEPOT"	,	"11:10:00"	);
            insertData(db,	"225P"	,	"Vellore"	,	"11:05:00"	,	"Thiruvannamalai"	,	"13:05:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"11:08:00"	,	"BAGAYAM"	,	"11:53:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"11:10:00"	,	"CHOLAVARAM"	,	"11:50:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"11:10:00"	,	"KALANGAMEDU"	,	"11:50:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"11:15:00"	,	"ARCOTTANKUDISAI"	,	"12:05:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"11:15:00"	,	"MAHADEVAMALAI"	,	"12:30:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"11:15:00"	,	"THIPPASAMUDRAM"	,	"12:20:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"11:20:00"	,	"ARCOT"	,	"12:10:00"	);
            insertData(db,	"T 7SA"	,	"VELLORE"	,	"11:20:00"	,	"ARCOT"	,	"12:10:00"	);
            insertData(db,	"T 7JB"	,	"VELLORE"	,	"11:20:00"	,	"RANIPET"	,	"12:20:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"11:20:00"	,	"SENDRAMPALLI"	,	"12:20:00"	);
            insertData(db,	"225X"	,	"Vellore"	,	"11:25:00"	,	"Thiruvannamalai"	,	"13:25:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"11:25:00"	,	"LALAPET"	,	"12:30:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"11:28:00"	,	"BAGAYAM"	,	"12:13:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"11:30:00"	,	"ADUKAMPARAI G.H."	,	"12:00:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"11:30:00"	,	"ANANTHAPURAM H.S"	,	"12:50:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"11:30:00"	,	"DEVARISHIKUPPAM"	,	"12:50:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"11:30:00"	,	"KND DEPOT"	,	"11:35:00"	);
            insertData(db,	"T16FA"	,	"VELLORE"	,	"11:30:00"	,	"PARADARAMI"	,	"13:00:00"	);
            insertData(db,	"225J"	,	"Vellore"	,	"11:30:00"	,	"Thiruvannamalai"	,	"13:30:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"11:33:00"	,	"BAGAYAM"	,	"12:18:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"11:33:00"	,	"PERIACHITTERI"	,	"12:13:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"11:34:00"	,	"BAGAYAM"	,	"12:19:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"11:35:00"	,	"ARCOT"	,	"12:30:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"11:35:00"	,	"ERIPUDUR"	,	"12:35:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"11:35:00"	,	"KND DEPOT"	,	"11:45:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"11:35:00"	,	"MUTHUKADAI"	,	"12:45:00"	);
            insertData(db,	"G 9AA"	,	"KATPADI"	,	"11:37:00"	,	"GUDIYATHAM"	,	"12:45:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"11:40:00"	,	"ARCOT"	,	"12:30:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"11:40:00"	,	"ADUKAMPARAI G.H."	,	"12:40:00"	);
            insertData(db,	"225S"	,	"Vellore"	,	"11:42:00"	,	"Thiruvannamalai"	,	"13:42:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"11:45:00"	,	"ARCOT"	,	"12:35:00"	);
            insertData(db,	"T10AA"	,	"VELLORE"	,	"11:45:00"	,	"MUTHUKADAI"	,	"12:45:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"11:45:00"	,	"PERIYABODINATHAM"	,	"12:55:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"11:45:00"	,	"R.KRISHNAPURAM"	,	"12:45:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"11:45:00"	,	"REDDIPALAYAM"	,	"12:45:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"11:45:00"	,	"BAGAYAM"	,	"12:30:00"	);
            insertData(db,	"T 7 C"	,	"VELLORE"	,	"11:50:00"	,	"ARCOT"	,	"12:40:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"11:50:00"	,	"MELMANKUPPAM"	,	"13:00:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"11:50:00"	,	"BAGAYAM"	,	"12:35:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"11:50:00"	,	"WALAJA"	,	"13:00:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"11:55:00"	,	"ARCOT"	,	"12:55:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"11:55:00"	,	"KUPPIREDDITHANGAL"	,	"13:05:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"11:55:00"	,	"BAGAYAM"	,	"12:40:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"12:00:00"	,	"CHINNAPUTHUR"	,	"13:15:00"	);
            insertData(db,	"407 A"	,	"VELLORE"	,	"12:00:00"	,	"THIRUTHANI"	,	"14:00:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"12:00:00"	,	"VILAPAKKAM"	,	"13:10:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"12:00:00"	,	"BAGAYAM"	,	"12:45:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"12:00:00"	,	"GUDIYATHAM"	,	"13:05:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"12:05:00"	,	"KND DEPOT"	,	"12:10:00"	);
            insertData(db,	"103 A"	,	"VELLORE"	,	"12:05:00"	,	"ODUGATHUR"	,	"13:40:00"	);
            insertData(db,	"225A"	,	"Vellore"	,	"12:05:00"	,	"Thiruvannamalai"	,	"14:05:00"	);
            insertData(db,	"T 2 CU"	,	"KATPADI"	,	"12:05:00"	,	"BAGAYAM"	,	"12:50:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"12:05:00"	,	"BAGAYAM"	,	"12:50:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"12:07:00"	,	"BAGAYAM"	,	"12:52:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"12:08:00"	,	"BAGAYAM"	,	"12:52:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"12:10:00"	,	"PASUMATHUR"	,	"13:00:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"12:10:00"	,	"VARGURPATTINAM"	,	"13:30:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"12:13:00"	,	"BAGAYAM"	,	"13:05:00"	);
            insertData(db,	"T 7VB"	,	"VELLORE"	,	"12:15:00"	,	"ARCOT"	,	"13:05:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"12:15:00"	,	"GURUVARAJAPALAYAM"	,	"13:25:00"	);
            insertData(db,	"T13CA"	,	"VELLORE"	,	"12:15:00"	,	"PINNATHURAI"	,	"14:00:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"12:15:00"	,	"VALAYAKARANPATTI"	,	"13:25:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"12:18:00"	,	"BAGAYAM"	,	"13:03:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"12:18:00"	,	"BAGAYAM"	,	"13:03:00"	);
            insertData(db,	"T40 B"	,	"VELLORE"	,	"12:20:00"	,	"ARCOT"	,	"13:20:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"12:20:00"	,	"KAVANUR R.S"	,	"13:25:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"12:20:00"	,	"KND DEPOT"	,	"12:25:00"	);
            insertData(db,	"T20MA"	,	"VELLORE"	,	"12:20:00"	,	"THENGAL"	,	"13:50:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"12:25:00"	,	"KND DEPOT"	,	"12:30:00"	);
            insertData(db,	"T 8 E"	,	"VELLORE"	,	"12:25:00"	,	"PUTHUR"	,	"13:00:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"12:25:00"	,	"RANIPET"	,	"13:35:00"	);
            insertData(db,	"225B"	,	"Vellore"	,	"12:25:00"	,	"Thiruvannamalai"	,	"14:25:00"	);
            insertData(db,	"7C"	,	"UPPUVELLORE"	,	"12:25:00"	,	"TINDIVANAM"	,	"13:25:00"	);
            insertData(db,	"G 9 D"	,	"KATPADI"	,	"12:25:00"	,	"GUDIYATHAM"	,	"13:30:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"12:27:00"	,	"BAGAYAM"	,	"13:12:00"	);
            insertData(db,	"T 7DA"	,	"VELLORE"	,	"12:30:00"	,	"ARCOT"	,	"13:20:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"12:30:00"	,	"KILNAGAR"	,	"14:00:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"12:30:00"	,	"THIRUVALLUVAR UNIV"	,	"13:30:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"12:31:00"	,	"BAGAYAM"	,	"13:17:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"12:35:00"	,	"PASUMATHUR"	,	"13:35:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"12:40:00"	,	"AMIRTHI"	,	"13:40:00"	);
            insertData(db,	"T 7BA"	,	"VELLORE"	,	"12:40:00"	,	"ARCOT"	,	"13:40:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"12:40:00"	,	"SENDRAYANKOTTAI"	,	"13:45:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"12:40:00"	,	"SIVANATHAPURAM"	,	"13:25:00"	);
            insertData(db,	"225C"	,	"Vellore"	,	"12:40:00"	,	"Thiruvannamalai"	,	"14:00:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"12:45:00"	,	"ADUKAMPARAI"	,	"13:15:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"12:45:00"	,	"ARCOT"	,	"13:30:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"12:45:00"	,	"DURAIMOOLAI"	,	"14:05:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"12:45:00"	,	"G.R.PALAYAM"	,	"13:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"12:45:00"	,	"G.R.PALAYAM"	,	"13:20:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"12:45:00"	,	"MUTHUKADAI"	,	"13:50:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"12:45:00"	,	"SINGIRIKOIL"	,	"14:00:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"12:50:00"	,	"THONDANTHULASI"	,	"13:30:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"12:55:00"	,	"KND DEPOT"	,	"13:00:00"	);
            insertData(db,	"104 B"	,	"VELLORE"	,	"12:55:00"	,	"ODUGATHUR"	,	"14:35:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"12:57:00"	,	"BAGAYAM"	,	"13:42:00"	);
            insertData(db,	"T 2BA"	,	"VELLORE"	,	"13:00:00"	,	"MELAKUPPAM"	,	"13:00:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"13:00:00"	,	"MOOLAKANKUPPAM"	,	"14:15:00"	);
            insertData(db,	"T20AB"	,	"VELLORE"	,	"13:00:00"	,	"PONNAI"	,	"14:15:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"13:00:00"	,	"REDDIPALAYAM"	,	"14:00:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"13:00:00"	,	"SENJIKRISHNAPURAM"	,	"14:00:00"	);
            insertData(db,	"225D"	,	"Vellore"	,	"13:00:00"	,	"Thiruvannamalai"	,	"15:00:00"	);
            insertData(db,	"G 9 C"	,	"KATPADI"	,	"13:00:00"	,	"GUDIYATHAM"	,	"14:10:00"	);
            insertData(db,	"T 2AA"	,	"KATPADI"	,	"13:03:00"	,	"BAGAYAM"	,	"13:48:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"13:03:00"	,	"BAGAYAM"	,	"14:03:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"13:04:00"	,	"BAGAYAM"	,	"13:49:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"13:05:00"	,	"AMMAVARPALLI"	,	"14:10:00"	);
            insertData(db,	"T10 A"	,	"VELLORE"	,	"13:05:00"	,	"MUTHUKADAI"	,	"14:10:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"13:05:00"	,	"MUTHUKADAI"	,	"13:55:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"13:08:00"	,	"PERIACHITTERI"	,	"13:48:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"13:10:00"	,	"ANAICUT"	,	"14:00:00"	);
            insertData(db,	"T43 B"	,	"VELLORE"	,	"13:10:00"	,	"ARCOT"	,	"14:00:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"13:10:00"	,	"ARCOTTANKUDISAI"	,	"13:50:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"13:10:00"	,	"PALLIKONDA"	,	"14:10:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"13:10:00"	,	"PUDUPALAYAM"	,	"14:25:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"13:15:00"	,	"PALLATHUR"	,	"14:15:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"13:15:00"	,	"BAGAYAM"	,	"14:00:00"	);
            insertData(db,	"G 9MA"	,	"KATPADI"	,	"13:15:00"	,	"GUDIYATHAM"	,	"14:15:00"	);
            insertData(db,	"T10AB"	,	"VELLORE"	,	"13:20:00"	,	"ARCOT"	,	"14:40:00"	);
            insertData(db,	"225R"	,	"Vellore"	,	"13:20:00"	,	"Thiruvannamalai"	,	"15:20:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"13:20:00"	,	"VARADALAMPATTU"	,	"14:20:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"13:20:00"	,	"VAZIYUR"	,	"14:20:00"	);
            insertData(db,	"T 7AF"	,	"VELLORE"	,	"13:20:00"	,	"WALAJA"	,	"14:20:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"13:20:00"	,	"BAGAYAM"	,	"14:05:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"13:22:00"	,	"WALAJA"	,	"14:35:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"13:25:00"	,	"BAGAYAM"	,	"14:10:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"13:25:00"	,	"WALAJA"	,	"14:35:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"13:30:00"	,	"AMIRTHI"	,	"14:45:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"13:30:00"	,	"LAPPAIKRISHNAPURAM"	,	"14:30:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"13:30:00"	,	"VARGURPATTINAM"	,	"14:40:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"13:30:00"	,	"BAGAYAM"	,	"14:15:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"13:35:00"	,	"ARCOT"	,	"14:35:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"13:35:00"	,	"KARUNGALI"	,	"15:00:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"13:35:00"	,	"OTHIYATHUR"	,	"14:45:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"13:35:00"	,	"RAGUNATHAPURAM"	,	"14:35:00"	);
            insertData(db,	"T 7JB"	,	"VELLORE"	,	"13:35:00"	,	"RANIPET"	,	"14:35:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"13:35:00"	,	"BAGAYAM"	,	"14:20:00"	);
            insertData(db,	"T15NA"	,	"KATPADI"	,	"13:35:00"	,	"KND DEPOT"	,	"14:00:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"13:37:00"	,	"BAGAYAM"	,	"14:22:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"13:40:00"	,	"ATHIYUR"	,	"14:10:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"13:40:00"	,	"KOLLAMANGALAM"	,	"14:40:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"13:40:00"	,	"SERKADU"	,	"14:40:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"13:40:00"	,	"WALAJA"	,	"15:00:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"13:43:00"	,	"BAGAYAM"	,	"14:28:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"13:45:00"	,	"ERIPUDUR"	,	"15:00:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"13:45:00"	,	"PARADARAMI"	,	"15:05:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"13:45:00"	,	"KALPUDUR"	,	"13:50:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"13:50:00"	,	"BHEL"	,	"14:50:00"	);
            insertData(db,	"400 A"	,	"VELLORE"	,	"13:50:00"	,	"THIRUTHANI"	,	"16:25:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"13:52:00"	,	"BAGAYAM"	,	"14:38:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"13:53:00"	,	"BAGAYAM"	,	"14:38:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"13:55:00"	,	"MAHADEVAMALAI"	,	"15:05:00"	);
            insertData(db,	"225E"	,	"Vellore"	,	"13:55:00"	,	"Thiruvannamalai"	,	"15:55:00"	);
            insertData(db,	"T10FA"	,	"VELLORE"	,	"13:55:00"	,	"VALLIMALAI"	,	"15:05:00"	);
            insertData(db,	"G25 A"	,	"KATPADI"	,	"13:55:00"	,	"GUDIYATHAM"	,	"14:55:00"	);
            insertData(db,	"T11 B"	,	"KATPADI"	,	"13:55:00"	,	"MUTHUKADAI"	,	"14:55:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"13:56:00"	,	"BAGAYAM"	,	"14:41:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"14:00:00"	,	"AMIRTHI"	,	"15:10:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"14:00:00"	,	"IRUMBULI"	,	"15:10:00"	);
            insertData(db,	"T13CB"	,	"VELLORE"	,	"14:00:00"	,	"KILKOTHUR"	,	"15:50:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"14:00:00"	,	"ODUGATHUR"	,	"15:40:00"	);
            insertData(db,	"T 7TB"	,	"VELLORE"	,	"14:00:00"	,	"VILAPAKKAM"	,	"15:20:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"14:00:00"	,	"ADUKAMPARAI G.H."	,	"15:00:00"	);
            insertData(db,	"T20 R"	,	"KATPADI"	,	"14:00:00"	,	"BAGAYAM"	,	"14:40:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"14:00:00"	,	"LALAPET"	,	"15:10:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"14:01:00"	,	"BAGAYAM"	,	"14:47:00"	);
            insertData(db,	"201B"	,	"Vellore"	,	"14:10:00"	,	"Cheyyar"	,	"15:50:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"14:10:00"	,	"KASIMANAGAR"	,	"15:20:00"	);
            insertData(db,	"225F"	,	"Vellore"	,	"14:10:00"	,	"Thiruvannamalai"	,	"16:10:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"14:10:00"	,	"PUTHUR"	,	"15:20:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"14:13:00"	,	"BAGAYAM"	,	"15:02:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"14:15:00"	,	"PASUMATHUR"	,	"15:05:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"14:15:00"	,	"REDDIYUR"	,	"15:15:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"14:20:00"	,	"A.K.PADAVEDU"	,	"15:40:00"	);
            insertData(db,	"T 7SA"	,	"VELLORE"	,	"14:20:00"	,	"ARCOT"	,	"15:15:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"14:20:00"	,	"MELMANKUPPAM"	,	"15:35:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"14:20:00"	,	"PERIYABODINATHAM"	,	"15:35:00"	);
            insertData(db,	"407 B"	,	"VELLORE"	,	"14:20:00"	,	"THIRUTHANI"	,	"16:45:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"14:25:00"	,	"ANAICUT"	,	"15:15:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"14:30:00"	,	"DEVARISHIKUPPAM"	,	"15:35:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"14:30:00"	,	"SENJI"	,	"15:35:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"14:32:00"	,	"BAGAYAM"	,	"15:17:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"14:34:00"	,	"BAGAYAM"	,	"15:19:00"	);
            insertData(db,	"T16FA"	,	"VELLORE"	,	"14:35:00"	,	"PARADARAMI"	,	"16:15:00"	);
            insertData(db,	"225G"	,	"Vellore"	,	"14:35:00"	,	"Thiruvannamalai"	,	"16:35:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"14:35:00"	,	"WALAJA"	,	"15:50:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"14:37:00"	,	"BAGAYAM"	,	"15:22:00"	);
            insertData(db,	"T 2AA"	,	"KATPADI"	,	"14:38:00"	,	"ADUKAMPARAI G.H."	,	"15:21:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"14:40:00"	,	"ARCOT"	,	"15:30:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"14:40:00"	,	"KUPPIREDDITHANGAL"	,	"15:40:00"	);
            insertData(db,	"225W"	,	"Vellore"	,	"14:40:00"	,	"Thiruvannamalai"	,	"16:40:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"14:40:00"	,	"PERIACHITTERI"	,	"15:20:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"14:45:00"	,	"ARCOT"	,	"15:35:00"	);
            insertData(db,	"T 7DA"	,	"VELLORE"	,	"14:45:00"	,	"ARCOT"	,	"15:45:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"14:45:00"	,	"ARCOTTANKUDISAI"	,	"15:25:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"14:45:00"	,	"VAZIYUR"	,	"15:55:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"14:45:00"	,	"BAGAYAM"	,	"15:30:00"	);
            insertData(db,	"T40 B"	,	"VELLORE"	,	"14:50:00"	,	"ARCOT"	,	"15:50:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"14:50:00"	,	"JAMALPURAM"	,	"15:30:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"14:50:00"	,	"LABAIKRISHNAPURAM"	,	"15:25:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"14:50:00"	,	"PONNAI"	,	"16:15:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"14:50:00"	,	"SENDRAMPALLI"	,	"15:50:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"14:50:00"	,	"SENDRAYANKOTTAI"	,	"16:00:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"14:50:00"	,	"BAGAYAM"	,	"15:35:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"14:55:00"	,	"PASUMATHUR"	,	"15:55:00"	);
            insertData(db,	"225H"	,	"Vellore"	,	"14:55:00"	,	"Thiruvannamalai"	,	"16:55:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"14:55:00"	,	"VARGURPATTINAM"	,	"16:05:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"14:55:00"	,	"BAGAYAM"	,	"15:40:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"15:00:00"	,	"AMIRTHI"	,	"16:00:00"	);
            insertData(db,	"T 7BA"	,	"VELLORE"	,	"15:00:00"	,	"ARCOT"	,	"15:50:00"	);
            insertData(db,	"T10AA"	,	"VELLORE"	,	"15:00:00"	,	"BHEL"	,	"15:55:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"15:00:00"	,	"NANJUKONDAPURAM"	,	"16:00:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"15:00:00"	,	"BAGAYAM"	,	"15:45:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"15:02:00"	,	"GUDIYATHAM"	,	"16:10:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"15:05:00"	,	"BAGAYAM"	,	"15:50:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"15:07:00"	,	"BAGAYAM"	,	"15:52:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"15:10:00"	,	"VILAPAKKAM"	,	"16:20:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"15:13:00"	,	"PERIYAPUDUR"	,	"15:23:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"15:15:00"	,	"ANAICUT"	,	"16:00:00"	);
            insertData(db,	"T43 B"	,	"VELLORE"	,	"15:15:00"	,	"ARCOT"	,	"16:10:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"15:15:00"	,	"ARJUNAPURAM"	,	"16:35:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"15:15:00"	,	"MUTHUKADAI"	,	"16:20:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"15:15:00"	,	"PACHANAYANIKUPPAM"	,	"16:30:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"15:15:00"	,	"RANIPET"	,	"16:25:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"15:15:00"	,	"SINGIRIKOIL"	,	"16:30:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"15:15:00"	,	"BAGAYAM"	,	"15:58:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"15:20:00"	,	"KILNAGAR"	,	"17:00:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"15:20:00"	,	"THONDANTHULASI"	,	"16:10:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"15:20:00"	,	"VELLORE"	,	"15:35:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"15:23:00"	,	"BAGAYAM"	,	"16:08:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"15:25:00"	,	"REDDIPALAYAM"	,	"16:25:00"	);
            insertData(db,	"225I"	,	"Vellore"	,	"15:25:00"	,	"Thiruvannamalai"	,	"17:25:00"	);
            insertData(db,	"G 9 C"	,	"KATPADI"	,	"15:25:00"	,	"GUDIYATHAM"	,	"16:25:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"15:28:00"	,	"BAGAYAM"	,	"16:13:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"15:30:00"	,	"AMMAVARPALLI"	,	"16:40:00"	);
            insertData(db,	"201A"	,	"Vellore"	,	"15:30:00"	,	"Cheyyar"	,	"17:10:00"	);
            insertData(db,	"T10 A"	,	"VELLORE"	,	"15:30:00"	,	"MUTHUKADAI"	,	"16:45:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"15:32:00"	,	"BAGAYAM"	,	"16:17:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"15:33:00"	,	"BAGAYAM"	,	"16:33:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"15:35:00"	,	"SENDRAYANKOTTAI"	,	"16:40:00"	);
            insertData(db,	"225K"	,	"Vellore"	,	"15:35:00"	,	"Thiruvannamalai"	,	"17:35:00"	);
            insertData(db,	"B51 A"	,	"VELLORE"	,	"15:35:00"	,	"VIRUDHUNAGAR"	,	"16:10:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"15:40:00"	,	"DURAIMOOLAI"	,	"17:00:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"15:40:00"	,	"G.R.PALAYAM"	,	"16:10:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"15:40:00"	,	"G.R.PALAYAM"	,	"16:10:00"	);
            insertData(db,	"225V"	,	"Vellore"	,	"15:40:00"	,	"Thiruvannamalai"	,	"17:40:00"	);
            insertData(db,	"103 A"	,	"VELLORE"	,	"15:40:00"	,	"VARADALAMPET"	,	"17:40:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"15:40:00"	,	"VAZIYUR"	,	"16:45:00"	);
            insertData(db,	"T43 A"	,	"VELLORE"	,	"15:45:00"	,	"ARCOT"	,	"16:35:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"15:45:00"	,	"PALLATHUR"	,	"16:45:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"15:45:00"	,	"RATHINAGIRI"	,	"16:20:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"15:45:00"	,	"SENDRAMPALLI"	,	"16:45:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"15:45:00"	,	"VAZHAVANKUNDRAM"	,	"16:45:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"15:47:00"	,	"BAGAYAM"	,	"16:32:00"	);
            insertData(db,	"T 7AF"	,	"VELLORE"	,	"15:50:00"	,	"ARCOT"	,	"16:35:00"	);
            insertData(db,	"T 7VB"	,	"VELLORE"	,	"15:50:00"	,	"ARCOT"	,	"16:45:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"15:50:00"	,	"ARCOT"	,	"16:40:00"	);
            insertData(db,	"T 3 A"	,	"VELLORE"	,	"15:50:00"	,	"NAICKENERI"	,	"16:40:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"15:50:00"	,	"SRIPURAM"	,	"16:30:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"15:50:00"	,	"VARADALAMPATTU"	,	"16:50:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"15:52:00"	,	"VELLORE"	,	"16:15:00"	);
            insertData(db,	"T13CA"	,	"VELLORE"	,	"15:55:00"	,	"KILKOTHUR"	,	"17:30:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"15:55:00"	,	"MUTHUKADAI"	,	"15:45:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"16:00:00"	,	"KARUNGALI"	,	"17:10:00"	);
            insertData(db,	"T10AB"	,	"KATPADI"	,	"16:00:00"	,	"ARCOT"	,	"17:05:00"	);
            insertData(db,	"G 9MA"	,	"KATPADI"	,	"16:00:00"	,	"GUDIYATHAM"	,	"17:15:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"16:04:00"	,	"BAGAYAM"	,	"16:49:00"	);
            insertData(db,	"T20AB"	,	"VELLORE"	,	"16:05:00"	,	"AVANARANGAPALLI"	,	"17:40:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"16:05:00"	,	"PASUMATHUR"	,	"16:55:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"16:05:00"	,	"PUDUPALAYAM"	,	"17:20:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"16:05:00"	,	"PUNGANOOR"	,	"16:45:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"16:09:00"	,	"BAGAYAM"	,	"16:54:00"	);
            insertData(db,	"161 A"	,	"VELLORE"	,	"16:10:00"	,	"ARAKKONAM"	,	"19:00:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"16:10:00"	,	"ATHIYUR"	,	"16:55:00"	);
            insertData(db,	"T10AC"	,	"VELLORE"	,	"16:10:00"	,	"KATPADI"	,	"16:25:00"	);
            insertData(db,	"225N"	,	"Vellore"	,	"16:10:00"	,	"Thiruvannamalai"	,	"18:10:00"	);
            insertData(db,	"T12 A"	,	"KATPADI"	,	"16:10:00"	,	"ARCOT"	,	"17:10:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"16:15:00"	,	"AMIRTHI"	,	"17:30:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"16:15:00"	,	"ARCOTTANKUDISAI"	,	"16:55:00"	);
            insertData(db,	"104 B"	,	"VELLORE"	,	"16:15:00"	,	"MELARASAMPET"	,	"18:40:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"16:15:00"	,	"SERKADU"	,	"17:15:00"	);
            insertData(db,	"T20MA"	,	"VELLORE"	,	"16:15:00"	,	"THENGAL"	,	"18:00:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"16:15:00"	,	"66 PUTHUR"	,	"16:35:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"16:15:00"	,	"BAGAYAM"	,	"17:00:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"16:20:00"	,	"ERIPUDUR"	,	"17:25:00"	);
            insertData(db,	"T P"	,	"VELLORE"	,	"16:20:00"	,	"KATPADI"	,	"16:45:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"16:20:00"	,	"KAVANUR R.S"	,	"17:25:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"16:20:00"	,	"RATHINAGIRI"	,	"16:50:00"	);
            insertData(db,	"225O"	,	"Vellore"	,	"16:20:00"	,	"Thiruvannamalai"	,	"18:20:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"16:20:00"	,	"VINNAMPALLI"	,	"17:05:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"16:20:00"	,	"WALAJA"	,	"17:40:00"	);
            insertData(db,	"T 7JB"	,	"VELLORE"	,	"16:20:00"	,	"WALAJA"	,	"16:45:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"16:20:00"	,	"ADUKAMPARAI G.H."	,	"17:20:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"16:20:00"	,	"PERIACHITTERI"	,	"17:05:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"16:20:00"	,	"T.E.L."	,	"16:40:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"16:25:00"	,	"ANAICUT"	,	"17:30:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"16:25:00"	,	"ARCOT"	,	"17:25:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"16:25:00"	,	"KALLANKULAM"	,	"17:15:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"16:25:00"	,	"MAHADEVAMALAI"	,	"17:45:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"16:25:00"	,	"OTHIYATHUR"	,	"17:30:00"	);
            insertData(db,	"83 A"	,	"VELLORE"	,	"16:25:00"	,	"PALAMANER"	,	"18:55:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"16:25:00"	,	"BAGAYAM"	,	"16:25:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"16:30:00"	,	"AMIRTHI"	,	"17:40:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"16:30:00"	,	"REDDIPALAYAM"	,	"17:30:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"16:30:00"	,	"ARCOT"	,	"17:30:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"16:30:00"	,	"KATPADI"	,	"16:30:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"16:30:00"	,	"PUTHUR"	,	"17:30:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"16:30:00"	,	"VANDRANTHANGAL"	,	"16:45:00"	);
            insertData(db,	"T 7BB"	,	"VELLORE"	,	"16:32:00"	,	"CHETTITHANGAL"	,	"17:55:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"16:35:00"	,	"THONDANTHULASI"	,	"17:15:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"16:35:00"	,	"BAGAYAM"	,	"17:20:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"16:37:00"	,	"BAGAYAM"	,	"17:22:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"16:40:00"	,	"ARCOT"	,	"17:30:00"	);
            insertData(db,	"T10FA"	,	"VELLORE"	,	"16:40:00"	,	"RANIPET"	,	"18:00:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"16:45:00"	,	"MOTHAKKAL"	,	"17:45:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"16:49:00"	,	"KALPUDUR"	,	"17:05:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"16:50:00"	,	"ARCOT"	,	"17:50:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"16:50:00"	,	"DEVARISHIKUPPAM"	,	"18:25:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"16:50:00"	,	"T.E.L."	,	"17:10:00"	);
            insertData(db,	"B51 A"	,	"VELLORE"	,	"16:55:00"	,	"AMATHUR"	,	"17:05:00"	);
            insertData(db,	"225X"	,	"Vellore"	,	"16:55:00"	,	"Thiruvannamalai"	,	"18:55:00"	);
            insertData(db,	"G 9 A"	,	"KATPADI"	,	"16:55:00"	,	"GUDIYATHAM"	,	"18:00:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"16:59:00"	,	"BAGAYAM"	,	"17:48:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"17:00:00"	,	"KALANGAMEDU"	,	"17:40:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"17:00:00"	,	"PARADARAMI"	,	"18:20:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"17:00:00"	,	"SENDRAMPALLI"	,	"18:20:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"17:00:00"	,	"66 PUTHUR"	,	"17:30:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"17:02:00"	,	"BAGAYAM"	,	"17:46:00"	);
            insertData(db,	"225P"	,	"Vellore"	,	"17:05:00"	,	"Thiruvannamalai"	,	"19:05:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"17:05:00"	,	"LALAPET"	,	"18:25:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"17:07:00"	,	"BAGAYAM"	,	"17:52:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"17:10:00"	,	"CHINNAPUTHUR"	,	"18:20:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"17:10:00"	,	"MARUTHAMPAKKAM"	,	"18:25:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"17:10:00"	,	"MUTHUKADAI"	,	"18:20:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"17:10:00"	,	"BAGAYAM"	,	"17:37:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"17:15:00"	,	"ARCOT"	,	"18:15:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"17:15:00"	,	"PASUMATHUR"	,	"18:20:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"17:15:00"	,	"SINGAREDDIYUR"	,	"18:00:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"17:17:00"	,	"T.E.L."	,	"17:37:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"17:20:00"	,	"GURUVARAJAPALAYAM"	,	"18:30:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"17:20:00"	,	"NANJUKONDAPURAM"	,	"18:20:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"17:20:00"	,	"PERUMALKUPPAM"	,	"18:40:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"17:20:00"	,	"WALAJA"	,	"18:30:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"17:25:00"	,	"ANAICUT"	,	"18:15:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"17:25:00"	,	"DURUVAM"	,	"18:45:00"	);
            insertData(db,	"225S"	,	"Vellore"	,	"17:25:00"	,	"Thiruvannamalai"	,	"19:27:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"17:25:00"	,	"VARGURPATTINAM"	,	"18:35:00"	);
            insertData(db,	"B51 A"	,	"VELLORE"	,	"17:25:00"	,	"VIRUDHUNAGAR"	,	"18:00:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"17:28:00"	,	"BAGAYAM"	,	"18:13:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"17:30:00"	,	"ADUKAMPARAI"	,	"18:00:00"	);
            insertData(db,	"T40B"	,	"Vengayavellore"	,	"17:30:00"	,	"Che.Agaram"	,	"18:30:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"17:30:00"	,	"KUPPIREDDITHANGAL"	,	"18:40:00"	);
            insertData(db,	"T L"	,	"VELLORE"	,	"17:30:00"	,	"PERIACHITTERI"	,	"17:50:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"17:30:00"	,	"RATHINAGIRI"	,	"18:10:00"	);
            insertData(db,	"34B"	,	"UPPUVELLORE"	,	"17:30:00"	,	"TINDIVANAM"	,	"18:30:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"17:33:00"	,	"BAGAYAM"	,	"18:17:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"17:35:00"	,	"ARCOT"	,	"18:35:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"17:35:00"	,	"IRUMBULI"	,	"18:45:00"	);
            insertData(db,	"225A"	,	"Vellore"	,	"17:35:00"	,	"Thiruvannamalai"	,	"19:35:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"17:38:00"	,	"BAGAYAM"	,	"18:37:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"17:39:00"	,	"BAGAYAM"	,	"18:24:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"17:40:00"	,	"MULLENDRAM"	,	"19:00:00"	);
            insertData(db,	"407 A"	,	"VELLORE"	,	"17:40:00"	,	"THIRUTHANI"	,	"20:35:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"17:40:00"	,	"GUDIYATHAM"	,	"18:45:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"17:42:00"	,	"BAGAYAM"	,	"18:22:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"17:45:00"	,	"ARCOTTANKUDISAI"	,	"18:35:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"17:45:00"	,	"NANJUKONDAPURAM"	,	"18:55:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"17:45:00"	,	"BAGAYAM"	,	"18:30:00"	);
            insertData(db,	"T20 R"	,	"VELLORE"	,	"17:50:00"	,	"PONNAIPUDUR"	,	"19:20:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"17:50:00"	,	"KATPADI"	,	"17:50:00"	);
            insertData(db,	"T10AA"	,	"VELLORE"	,	"17:55:00"	,	"MUTHUKADAI"	,	"19:00:00"	);
            insertData(db,	"777VA"	,	"Vellore"	,	"17:55:00"	,	"Polur"	,	"22:35:00"	);
            insertData(db,	"225B"	,	"Vellore"	,	"17:55:00"	,	"Thiruvannamalai"	,	"19:55:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"17:55:00"	,	"BAGAYAM"	,	"18:40:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"18:00:00"	,	"AMMAVARPALLI"	,	"19:10:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"18:00:00"	,	"ANANTHAPURAM H.S"	,	"19:10:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"18:00:00"	,	"ATHIYUR"	,	"18:50:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"18:00:00"	,	"DEVARISHIKUPPAM"	,	"19:10:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"18:00:00"	,	"ODUGATHUR"	,	"19:45:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"18:00:00"	,	"PALLATHUR"	,	"19:00:00"	);
            insertData(db,	"225J"	,	"Vellore"	,	"18:00:00"	,	"Thiruvannamalai"	,	"20:00:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"18:00:00"	,	"VALAYAKARANPATTI"	,	"19:15:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"18:00:00"	,	"VAZIYUR"	,	"19:10:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"18:00:00"	,	"VILAPAKKAM"	,	"19:10:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"18:00:00"	,	"BAGAYAM"	,	"18:45:00"	);
            insertData(db,	"T 7VA"	,	"VELLORE"	,	"18:05:00"	,	"ARCOT"	,	"18:55:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"18:05:00"	,	"RAGUNATHAPURAM"	,	"19:05:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"18:05:00"	,	"REDDIYUR"	,	"19:05:00"	);
            insertData(db,	"7C"	,	"UPPUVELLORE"	,	"18:05:00"	,	"TINDIVANAM"	,	"19:05:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"18:05:00"	,	"BAGAYAM"	,	"18:50:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"18:07:00"	,	"BAGAYAM"	,	"18:52:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"18:10:00"	,	"VALLIMALAI"	,	"19:20:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"18:12:00"	,	"BAGAYAM"	,	"18:57:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"18:15:00"	,	"AMIRTHI"	,	"19:35:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"18:15:00"	,	"RANIPET"	,	"19:25:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"18:15:00"	,	"SINGIRIKOIL"	,	"19:15:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"18:15:00"	,	"THIPPASAMUDRAM"	,	"19:20:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"18:15:00"	,	"VARADALAMPATTU"	,	"19:15:00"	);
            insertData(db,	"G 9AA"	,	"KATPADI"	,	"18:15:00"	,	"GUDIYATHAM"	,	"19:30:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"18:20:00"	,	"G.R.PALAYAM"	,	"19:00:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"18:20:00"	,	"G.R.PALAYAM"	,	"19:00:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"18:20:00"	,	"PALAYATHONDANTHULASI"	,	"19:00:00"	);
            insertData(db,	"T16FA"	,	"VELLORE"	,	"18:20:00"	,	"PARADARAMI"	,	"19:50:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"18:20:00"	,	"WALAJA"	,	"19:30:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"18:22:00"	,	"LAPPAIKRISHNAPURAM"	,	"19:30:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"18:25:00"	,	"KANNAMANGALAM"	,	"19:25:00"	);
            insertData(db,	"T12BA"	,	"VELLORE"	,	"18:25:00"	,	"PASUMATHUR"	,	"19:15:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"18:30:00"	,	"ANAICUT"	,	"19:30:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"18:30:00"	,	"CHINNACHERI"	,	"20:00:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"18:32:00"	,	"BAGAYAM"	,	"19:17:00"	);
            insertData(db,	"T 7 C"	,	"VELLORE"	,	"18:35:00"	,	"ARCOT"	,	"19:25:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"18:35:00"	,	"DURAIMOOLAI"	,	"19:55:00"	);
            insertData(db,	"T10 A"	,	"VELLORE"	,	"18:35:00"	,	"MUTHUKADAI"	,	"19:50:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"18:35:00"	,	"REDDIPALAYAM"	,	"19:35:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"18:38:00"	,	"BAGAYAM"	,	"19:22:00"	);
            insertData(db,	"T38 A"	,	"VELLORE"	,	"18:40:00"	,	"ARCOT"	,	"19:30:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"18:40:00"	,	"ERIPUDUR"	,	"19:40:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"18:40:00"	,	"ERIPUDUR"	,	"19:40:00"	);
            insertData(db,	"225C"	,	"Vellore"	,	"18:40:00"	,	"Thiruvannamalai"	,	"20:40:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"18:40:00"	,	"ADUKAMPARAI G.H."	,	"19:40:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"18:43:00"	,	"PERIACHITTERI"	,	"19:23:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"18:45:00"	,	"A.K.PADAVEDU"	,	"20:05:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"18:47:00"	,	"BAGAYAM"	,	"19:32:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"18:50:00"	,	"G.R.PALAYAM"	,	"19:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"18:50:00"	,	"G.R.PALAYAM"	,	"19:20:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"18:50:00"	,	"ONNUPURAM"	,	"20:05:00"	);
            insertData(db,	"225D"	,	"Vellore"	,	"18:50:00"	,	"Thiruvannamalai"	,	"20:50:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"18:50:00"	,	"PUTHUR"	,	"19:50:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"18:53:00"	,	"BAGAYAM"	,	"19:38:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"18:55:00"	,	"ARCOT"	,	"20:45:00"	);
            insertData(db,	"T 7AC"	,	"VELLORE"	,	"18:55:00"	,	"WALAJA"	,	"20:10:00"	);
            insertData(db,	"G 9MA"	,	"KATPADI"	,	"18:55:00"	,	"GUDIYATHAM"	,	"20:05:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"19:00:00"	,	"AMIRTHI"	,	"20:05:00"	);
            insertData(db,	"T 7 F"	,	"VELLORE"	,	"19:00:00"	,	"ARCOT"	,	"20:00:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"19:00:00"	,	"OTHIYATHUR"	,	"20:10:00"	);
            insertData(db,	"225R"	,	"Vellore"	,	"19:00:00"	,	"Thiruvannamalai"	,	"21:00:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"19:03:00"	,	"BAGAYAM"	,	"19:48:00"	);
            insertData(db,	"T16MA"	,	"VELLORE"	,	"19:05:00"	,	"MAHADEVAMALAI"	,	"20:20:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"19:05:00"	,	"BAGAYAM"	,	"19:50:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"19:09:00"	,	"BAGAYAM"	,	"19:54:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"19:10:00"	,	"KANIYAMBADI"	,	"19:50:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"19:10:00"	,	"ARCOT"	,	"20:10:00"	);
            insertData(db,	"T 7JB"	,	"VELLORE"	,	"19:12:00"	,	"RANIPET"	,	"20:25:00"	);
            insertData(db,	"T 8 A"	,	"VELLORE"	,	"19:15:00"	,	"ANAICUT"	,	"20:00:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"19:15:00"	,	"ARCOT"	,	"20:15:00"	);
            insertData(db,	"T10AB"	,	"VELLORE"	,	"19:15:00"	,	"ARCOT"	,	"20:30:00"	);
            insertData(db,	"T17KA"	,	"VELLORE"	,	"19:15:00"	,	"KAVANUR R.S"	,	"20:20:00"	);
            insertData(db,	"225E"	,	"Vellore"	,	"19:15:00"	,	"Thiruvannamalai"	,	"21:15:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"19:15:00"	,	"VAZIYUR"	,	"20:25:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"19:15:00"	,	"BAGAYAM"	,	"20:02:00"	);
            insertData(db,	"T 7VB"	,	"VELLORE"	,	"19:16:00"	,	"ARCOT"	,	"20:00:00"	);
            insertData(db,	"G 9 D"	,	"KATPADI"	,	"19:20:00"	,	"GUDIYATHAM"	,	"20:20:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"19:20:00"	,	"KATPADI"	,	"19:20:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"19:25:00"	,	"ARCOTTANKUDISAI"	,	"20:00:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"19:25:00"	,	"BAGAYAM"	,	"20:16:00"	);
            insertData(db,	"T 2KA"	,	"VELLORE"	,	"19:30:00"	,	"AMIRTHI"	,	"20:45:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"19:30:00"	,	"NANJUKONDAPURAM"	,	"20:30:00"	);
            insertData(db,	"T20MA"	,	"VELLORE"	,	"19:30:00"	,	"THENGAL"	,	"20:30:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"19:30:00"	,	"BAGAYAM"	,	"20:23:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"19:33:00"	,	"BAGAYAM"	,	"20:13:00"	);
            insertData(db,	"T 7BB"	,	"VELLORE"	,	"19:35:00"	,	"ARCOT"	,	"20:35:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"19:35:00"	,	"JAMALPURAM"	,	"20:20:00"	);
            insertData(db,	"T13CB"	,	"VELLORE"	,	"19:35:00"	,	"KILKOTHUR"	,	"21:25:00"	);
            insertData(db,	"103 A"	,	"VELLORE"	,	"19:35:00"	,	"ODUGATHUR"	,	"21:10:00"	);
            insertData(db,	"T20AB"	,	"VELLORE"	,	"19:35:00"	,	"PONNAI"	,	"20:50:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"19:35:00"	,	"SENDRAMPALLI"	,	"20:35:00"	);
            insertData(db,	"225F"	,	"Vellore"	,	"19:35:00"	,	"Thiruvannamalai"	,	"21:35:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"19:35:00"	,	"BAGAYAM"	,	"20:30:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"19:37:00"	,	"BAGAYAM"	,	"20:22:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"19:40:00"	,	"G.R.PALAYAM"	,	"20:25:00"	);
            insertData(db,	"T 7 J"	,	"VELLORE"	,	"19:40:00"	,	"G.R.PALAYAM"	,	"20:25:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"19:42:00"	,	"BAGAYAM"	,	"20:27:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"19:45:00"	,	"ARCOT"	,	"20:35:00"	);
            insertData(db,	"T 7 I"	,	"VELLORE"	,	"19:45:00"	,	"ARCOT"	,	"20:35:00"	);
            insertData(db,	"201C"	,	"Vellore"	,	"19:45:00"	,	"Cheyyar"	,	"21:30:00"	);
            insertData(db,	"G 9 A"	,	"KATPADI"	,	"19:45:00"	,	"GUDIYATHAM"	,	"20:55:00"	);
            insertData(db,	"T10 B"	,	"VELLORE"	,	"19:50:00"	,	"MUTHUKADAI"	,	"20:50:00"	);
            insertData(db,	"T12BB"	,	"VELLORE"	,	"19:50:00"	,	"PASUMATHUR"	,	"20:50:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"19:50:00"	,	"SRIPURAM"	,	"20:20:00"	);
            insertData(db,	"225G"	,	"Vellore"	,	"19:55:00"	,	"Thiruvannamalai"	,	"21:55:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"19:59:00"	,	"BAGAYAM"	,	"20:59:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"20:00:00"	,	"ADUKAMPARAI"	,	"20:30:00"	);
            insertData(db,	"T13 A"	,	"VELLORE"	,	"20:00:00"	,	"AGARAM"	,	"21:20:00"	);
            insertData(db,	"T16FB"	,	"VELLORE"	,	"20:00:00"	,	"PARADARAMI"	,	"21:20:00"	);
            insertData(db,	"407 B"	,	"VELLORE"	,	"20:00:00"	,	"SHOLINGHUR"	,	"21:15:00"	);
            insertData(db,	"400 A"	,	"VELLORE"	,	"20:00:00"	,	"THIRUTHANI"	,	"22:10:00"	);
            insertData(db,	"9E"	,	"VELLOREGRAMAM"	,	"20:00:00"	,	"ULUNTHURPET"	,	"20:55:00"	);
            insertData(db,	"G 9 C"	,	"KATPADI"	,	"20:00:00"	,	"GUDIYATHAM"	,	"21:05:00"	);
            insertData(db,	"T10CA"	,	"KATPADI"	,	"20:00:00"	,	"MUTHUKADAI"	,	"21:00:00"	);
            insertData(db,	"T11 B"	,	"VELLORE"	,	"20:05:00"	,	"KND DEPOT"	,	"20:10:00"	);
            insertData(db,	"T10BA"	,	"VELLORE"	,	"20:05:00"	,	"KUPPIREDDITHANGAL"	,	"21:20:00"	);
            insertData(db,	"T L"	,	"KATPADI"	,	"20:08:00"	,	"VELLORE"	,	"21:15:00"	);
            insertData(db,	"T10FA"	,	"VELLORE"	,	"20:10:00"	,	"LALAPET"	,	"21:45:00"	);
            insertData(db,	"225W"	,	"Vellore"	,	"20:10:00"	,	"Thiruvannamalai"	,	"22:10:00"	);
            insertData(db,	"T10DA"	,	"KATPADI"	,	"20:10:00"	,	"WALAJA"	,	"21:20:00"	);
            insertData(db,	"T 5 A"	,	"VELLORE"	,	"20:15:00"	,	"KALANGAMEDU"	,	"21:00:00"	);
            insertData(db,	"T16DA"	,	"VELLORE"	,	"20:15:00"	,	"MELMANKUPPAM"	,	"21:40:00"	);
            insertData(db,	"G 9 B"	,	"KATPADI"	,	"20:15:00"	,	"GUDIYATHAM"	,	"21:25:00"	);
            insertData(db,	"T 7SA"	,	"VELLORE"	,	"20:20:00"	,	"ARCOT"	,	"21:10:00"	);
            insertData(db,	"T20BA"	,	"VELLORE"	,	"20:20:00"	,	"PERUMALKUPPAM"	,	"21:40:00"	);
            insertData(db,	"T60 A"	,	"VELLORE"	,	"20:20:00"	,	"RAGUNATHAPURAM"	,	"21:20:00"	);
            insertData(db,	"7C"	,	"UPPUVELLORE"	,	"20:20:00"	,	"TINDIVANAM"	,	"21:20:00"	);
            insertData(db,	"T Q"	,	"KATPADI"	,	"20:23:00"	,	"ADUKAMPARAI G.H."	,	"21:40:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"20:23:00"	,	"BAGAYAM"	,	"21:08:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"20:28:00"	,	"BAGAYAM"	,	"21:12:00"	);
            insertData(db,	"T 8 B"	,	"VELLORE"	,	"20:30:00"	,	"ANAICUT"	,	"21:25:00"	);
            insertData(db,	"T 7BA"	,	"VELLORE"	,	"20:30:00"	,	"ARCOT"	,	"21:30:00"	);
            insertData(db,	"T 2VA"	,	"VELLORE"	,	"20:30:00"	,	"KILNAGAR"	,	"22:05:00"	);
            insertData(db,	"T25 A"	,	"VELLORE"	,	"20:30:00"	,	"NANJUKONDAPURAM"	,	"21:35:00"	);
            insertData(db,	"T N"	,	"KATPADI"	,	"20:33:00"	,	"BAGAYAM"	,	"21:18:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"20:33:00"	,	"BAGAYAM"	,	"21:18:00"	);
            insertData(db,	"T 3 A"	,	"VELLORE"	,	"20:35:00"	,	"KATPADI"	,	"20:55:00"	);
            insertData(db,	"T12AA"	,	"VELLORE"	,	"20:35:00"	,	"THIPPASAMUDRAM"	,	"21:40:00"	);
            insertData(db,	"T 2AA"	,	"KATPADI"	,	"20:37:00"	,	"VELLORE"	,	"21:02:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"20:39:00"	,	"BAGAYAM"	,	"21:24:00"	);
            insertData(db,	"T11CA"	,	"VELLORE"	,	"20:40:00"	,	"ANANTHAPURAM H.S"	,	"21:55:00"	);
            insertData(db,	"201A"	,	"Vellore"	,	"20:40:00"	,	"Cheyyar"	,	"22:30:00"	);
            insertData(db,	"T 2BA"	,	"VELLORE"	,	"20:40:00"	,	"MELAKUPPAM"	,	"21:30:00"	);
            insertData(db,	"T P"	,	"KATPADI"	,	"20:40:00"	,	"BAGAYAM"	,	"21:25:00"	);
            insertData(db,	"T 7 C"	,	"VELLORE"	,	"20:45:00"	,	"ARCOT"	,	"21:35:00"	);
            insertData(db,	"T 1CA"	,	"VELLORE"	,	"20:45:00"	,	"MOOLAKANKUPPAM"	,	"22:00:00"	);
            insertData(db,	"T 3AA"	,	"VELLORE"	,	"20:45:00"	,	"MULLENDRAM"	,	"21:55:00"	);
            insertData(db,	"T20AA"	,	"VELLORE"	,	"20:45:00"	,	"PONNAI"	,	"22:00:00"	);
            insertData(db,	"T20 A"	,	"VELLORE"	,	"20:45:00"	,	"VELLORE"	,	"21:50:00"	);
            insertData(db,	"T A"	,	"KATPADI"	,	"20:47:00"	,	"BAGAYAM"	,	"21:30:00"	);
            insertData(db,	"T11AA"	,	"VELLORE"	,	"20:50:00"	,	"KND DEPOT"	,	"21:00:00"	);
            insertData(db,	"T13BA"	,	"VELLORE"	,	"20:50:00"	,	"MOTHAKKAL"	,	"21:50:00"	);
            insertData(db,	"T10AA"	,	"VELLORE"	,	"20:50:00"	,	"MUTHUKADAI"	,	"21:50:00"	);
            insertData(db,	"T13CA"	,	"VELLORE"	,	"20:50:00"	,	"PINNATHURAI"	,	"22:15:00"	);
            insertData(db,	"T13AA"	,	"VELLORE"	,	"20:50:00"	,	"VARADALAMPATTU"	,	"21:50:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"20:54:00"	,	"KATPADI"	,	"20:54:00"	);
            insertData(db,	"225H"	,	"Vellore"	,	"20:55:00"	,	"Thiruvannamalai"	,	"22:55:00"	);
            insertData(db,	"T10DB"	,	"KATPADI"	,	"20:55:00"	,	"VELLORE"	,	"21:10:00"	);
            insertData(db,	"T11 A"	,	"VELLORE"	,	"21:00:00"	,	"IRUMBULI"	,	"22:10:00"	);
            insertData(db,	"T 8 D"	,	"VELLORE"	,	"21:00:00"	,	"KND DEPOT"	,	"21:10:00"	);
            insertData(db,	"T15SA"	,	"VELLORE"	,	"21:00:00"	,	"SINGIRIKOIL"	,	"22:20:00"	);
            insertData(db,	"T 8AA"	,	"VELLORE"	,	"21:00:00"	,	"VELLORE"	,	"22:10:00"	);
            insertData(db,	"T 7TA"	,	"VELLORE"	,	"21:00:00"	,	"VILAPAKKAM"	,	"22:10:00"	);
            insertData(db,	"T 3 A"	,	"KATPADI"	,	"21:00:00"	,	"VELLORE"	,	"21:20:00"	);
            insertData(db,	"T 1GB"	,	"KATPADI"	,	"21:00:00"	,	"VELLORE"	,	"21:30:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"21:01:00"	,	"BAGAYAM"	,	"20:16:00"	);
            insertData(db,	"T C"	,	"KATPADI"	,	"21:01:00"	,	"BAGAYAM"	,	"21:46:00"	);
            insertData(db,	"104 B"	,	"VELLORE"	,	"21:05:00"	,	"ODUGATHUR"	,	"22:50:00"	);
            insertData(db,	"T 2AA"	,	"VELLORE"	,	"21:05:00"	,	"T.E.L./AP"	,	"21:40:00"	);
            insertData(db,	"T D"	,	"KATPADI"	,	"21:08:00"	,	"BAGAYAM"	,	"21:53:00"	);
            insertData(db,	"225I"	,	"Vellore"	,	"21:10:00"	,	"Thiruvannamalai"	,	"23:10:00"	);
            insertData(db,	"T22 A"	,	"VELLORE"	,	"21:10:00"	,	"VAZIYUR"	,	"22:20:00"	);
            insertData(db,	"T 8 E"	,	"KATPADI"	,	"21:10:00"	,	"PUTHUR"	,	"22:05:00"	);
            insertData(db,	"T G"	,	"KATPADI"	,	"21:12:00"	,	"BAGAYAM"	,	"21:57:00"	);
            insertData(db,	"T11DA"	,	"VELLORE"	,	"21:15:00"	,	"AMIRTHI"	,	"22:15:00"	);
            insertData(db,	"T15 A"	,	"VELLORE"	,	"21:15:00"	,	"AMIRTHI"	,	"22:30:00"	);
            insertData(db,	"T 7 K"	,	"VELLORE"	,	"21:15:00"	,	"MAHAMADUPURAM"	,	"22:35:00"	);
            insertData(db,	"T 7JA"	,	"VELLORE"	,	"21:15:00"	,	"RANIPET"	,	"22:25:00"	);
            insertData(db,	"T19 A"	,	"VELLORE"	,	"21:15:00"	,	"VALAYAKARANPATTI"	,	"22:15:00"	);
            insertData(db,	"T16CA"	,	"VELLORE"	,	"21:15:00"	,	"VAZHAVANKUNDRAM"	,	"22:30:00"	);
            insertData(db,	"T E"	,	"KATPADI"	,	"21:15:00"	,	"BAGAYAM"	,	"22:00:00"	);
            insertData(db,	"T M"	,	"KATPADI"	,	"21:19:00"	,	"BAGAYAM"	,	"22:00:00"	);
            insertData(db,	"T 7VA"	,	"VELLORE"	,	"21:20:00"	,	"ARCOT"	,	"22:10:00"	);
            insertData(db,	"T16BA"	,	"VELLORE"	,	"21:20:00"	,	"DURAIMOOLAI"	,	"22:30:00"	);
            insertData(db,	"T23 A"	,	"VELLORE"	,	"21:20:00"	,	"ONNUPURAM"	,	"22:20:00"	);
            insertData(db,	"T14 A"	,	"VELLORE"	,	"21:20:00"	,	"SERKADU"	,	"22:20:00"	);
            insertData(db,	"225K"	,	"Vellore"	,	"21:20:00"	,	"Thiruvannamalai"	,	"23:20:00"	);
            insertData(db,	"T J"	,	"KATPADI"	,	"21:20:00"	,	"VELLORE"	,	"22:00:00"	);
            insertData(db,	"T11BA"	,	"VELLORE"	,	"21:25:00"	,	"KAVANUR"	,	"22:45:00"	);
            insertData(db,	"T63 A"	,	"VELLORE"	,	"21:25:00"	,	"SENJIKRISHNAPURAM"	,	"22:45:00"	);
            insertData(db,	"T I"	,	"KATPADI"	,	"21:28:00"	,	"BAGAYAM"	,	"22:45:00"	);
            insertData(db,	"T 7 E"	,	"VELLORE"	,	"21:30:00"	,	"ARCOT"	,	"22:15:00"	);
            insertData(db,	"T 8BA"	,	"VELLORE"	,	"21:30:00"	,	"ATHIYUR"	,	"22:10:00"	);
            insertData(db,	"T12KA"	,	"VELLORE"	,	"21:30:00"	,	"KARUNGALI"	,	"22:45:00"	);
            insertData(db,	"T 3 A"	,	"VELLORE"	,	"21:30:00"	,	"NAICKENERI"	,	"22:20:00"	);
            insertData(db,	"T16FA"	,	"VELLORE"	,	"21:30:00"	,	"PARADARAMI"	,	"23:00:00"	);
            insertData(db,	"T61 A"	,	"VELLORE"	,	"21:30:00"	,	"THONDANTHULASI"	,	"22:15:00"	);
            insertData(db,	"T10AC"	,	"KATPADI"	,	"21:30:00"	,	"MUTHUKADAI"	,	"22:20:00"	);
            insertData(db,	"T 7AA"	,	"VELLORE"	,	"21:40:00"	,	"ARCOT"	,	"22:20:00"	);
            insertData(db,	"T17 A"	,	"VELLORE"	,	"21:40:00"	,	"DEVARISHIKUPPAM"	,	"23:00:00"	);
            insertData(db,	"T11 G"	,	"VELLORE"	,	"21:40:00"	,	"KND DEPOT"	,	"21:45:00"	);
            insertData(db,	"T 8 C"	,	"VELLORE"	,	"21:45:00"	,	"KALLANKULAM"	,	"22:35:00"	);
            insertData(db,	"T15NA"	,	"VELLORE"	,	"21:45:00"	,	"NANJUKONDAPURAM"	,	"22:45:00"	);
            insertData(db,	"161 A"	,	"VELLORE"	,	"21:45:00"	,	"THIRUTHANI"	,	"0:00:00"	);
            insertData(db,	"T10DB"	,	"VELLORE"	,	"21:50:00"	,	"66 PUTHUR"	,	"22:00:00"	);
            insertData(db,	"T24 A"	,	"VELLORE"	,	"21:50:00"	,	"CHINNAPUTHUR"	,	"23:05:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"21:50:00"	,	"ERIYUR"	,	"23:25:00"	);
            insertData(db,	"104 A"	,	"VELLORE"	,	"21:50:00"	,	"ERIYUR"	,	"23:25:00"	);
            insertData(db,	"T21 A"	,	"VELLORE"	,	"21:50:00"	,	"OTHIYATHUR"	,	"23:00:00"	);
            insertData(db,	"T16 A"	,	"VELLORE"	,	"21:50:00"	,	"SENDRAMPALLI"	,	"22:50:00"	);
            insertData(db,	"225V"	,	"Vellore"	,	"21:55:00"	,	"Thiruvannamalai"	,	"23:55:00"	);
            insertData(db,	"G 9MA"	,	"KATPADI"	,	"22:00:00"	,	"GUDIYATHAM"	,	"23:00:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"22:00:00"	,	"PERIYAPUDUR"	,	"22:10:00"	);
            insertData(db,	"T O"	,	"KATPADI"	,	"22:04:00"	,	"VELLORE"	,	"22:30:00"	);
            insertData(db,	"T H"	,	"KATPADI"	,	"22:05:00"	,	"BAGAYAM"	,	"22:50:00"	);
            insertData(db,	"T16EA"	,	"VELLORE"	,	"22:10:00"	,	"PALLATHUR"	,	"23:10:00"	);
            insertData(db,	"T F"	,	"KATPADI"	,	"22:13:00"	,	"VELLORE"	,	"22:38:00"	);
            insertData(db,	"T 1GA"	,	"KATPADI"	,	"22:20:00"	,	"VELLORE"	,	"22:45:00"	);
            insertData(db,	"225N"	,	"Vellore"	,	"22:25:00"	,	"Thiruvannamalai"	,	"0:25:00"	);
            insertData(db,	"T B"	,	"KATPADI"	,	"22:25:00"	,	"KATPADI"	,	"22:25:00"	);
            insertData(db,	"225X"	,	"Vellore"	,	"22:35:00"	,	"Thiruvannamalai"	,	"0:35:00"	);
            insertData(db,	"156RA"	,	"VELLORE"	,	"22:45:00"	,	"ARCOT"	,	"23:15:00"	);
            insertData(db,	"T B"	,	"VELLORE"	,	"22:50:00"	,	"VELLORE"	,	"0:00:00"	);
            insertData(db,	"G 9 C"	,	"KATPADI"	,	"22:50:00"	,	"GUDIYATHAM"	,	"23:55:00"	);
            insertData(db,	"225O"	,	"Vellore"	,	"23:00:00"	,	"Thiruvannamalai"	,	"1:00:00"	);
            insertData(db,	"225P"	,	"Vellore"	,	"23:20:00"	,	"Thiruvannamalai"	,	"1:20:00"	);
            insertData(db,	"225S"	,	"Vellore"	,	"23:30:00"	,	"Thiruvannamalai"	,	"1:30:00"	);





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

//            q2schedule(source,departure);
            q3schedule(source,departure,"00:00:00","23:59:00");
//            q1schedule(source,departure);


            }catch(Exception e){
            System.out.println(e);
        }
    }

//    private void q2schedule(String source, String departure) {
//        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        List<String> timingsList = new ArrayList<>();
//
//        try {
//            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + TABLE_NAME +
//                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ?",
//                    new String[]{departure, source});
//            if (cursor.moveToFirst()) {
//                List<Timing> timings = new ArrayList<>();
//                int routeIndex = cursor.getColumnIndex(COLUMN_ROUTE);
//                int sourceTimeIndex = cursor.getColumnIndex(COLUMN_SOURCE_TIME);
//                int destinationTimeIndex = cursor.getColumnIndex(COLUMN_DESTINATION_TIME);
//                do {
//                    Timing timing = new Timing(cursor.getString(routeIndex),
//                            cursor.getString(sourceTimeIndex),
//                            cursor.getString(destinationTimeIndex));
//                    timings.add(timing);
//                } while (cursor.moveToNext());
//
//                // Sort timings by source time
//                Collections.sort(timings, new Comparator<Timing>() {
//                    @Override
//                    public int compare(Timing t1, Timing t2) {
//                        return t1.getSourceTime().compareTo(t2.getSourceTime());
//                    }
//                });
//
//                for (Timing timing : timings) {
//                    String timingString = "ROUTE: " + timing.getRoute() + "\n" +
//                            "SOURCE TIME: " + timing.getSourceTime() + "\n" +
//                            "DESTINATION TIME: " + timing.getDestinationTime();
//                    timingsList.add(timingString);
//                }
//            }
//            cursor.close();
//        } catch (SQLiteException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        if (!timingsList.isEmpty()) {
//            Intent intent = new Intent(HomeActivity.this, Schedule.class);
//            intent.putStringArrayListExtra("timingsList", (ArrayList<String>) timingsList);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "No schedule found for the selected source and destination.", Toast.LENGTH_SHORT).show();
//        }
//    }

    // Define a Timing class to hold route, source time, and destination time
    class Timing {
        private String route;
        private String sourceTime;
        private String destinationTime;

        public Timing(String route, String sourceTime, String destinationTime) {
            this.route = route;
            this.sourceTime = sourceTime;
            this.destinationTime = destinationTime;
        }

        public String getRoute() {
            return route;
        }

        public String getSourceTime() {
            return sourceTime;
        }

        public String getDestinationTime() {
            return destinationTime;
        }
    }
    private void    q3schedule(String source, String departure, String startTime, String endTime) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> timingsList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ?" +
                            " AND " + COLUMN_SOURCE_TIME + " BETWEEN ? AND ?",
                    new String[]{departure, source, startTime, endTime});
            if (cursor.moveToFirst()) {
                List<Timing> timings = new ArrayList<>();
                int routeIndex = cursor.getColumnIndex(COLUMN_ROUTE);
                int sourceTimeIndex = cursor.getColumnIndex(COLUMN_SOURCE_TIME);
                int destinationTimeIndex = cursor.getColumnIndex(COLUMN_DESTINATION_TIME);
                do {
                    Timing timing = new Timing(cursor.getString(routeIndex),
                            cursor.getString(sourceTimeIndex),
                            cursor.getString(destinationTimeIndex));
                    timings.add(timing);
                } while (cursor.moveToNext());

                // Sort timings by source time
                Collections.sort(timings, new Comparator<Timing>() {
                    @Override
                    public int compare(Timing t1, Timing t2) {
                        return t1.getSourceTime().compareTo(t2.getSourceTime());
                    }
                });

                for (Timing timing : timings) {
                    String timingString = "ROUTE: " + timing.getRoute() + "\n" +
                            "SOURCE TIME: " + timing.getSourceTime() + "\n" +
                            "DESTINATION TIME: " + timing.getDestinationTime();
                    timingsList.add(timingString);
                }
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
            Toast.makeText(this, "No schedule found for the selected source and destination within the specified time range.", Toast.LENGTH_SHORT).show();
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
