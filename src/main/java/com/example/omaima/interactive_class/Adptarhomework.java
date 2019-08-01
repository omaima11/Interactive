package com.example.omaima.interactive_class;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.v4.provider.DocumentFile;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Adptarhomework  extends RecyclerView.Adapter<Adptarhomework.ViewHolder> {
    RecyclerView recyclerView;
    Context context;
    ArrayList<String> items= new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();
    private PDFView pdfView;

    public void update(String fileName ,String url)
    {
        items.add(fileName);
        urls.add(url);
        notifyDataSetChanged();
    }

    public Adptarhomework(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.row_homework,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        DownloadManager downloadManager;
        public ViewHolder(final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rowhomework);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //int postion=recyclerView.getChildLayoutPosition(view);
                    //Intent intent=new Intent();
                    //intent.setType(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse(urls.get(postion)));
                    //context.startActivity(intent);

                    //String url = urls.get(position);
                    /*downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long referance = downloadManager.enqueue(request);*/
                    //Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setDataAndType(Uri.parse(urls.get(postion)),"application/msword");
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //context.startActivity(intent);
                    int pos = recyclerView.getChildLayoutPosition(view);
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW).setData(Uri.parse(urls.get(pos)));
                    intent.setDataAndType(Uri.parse("http://"+urls.get(pos)), Intent.ACTION_VIEW);
                    //context.startActivity(intent);
                    Toast.makeText(context, urls.get(pos), Toast.LENGTH_LONG).show();
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                    else{
                        //Toast.makeText(context, "ELSE PART BEING CALLED : context.getPackageManager() = NULL", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "DOCUMENT NOT ACKNOWLEDGED YET, PLS WAIT...", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

            } catch (IOException e) {

                return null;
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

}