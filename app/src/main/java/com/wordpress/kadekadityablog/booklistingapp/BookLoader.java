package com.wordpress.kadekadityablog.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by I Kadek Aditya on 5/12/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("BookListingApp","onStartLoading() di panggil");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        Log.i("BookListingApp","loadInBackground() di panggil");
        if (url == null) {
            return null;
        }
        List<Book> bookList = QueryUtils.fetchBookData(url);
        return bookList;
    }
}
