package com.wordpress.kadekadityablog.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=20";
    //volumes?q=android&maxResults=10
    private BookAdapter bookAdapter;
    private boolean connected = false;
    private int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("BookListingApp","onCreate() di panggil");
        Log.i("BookListingApp",BOOK_LOADER_ID+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView)findViewById(R.id.list);
        bookListView.setEmptyView(findViewById(R.id.tv_loadMessage));
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else {
            connected = false;
        }
        if (!connected){
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progres_bar);
            progressBar.setVisibility(GONE);
            TextView textView = (TextView)findViewById(R.id.tv_loadMessage);
            textView.setText("Not network found");
        }else{
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, MainActivity.this);
            Log.i("BookListingApp", "loaderManager.initLoader() di panggil");
        }

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book currentBook = bookAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentBook.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });

        Button btnSearch = (Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.ed_search);
                String query = editText.getText().toString();
                REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q="+query+"&maxResults=20";
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                }
                else {
                    connected = false;
                }
                if (!connected){
                    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progres_bar);
                    progressBar.setVisibility(GONE);
                    ListView bookListView = (ListView)findViewById(R.id.list);
                    bookListView.setVisibility(GONE);
                    TextView textView = (TextView)findViewById(R.id.tv_loadMessage);
                    textView.setText("Not network found");
                    textView.setVisibility(View.VISIBLE);
                }else{
                    TextView textView = (TextView)findViewById(R.id.tv_loadMessage);
                    textView.setVisibility(View.GONE);
                    ListView bookListView = (ListView)findViewById(R.id.list);
                    bookListView.setVisibility(GONE);
                    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progres_bar);
                    progressBar.setVisibility(View.VISIBLE);
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.restartLoader(1, null, MainActivity.this);
                    bookListView = (ListView)findViewById(R.id.list);
                    bookListView.setVisibility(View.VISIBLE);
                    Log.i("BookListingApp", "loaderManager.initLoader() di panggil");
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i("BookListingApp","onCreateLoader() di panggil");
        return new BookLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        Log.i("BookListingApp","onLoadFinished() di panggil");
        bookAdapter.clear();
        if (bookList != null && !bookList.isEmpty()) {
            bookAdapter.addAll(bookList);
        }
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progres_bar);
        progressBar.setVisibility(GONE);
        TextView textView = (TextView)findViewById(R.id.tv_loadMessage);
        textView.setText("Book not found");
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i("BookListingApp","onLoaderReset() di panggil");
        bookAdapter.clear();
    }
}
