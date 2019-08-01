package com.example.omaima.interactive_class;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class IC extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private int REQUEST_INTERNET = 123;

    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab1
        TabHost.TabSpec spec= tabHost.newTabSpec("الدرس");
        spec.setContent(R.id.tab1);
        spec.setIndicator("الدرس");
        tabHost.addTab(spec);

        //Tab2
        spec=tabHost.newTabSpec("عرض المادة المشروحة");
        spec.setContent(R.id.tab2);
        spec.setIndicator("عرض المادة المشروحة");
        tabHost.addTab(spec);

        //Tab3
        spec=tabHost.newTabSpec("اشعارات");
        spec.setContent(R.id.tab3);
        spec.setIndicator("اشعارات");
        tabHost.addTab(spec);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ic, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =new Intent(this,setting.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       // Fragment fragment = null;


        if (id == R.id.nav_record) {
            Intent intent =new Intent(this,record.class);
            startActivity(intent);
            return true;

            // Handle the camera action
        } else if (id == R.id.nav_lab) {
            /*fragment = new lab();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment ,fragment);
            transaction.commit();*/

             Intent intent =new Intent(this,lab.class);
             startActivity(intent);
             return true;
        } else if (id == R.id.nav_aspent) {
            Intent intent =new Intent(this,attendance.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_question) {
            Intent intent =new Intent(this,qustion.class);
            startActivity(intent);
            return true;

           }

        else if (id == R.id.nav_chat) {
            Intent intent =new Intent(this,chat.class);
            startActivity(intent);
            return true;

        }else if (id == R.id.nav_homework) {
            Intent intent =new Intent(this,homework.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_votting) {
            Intent intent =new Intent(this,votting.class);
            startActivity(intent);
            return true;

        }
        else if (id == R.id.nav_hand) {
            Intent intent =new Intent(this,hand.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.nav_exam) {
            Intent intent =new Intent(this,exam.class);
            startActivity(intent);
            return true;
        }

        //myHandler.postAtFrontOfQueue(mThread);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


  /*  class MyCust extends BaseAdapter
    {
        ArrayList<record> item= new ArrayList<record>();
        MyCust(ArrayList<record> item)
            {
                this.item=item;
            }


        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater linflater=getLayoutInflater();
            View view1=linflater.inflate(R.layout.activity_record,null);

           // ImageButton imageButton=(ImageButton)view1.findViewById(R.id.img);
            TextView textView= (TextView) view1.findViewById(R.id.textView1);
            textView.setText(item.get(i).name);
            return view1;
        }
    }
*/


}