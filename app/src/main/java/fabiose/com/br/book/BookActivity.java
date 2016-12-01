package fabiose.com.br.book;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.List;


import br.com.fabiose.book.enums.ActionEnum;
import br.com.fabiose.book.model.Book;
import br.com.fabiose.book.adapter.BookAdpter;
import br.com.fabiose.book.task.BookTask;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by fabioestrela on 30/11/16.
 */

public class BookActivity extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ListView listViewBook;
    private BookAdpter bookAdapter;
    private ImageButton imageButtonTrash;
    private List<Book> books;
    private int indexList = -1;
    private boolean flagBackActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_book);

        new BookTask(this, ActionEnum.L).execute();

        final EditText editTextTitle = (EditText)findViewById(R.id.editTextTitle);

        final ImageButton imageButtonAdd = (ImageButton)findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setTitle(editTextTitle.getText().toString());

                if(book.getTitle() != null && !book.getTitle().trim().equalsIgnoreCase(""))
                    new BookTask(BookActivity.this, ActionEnum.A).execute(book);
                else
                    Toast.makeText(BookActivity.this,"Please fill title field!",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonTrash = (ImageButton)findViewById(R.id.imageButtonTrash);
        imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = books.get(indexList);
                new BookTask(BookActivity.this, ActionEnum.D).execute(book);
            }
        });

        listViewBook = (ListView)findViewById(R.id.listViewBook);
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(indexList == position) {
                    imageButtonTrash.setVisibility(View.INVISIBLE);
                    indexList = -1;
                    bookAdapter.setSelectedPosition(-1);
                }else {
                    imageButtonTrash.setVisibility(View.VISIBLE);
                    indexList = position;
                    bookAdapter.setSelectedPosition(position);
                }
            }
        });

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if(accessToken == null){
                    flagBackActivity = true;
                    BookActivity.super.onBackPressed();
                }
            }
        };
    }

    public void resultGetBooks(List<Book> books) {
        this.books = new ArrayList<>(books);
        bookAdapter = new BookAdpter(this, this.books);
        listViewBook.setAdapter(bookAdapter);
    }

    public void resultAddBooks(List<Book> books) {
        this.books.addAll(books);
        bookAdapter = new BookAdpter(this, this.books);
        listViewBook.setAdapter(bookAdapter);
    }

    public void resultDeleteBooks(List<Book> books) {
        this.books.remove(indexList);
        indexList = -1;
        bookAdapter.clear();
        imageButtonTrash.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onBackPressed() {
      if(flagBackActivity)
          super.onBackPressed();
    }
}
