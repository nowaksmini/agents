package com.mini.smartroad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.mini.smartroad.client.user.UserClientAgent;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_container)
    FrameLayout loginFrameLayout;
    @BindView(R.id.register_container)
    FrameLayout registerFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        ConnectionUtils.prepareConnection(getApplicationContext());
    }

    public void startAgents() {
        ConnectionUtils.startAgent(UserClientAgent.class.getName() + UUID.randomUUID(),
                UserClientAgent.class, getApplicationContext(), new Object[]{"test@gmail.com", "password"});
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
}
