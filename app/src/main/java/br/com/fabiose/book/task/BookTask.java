package br.com.fabiose.book.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import br.com.fabiose.book.enums.ActionEnum;
import br.com.fabiose.book.model.Book;
import fabiose.com.br.book.BookActivity;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Created by fabioestrela on 30/11/16.
 */

public class BookTask extends AsyncTask<Book, Void, List<Book>>{

    private BookActivity bookActivity;
    private ActionEnum actionEnum;

    public BookTask(BookActivity bookActivity, ActionEnum actionEnum){
        this.bookActivity = bookActivity;
        this.actionEnum = actionEnum;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Book> doInBackground(Book... params) {
        try {
            String url = null;
            List<Book> books = null;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());


            switch (actionEnum){
                case L:{
                    url = "http://10.0.2.2:3000/getBooks";
                    books = Arrays.asList(restTemplate.getForObject(url, Book[].class));
                    break;
                }
                case A:{
                    url = "http://10.0.2.2:3000/addBook";
                    Book book = params[0];
                    book = restTemplate.postForEntity(url, book, Book.class).getBody();
                    books = new ArrayList<Book>();
                    books.add(book);
                    break;
                }
                case D:{
                    url = "http://10.0.2.2:3000/deleteBook";
                    Book book = params[0];
                    restTemplate.postForEntity(url, book, Book.class);
                    books = new ArrayList<Book>();
                    books.add(book);
                    break;
                }
            }

            return books;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        switch (actionEnum){
            case L:{
                bookActivity.resultGetBooks(books);
                break;
            }
            case A:{
                bookActivity.resultAddBooks(books);
                break;
            }
            case D:{
                bookActivity.resultDeleteBooks(books);
                break;
            }
        }
    }
}


