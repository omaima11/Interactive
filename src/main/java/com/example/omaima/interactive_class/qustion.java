package com.example.omaima.interactive_class;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.app.TabActivity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class qustion extends AppCompatActivity {

    EditText ques;
    Button send;
    ListView listView;
    //String[] listItem;
    // ListView listView2;
    Button share;

    FirebaseDatabase dp=FirebaseDatabase.getInstance();
    //FirebaseDatabase dp=FirebaseDatabase.getInstance("https://chat-d33ea.firebaseio.com/");
    DatabaseReference reff;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    // dataquestion q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qustion);

        //getJSON("");
        reff=dp.getReference("questions");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);

        ques = (EditText) findViewById(R.id.squestion);
        send = (Button) findViewById(R.id.send_sques);
        share = (Button) findViewById(R.id.share);
        listView = (ListView) findViewById(R.id.list_squestion);
        listView.setAdapter(arrayAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //senddata();
                if (ques.getText().toString().equals(""))
                {
                    Toast.makeText(qustion.this, "يرجى تعبئة الحقل",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String string=ques.getText().toString();
                    String id= reff.push().getKey();
                    reff.child(id).setValue(string);
                    Toast.makeText(qustion.this, "تم ارسال السؤال", Toast.LENGTH_SHORT).show();

                }

            }
        });
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string=dataSnapshot.getValue(String.class);
                arrayList.add(string);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*listItem = getResources().getStringArray(R.array.question);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItem);
        listView.setAdapter(arrayAdapter);*/
        //URL url="https://questionlab-be994.firebaseio.com/";
        /*reff = dp.getReference("dataquestion");reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                    maxid = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       /* listView2 = (ListView) findViewById(R.id.list_dquestion);
        listItem = getResources().getStringArray(R.array.ssquestion);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItem);
        listView2.setAdapter(arrayAdapter2);*/

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //senddata();
            }
        });

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("سؤالي");
        spec.setContent(R.id.tab1);
        spec.setIndicator("سؤالي");
        tabHost.addTab(spec);

        //Tab2
        spec = tabHost.newTabSpec("سؤال الدكتور");
        spec.setContent(R.id.tab2);
        spec.setIndicator("سؤال الدكتور");
        tabHost.addTab(spec);
    }

   /* public void senddata() {
        String data=ques.getText().toString();
        String id=reff.push().getKey();
        if(!TextUtils.isEmpty(data))
        {
            Dataqueston dataqueston=new Dataqueston(data,id);
            reff.child(id).setValue(dataqueston);
            Toast.makeText(qustion.this, "تم ارسال السؤال",
                    Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(qustion.this, "يرجى تعبئة الحقل",
                    Toast.LENGTH_SHORT).show();
        }
    }*/


    /*private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("questions");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        listView.setAdapter(arrayAdapter);
    }*/
}


