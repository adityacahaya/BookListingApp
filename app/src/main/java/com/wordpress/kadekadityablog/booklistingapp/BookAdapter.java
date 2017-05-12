package com.wordpress.kadekadityablog.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by I Kadek Aditya on 5/12/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> bookArrayList) {
        super(context, 0, bookArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book book = getItem(position);
        TextView author = (TextView)listItemView.findViewById(R.id.author);
        author.setText(book.getAuthor());
        TextView title = (TextView)listItemView.findViewById(R.id.title);
        title.setText(book.getTitle());
        return listItemView;
    }
}
