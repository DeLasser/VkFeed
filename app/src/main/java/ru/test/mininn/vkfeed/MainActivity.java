package ru.test.mininn.vkfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.test.mininn.vkfeed.login.LoginFragment;
import ru.test.mininn.vkfeed.wall.fragment.FeedFragment;

public class MainActivity extends AppCompatActivity {

    private final static String FRAGMENT_TAG_LOGIN = "login";
    private final static String FRAGMENT_TAG_FEED = "feed";

    private MenuItem logoutMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoginState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        logoutMenuItem = menu.findItem(R.id.logout);
        setLogoutMenuItemVisability();
        return true;
    }

    private void setLogoutMenuItemVisability() {
        Log.d("asdasd" ,getSupportFragmentManager().getFragments().size() + "");
        if (getSupportFragmentManager().getFragments().size() == 0 ) {
            return;
        }
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_LOGIN) != null) {
            logoutMenuItem.setVisible(false);
        } else {
            logoutMenuItem.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout: {
                logout();
            }
        }
        return true;
    }

    private void logout() {
        VKSdk.logout();
        startLoginFragment();
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
                .replace(R.id.container, new FeedFragment(), FRAGMENT_TAG_FEED)
                .commit();
        invalidateOptionsMenu();
    }

    private void startLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment(), FRAGMENT_TAG_LOGIN)
                .commit();
        invalidateOptionsMenu();
    }
}
