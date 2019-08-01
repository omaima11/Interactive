package com.example.omaima.interactive_class;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.DSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    RadioGroup group;
    private AutoCompleteTextView emailuser;
    private EditText mPasswordView;
    Button login;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fd;
    private FirebaseAuth.AuthStateListener ml;
    private DatabaseReference dr;
    private DatabaseReference dr1;
    private String userID;
    private ProgressBar progressBar;
    //public String em;
    //public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        group = (RadioGroup) findViewById(R.id.users);
        emailuser = (AutoCompleteTextView) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        fd=FirebaseDatabase.getInstance("https://interactiveclass-c5d1d.firebaseio.com");
        dr=fd.getReference().child("Students");
        dr1=fd.getReference().child("Lecturer");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        userID=user.getUid();
        // Set up the login form.
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        //   populateAutoComplete();
        final String user = emailuser.getText().toString();
        final String pass = mPasswordView.getText().toString();
        final int select = group.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please enter email and enter password ...", Toast.LENGTH_LONG).show();
            return;}
        else if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        else if (select <= -1)
        {
            Toast.makeText(getApplicationContext(), "enter Student or Doctor!", Toast.LENGTH_LONG).show();
        }
        else if(select==R.id.lecturer)
        {
            Toast.makeText(getApplicationContext(), "Lecturer", Toast.LENGTH_LONG).show();
        }
        else  {
            mAuth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            //if(pass==dr.child(userID)){
                            if (task.isSuccessful()) {
                                dr.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                       // if (dataSnapshot.getChildren().getClass().getName() != null) {
                                            //for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                Informatoin_std std = new Informatoin_std();
                                                std.setName(dataSnapshot.child(userID).getValue(Informatoin_std.class).getName());
                                                std.setEmail(dataSnapshot.child(userID).getValue(Informatoin_std.class).getEmail());
                                                std.setAttendace(dataSnapshot.child(userID).getValue(Informatoin_std.class).getAttendace());
                                                String em = std.getEmail();
                                                String name = std.getName();
                                                Toast.makeText(getApplicationContext(), "أهلا :" + name, Toast.LENGTH_LONG).show();

                                           // }
                                            Intent intent1 = new Intent(getApplicationContext(), IC.class);
                                            startActivity(intent1);
                                        }
                                       // else Toast.makeText(getApplicationContext(), "تأكد من عملية الادخال" , Toast.LENGTH_LONG).show();

                                        /*if (user ==dataSnapshot.child(userID).getValue(Informatoin_std.class).getEmail() ) {
                                        Intent intent1 = new Intent(getApplicationContext(), IC.class);
                                        startActivity(intent1);}
                                        else Toast.makeText(getApplicationContext(), "تأكد من عملية الادخال" , Toast.LENGTH_LONG).show();
                                        */

                                    //}

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "تسجيل الدخول خاطئة, يرجى المحاولة مرة اخرى", Toast.LENGTH_LONG).show();
                                 }
                        }

                    });
        }
    }
}