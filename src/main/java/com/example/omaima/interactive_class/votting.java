package com.example.omaima.interactive_class;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class votting extends AppCompatActivity {

    RadioButton R1;
    RadioButton R2;
    RadioButton R3;
    RadioButton R4;
    Button votting;
    RadioGroup Group ;
    RadioButton answer ;
    DatabaseReference reff=FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votting);

        R1=(RadioButton)findViewById(R.id.A);
        R2=(RadioButton)findViewById(R.id.B);
        R3=(RadioButton)findViewById(R.id.C);
        R4=(RadioButton)findViewById(R.id.D);
        final int select = Group.getCheckedRadioButtonId();
        votting=(Button) findViewById(R.id.votting);

    }
}
