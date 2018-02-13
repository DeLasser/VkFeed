package ru.test.mininn.vkfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;

import ru.test.mininn.vkfeed.login.LoginFragment;
import ru.test.mininn.vkfeed.wall.FeedFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoginState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                startFeedFragment();
            }

            @Override
            public void onError(VKError error) {
                showErrorDialog(error);
            }
        });
    }


    private void checkLoginState() {
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState loginState) {
                if (loginState != null) {
                    switch (loginState) {
                        case LoggedOut:
                            startLoginFragment();
                            break;
                        case LoggedIn:
                            startFeedFragment();
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {
                showErrorDialog(error);
            }
        });
    }

    private void showErrorDialog(VKError error) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
        dialogBuilder.setTitle(R.string.error)
                .setMessage(error.errorMessage)
                .create()
                .show();
    }

    private void startFeedFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new FeedFragment())
                .commit();
    }

    private void startLoginFragment () {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commit();
    }
}
