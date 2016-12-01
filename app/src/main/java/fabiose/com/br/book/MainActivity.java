package fabiose.com.br.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import com.facebook.AccessTokenTracker;
import com.facebook.AccessToken;

public class MainActivity extends AppCompatActivity {

private LoginButton loginButton;
private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if(accessToken != null)
                    startActivity(new Intent(MainActivity.this,BookActivity.class));


            }
        };


        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {}

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

  }
