package com.wordpress.kadekadityablog.booklistingapp;

/**
 * Created by I Kadek Aditya on 5/12/2017.
 */

public class Book {

    private String title;
    private String author;
    private String url;

    public Book(String title, String author, String url){
        this.title = title;
        this.author = author;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() { return url; }
}
