package com.mini.smartroad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mini.smartroad.client.ConnectionUtils;
import com.mini.smartroad.client.user.UserClientAgent;

import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        ConnectionUtils.prepareConnection(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public void startAgents() {
        ConnectionUtils.startAgent(UserClientAgent.class.getName(),
                UserClientAgent.class, getApplicationContext(), new Object[]{"test@gmail.com", "password"});
    }
}
