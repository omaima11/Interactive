package com.example.omaima.interactive_class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class attendance extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView mList;
    private ArrayList<String> mAList = new ArrayList<>();
   // private Adptar_attendace adptar=new Adptar_attendace();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        //Adptar_attendace<String> adapter = new Adptar_attendace<String>(attendance.this, R.layout.row_attendace,mAList);

        ListView listView = (ListView) findViewById(R.id.attendance);
        //listView.setAdapter(adapter);
    }

    }