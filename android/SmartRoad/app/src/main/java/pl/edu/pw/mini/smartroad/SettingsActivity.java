package pl.edu.pw.mini.smartroad;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;

import java.security.SecureRandom;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jade.android.AndroidHelper;
import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import pl.edu.pw.mini.smartroad.mca.MessageConsumingAgent;
import pl.edu.pw.mini.smartroad.sa.SpammerAgent;

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

    private MicroRuntimeServiceBinder microRuntimeServiceBinder;
    private ServiceConnection serviceConnection;

    private RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() {
        @Override
        public void onSuccess(AgentController agent) {
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(TAG, "Nickname already in use!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.start_agents_btn)
    public void startAgents() {
        Utils.ID = agentIdEditTxt.getText().toString();
        Utils.numberOfMessages = Integer.parseInt(messageAmountEditTxt.getText().toString());
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        Integer len = Integer.parseInt(messageLengthEditTxt.getText().toString());
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        Utils.message = sb.toString();
        Toast.makeText(this, Utils.message, Toast.LENGTH_LONG).show();
        startAgentsBtn.setVisibility(View.GONE);

        final Properties profile = new Properties();
        final String host = hostEditTxt.getText().toString();
        final String port = portEditTxt.getText().toString();
        profile.setProperty(Profile.MAIN_HOST, host);
        profile.setProperty(Profile.MAIN_PORT, port);
        profile.setProperty(Profile.MAIN, Boolean.FALSE.toString());
        profile.setProperty(Profile.JVM, Profile.ANDROID);
        profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.getLocalIPAddress());

        // Emulator: this is not really needed on a real device
//        profile.setProperty(Profile.LOCAL_PORT, "2000");

        if (microRuntimeServiceBinder == null) {
            serviceConnection = new ServiceConnection() {
                public void onServiceConnected(ComponentName className,
                                               IBinder service) {
                    microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
                    Log.i(TAG, "Gateway successfully bound to MicroRuntimeService");
                    startContainer(profile, agentStartupCallback);
                }

                public void onServiceDisconnected(ComponentName className) {
                    microRuntimeServiceBinder = null;
                    Log.i(TAG, "Gateway unbound from MicroRuntimeService");
                }
            };
            Log.i(TAG, "Binding Gateway to MicroRuntimeService...");
            bindService(new Intent(getApplicationContext(), MicroRuntimeService.class), serviceConnection,
                    Context.BIND_AUTO_CREATE);
        } else {
            Log.i(TAG, "MicroRumtimeGateway already binded to service");
            startContainer(profile, agentStartupCallback);
        }
    }

    private void startContainer(Properties profile,
                                final RuntimeCallback<AgentController> agentStartupCallback) {
        if (!MicroRuntime.isRunning()) {
            microRuntimeServiceBinder.startAgentContainer(profile,
                    new RuntimeCallback<Void>() {
                        @Override
                        public void onSuccess(Void thisIsNull) {
                            Log.i(TAG, "Successfully start of the container...");
                            startAgentSpammer("sa" + Utils.ID, SpammerAgent.class, agentStartupCallback);
                            startAgentSpammer("mca" + Utils.ID, MessageConsumingAgent.class, agentStartupCallback);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e(TAG, "Failed to start the container...");
                            throwable.printStackTrace();
                        }
                    });
        } else {
            startAgentSpammer("sa" + Utils.ID, SpammerAgent.class, agentStartupCallback);
            startAgentSpammer("mca" + Utils.ID, MessageConsumingAgent.class, agentStartupCallback);
        }
    }

    private void startAgentSpammer(final String nickname, final Class agentClass,
                                   final RuntimeCallback<AgentController> agentStartupCallback) {
        microRuntimeServiceBinder.startAgent(nickname, agentClass.getName(),
                new Object[]{getApplicationContext()},
                new RuntimeCallback<Void>() {
                    @Override
                    public void onSuccess(Void thisIsNull) {
                        Log.i(TAG, "Successfully start of the " + agentClass.getName() + "...");
                        try {
                            agentStartupCallback.onSuccess(MicroRuntime.getAgent(nickname));
                        } catch (ControllerException e) {
                            // Should never happen
                            agentStartupCallback.onFailure(e);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "Failed to start the " + agentClass.getName() + "...");
                        agentStartupCallback.onFailure(throwable);
                    }
                });
    }

}
