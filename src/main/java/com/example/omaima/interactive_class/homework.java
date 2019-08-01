package com.example.omaima.interactive_class;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class homework extends AppCompatActivity {

    TextView home;
    Button sendhome;
    Uri pdfUri;
    FirebaseDatabase db;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Lecturer_homework");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot, String s) {
                String fileName=dataSnapshot.getKey();
                String url=dataSnapshot.getValue(String.class);
                ((Adptarhomework)recyclerView.getAdapter()).update(fileName,url);
            }

            @Override
            public void onChildChanged( DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved( DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot,String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.list_homework);
        recyclerView.setLayoutManager(new LinearLayoutManager(homework.this));
        Adptarhomework adptarhomework=new Adptarhomework(recyclerView,homework.this,new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(adptarhomework);

        sendhome=(Button) findViewById(R.id.send_home);
        home= (TextView) findViewById(R.id.homework);
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        home.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                    if(ContextCompat.checkSelfPermission(homework.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    {
                        selectPdf();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(homework.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 8);
                    }
                }
        });

        sendhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(homework.this,"اختر الملف",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadFile(final Uri pdfUri)
    {
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("تحميل الملف ...");
        progressDialog.setProgress(0);
        progressDialog.show();
        //setting string variables to be used at different places
        final String pdfname = home.getText().toString()+".docx";
        final String pdfname1 = home.getText().toString()+System.currentTimeMillis()+"";
        final StorageReference reference= storage.getReference();
        reference.child("homework").child("lecturer homework").child(pdfname).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = reference.getDownloadUrl().toString();
                        DatabaseReference reference1= db.getReference();
                        reference1.child("Lecturer_homework").child(pdfname1).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "تم الارسال بنجاح ...", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "فشل في الارسال ...", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "لم يتم الارسال ...", Toast.LENGTH_LONG).show();


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int currentProgress = (int)(100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "PLEASE ENABLE PERMISSIONS, ignore: if done", Toast.LENGTH_LONG).show();
        }
    }

    private void selectPdf() {
        //user will be choosing the file through FILE_MANAGER using implicit Intent
        Intent intent = new Intent();
        intent.setType("application/msword");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 78);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==78 && resultCode==RESULT_OK && data!=null){
            //we will be fetching URI for our selected file
            pdfUri= data.getData();//-> returns uri of selected file
            home.setText(data.getData().getLastPathSegment());
            //home.setText(new StringBuilder().append(data.getData().getLastPathSegment()).toString());
        }
        else{
            Toast.makeText(getApplicationContext(), "اختر الملف", Toast.LENGTH_LONG).show();
        }
    }
}
