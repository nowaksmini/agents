package com.mini.smartroad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mini.smartroad.client.search.SearchStationsClientAgent;
import com.mini.smartroad.client.user.UserClientAgent;
import com.mini.smartroad.events.FailureEvent;
import com.mini.smartroad.events.FoundStationsEvent;
import com.mini.smartroad.events.UserEvent;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_container)
    FrameLayout loginFrameLayout;
    @BindView(R.id.register_container)
    FrameLayout registerFrameLayout;

    private EventBus bus = EventBus.getDefault();
    private final String SHARED_PREF_NAME = "smartroad";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        bus.register(this);
        ConnectionUtils.prepareConnection(getApplicationContext());
    }

    public void onEvent(UserEvent event) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(SearchActivity.USER_TOKEN, event.getUserToken());
        startActivity(intent);
    }

    public void onEvent(FailureEvent failureEvent) {
        Toast.makeText(this, failureEvent.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void saveCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(EMAIL, email).putString(PASSWORD, password).apply();
    }

    public void clearCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public String[] getCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return new String[]{sharedPreferences.getString(EMAIL, ""), sharedPreferences.getString(PASSWORD, "")};
    }

    public void switchView(View view) {
        if (view.getId() == R.id.link_login) {
            loginFrameLayout.setVisibility(View.VISIBLE);
            registerFrameLayout.setVisibility(View.GONE);
        } else {
            loginFrameLayout.setVisibility(View.GONE);
            registerFrameLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
