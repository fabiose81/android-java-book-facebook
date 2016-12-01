package br.com.fabiose.book.adapter;

/**
 * Created by fabioestrela on 30/11/16.
 */

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Color;

import br.com.fabiose.book.model.Book;
import fabiose.com.br.book.R;


import java.util.List;

public class BookAdpter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<Book> books;
    private Activity activity;
    private int selectedPos = -1;

    public BookAdpter(Activity activity,  List<Book> books) {
        this.activity = activity;
        this.books = books;
        this.mInflater = LayoutInflater.from(activity);
    }

    public void setSelectedPosition(int pos){
        selectedPos = pos;
        notifyDataSetChanged();
    }

    public void clear(){
        selectedPos = -1;
        notifyDataSetChanged();
    }

    public int getSelectedPosition(){
        return selectedPos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try{
            if(convertView == null)
                convertView = mInflater.inflate(R.layout.adapter_book, null);

            if (books.get(position) != null) {
                final TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
                textViewTitle.setText(books.get(position).getTitle());
                    if(selectedPos == position){
                        convertView.setBackgroundColor(Color.parseColor("#ff1ea0c1"));
                    }else{
                        convertView.setBackgroundColor(Color.TRANSPARENT);
                    }
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return books.size();
    }

}
