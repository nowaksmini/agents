package com.mini.smartroad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_container)
    FrameLayout loginFrameLayout;

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

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
