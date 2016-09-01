package com.mini.smartroad.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.mini.smartroad.common.Utils;

import jade.android.AndroidHelper;
import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public final class ConnectionUtils {
    private static final String TAG = ConnectionUtils.class.getName();
    public static MicroRuntimeServiceBinder microRuntimeServiceBinder;
    public static ServiceConnection serviceConnection;
    private static Properties properties;

    static {
        properties = new Properties();
        final String host = Utils.IP;
        final String port = Utils.PORT;
        properties.setProperty(Profile.MAIN_HOST, host);
        properties.setProperty(Profile.MAIN_PORT, port);
        properties.setProperty(Profile.MAIN, Boolean.FALSE.toString());
        properties.setProperty(Profile.JVM, Profile.ANDROID);
        properties.setProperty(Profile.LOCAL_HOST, AndroidHelper.getLocalIPAddress());
    }

    private static RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() {
        @Override
        public void onSuccess(AgentController agent) {
            try {
                Log.i(TAG, "AgentController started " + agent.getName());
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(TAG, "AgentController failure " + throwable.getMessage());
        }
    };

    public static void prepareConnection(Context context) {
        if (microRuntimeServiceBinder == null) {
            serviceConnection = new ServiceConnection() {
                public void onServiceConnected(ComponentName className,
                                               IBinder service) {
                    microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
                    Log.i(TAG, "Gateway successfully bound to MicroRuntimeService");
                    startContainer();
                }

                public void onServiceDisconnected(ComponentName className) {
                    microRuntimeServiceBinder = null;
                    Log.i(TAG, "Gateway unbound from MicroRuntimeService");
                }
            };
            Log.i(TAG, "Binding Gateway to MicroRuntimeService...");
            context.bindService(new Intent(context.getApplicationContext(), MicroRuntimeService.class), serviceConnection,
                    Context.BIND_AUTO_CREATE);
        } else {
            Log.i(TAG, "MicroRumtimeGateway already binded to service");
            startContainer();
        }
    }

    private static void startContainer() {
        if (!MicroRuntime.isRunning()) {
            microRuntimeServiceBinder.startAgentContainer(properties,
                    new RuntimeCallback<Void>() {
                        @Override
                        public void onSuccess(Void thisIsNull) {
                            Log.i(TAG, "Successfully start of the container...");
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e(TAG, "Failed to start the container...");
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    public static void startAgent(final String localName, final Class agentClass, Context context, Object[] params) {
        Object[] finalParams = new Object[params != null ? params.length + 1 : 1];
        finalParams[0] = context.getApplicationContext();
        if (params != null) {
            System.arraycopy(params, 0, finalParams, 1, params.length);
        }
        microRuntimeServiceBinder.startAgent(localName, agentClass.getName(),
                finalParams,
                new RuntimeCallback<Void>() {
                    @Override
                    public void onSuccess(Void thisIsNull) {
                        try {
                            agentStartupCallback.onSuccess(MicroRuntime.getAgent(localName));
                        } catch (ControllerException e) {
                            e.printStackTrace();
                            agentStartupCallback.onFailure(e.fillInStackTrace());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        agentStartupCallback.onFailure(throwable);
                    }
                });
    }
}
