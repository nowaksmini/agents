package com.mini.smartroad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.gc.materialdesign.views.Button;
import com.mini.smartroad.client.ConnectionUtils;
import com.mini.smartroad.client.user.UserClientAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @InjectView(R.id.start_agents_btn)
    Button startAgentsBtn;
    @InjectView(R.id.port)
    EditText portEditTxt;
    @InjectView(R.id.host)
    EditText hostEditTxt;
    @InjectView(R.id.message_amount)
    EditText messageAmountEditTxt;
    @InjectView(R.id.message_length)
    EditText messageLengthEditTxt;
    @InjectView(R.id.agentId)
    EditText agentIdEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        ConnectionUtils.prepareConnection(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.start_agents_btn)
    public void startAgents() {
        ConnectionUtils.startAgent(UserClientAgent.class.getName(),
                UserClientAgent.class, getApplicationContext(), new Object[]{"test@gmail.com", "password"});
    }

}
