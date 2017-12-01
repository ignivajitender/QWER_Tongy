package com.igniva.qwer.controller;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.igniva.qwer.model.ClientProfile;
import com.igniva.qwer.utils.Constants;
import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;

 /**
 * Created by igniva-android-17 on 28/9/17.
 */

public class TwilioVoiceCallRecieveService extends Service implements ConnectionListener, DeviceListener {

    private static final String TAG = "TwilioVoiceClient";

    private static final int MIC_PERMISSION_REQUEST_CODE = 1;
    private static final String TOKEN_SERVICE_URL = "TOKEN_SERVICE_URL";

    private Device clientDevice;

    private Connection activeConnection;
    private Connection pendingConnection;

    private AudioManager audioManager;
    private int savedAudioMode = AudioManager.MODE_INVALID;

    private boolean isDestroyed;

    private String strClientName;
    private String strClientToken;
    private ClientProfile clientProfile;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: INTENT CALL");
        if (intent != null && intent.hasExtra(Constants.TWILIO_INCOMMING)) {

//             * Determine if the receiving Intent has an extra for the incoming connection. If so,
//             * remove it from the Intent to prevent handling it again next time the Activity is resumed

            Device device = intent.getParcelableExtra(Device.EXTRA_DEVICE);
            Connection incomingConnection = intent.getParcelableExtra(Device.EXTRA_CONNECTION);

            Log.d(TAG, "createDevice: "+device);
            Log.d(TAG, "createDevice: "+incomingConnection);
            if (incomingConnection == null && device == null) {
                return START_NOT_STICKY;
            }
            intent.removeExtra(Device.EXTRA_DEVICE);
            intent.removeExtra(Device.EXTRA_CONNECTION);

            pendingConnection = incomingConnection;
            pendingConnection.setConnectionListener(this);

            Log.d(TAG, "onStartCommand: CALL INIT");
        }
        if(intent.hasExtra(Constants.TWILIO_SENDER_NAME)) {
            Log.d(TAG, "TWILIO_SENDER_NAME: " + intent.getStringExtra(Constants.TWILIO_SENDER_NAME));
            Log.d(TAG, "TWILIO_TOKEN: " + intent.getStringExtra(Constants.TWILIO_TOKEN));
            strClientName = intent.getStringExtra(Constants.TWILIO_SENDER_NAME);
            strClientToken = intent.getStringExtra(Constants.TWILIO_TOKEN);
        }

        clientProfile = new ClientProfile(strClientName, true, true);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            /*
             * Initialize the Twilio Client SDK
             */
            initializeTwilioClientSDK();


        return START_NOT_STICKY;
    }

    private void initializeTwilioClientSDK() {

        if (!Twilio.isInitialized()) {
            Log.d(TAG, "initializeTwilioClientSDK: IF");
            Twilio.initialize(getApplicationContext(), new Twilio.InitListener() {
                @Override
                public void onInitialized() {
                    Twilio.setLogLevel(Log.DEBUG);
                    retrieveCapabilityToken(clientProfile);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, e.toString());
                    Log.e(TAG, "Failed to initialize the Twilio Client SDK");
                }
            });
        }else {
            Log.d(TAG, "initializeTwilioClientSDK: ELSE");
            retrieveCapabilityToken(clientProfile);
        }
    }

    private void createDevice(String capabilityToken) {
        try {
            if (clientDevice == null) {
                clientDevice = Twilio.createDevice(capabilityToken, TwilioVoiceCallRecieveService.this);

                Intent intent = new Intent(getApplicationContext(), TwilioVoiceCallRecieveService.class);
                intent.putExtra(Constants.TWILIO_TOKEN, strClientToken);
                intent.putExtra(Constants.TWILIO_SENDER_NAME, strClientName);
                intent.putExtra(Constants.TWILIO_INCOMMING, 1);
                PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                clientDevice.setIncomingIntent(pendingIntent);
            } else {
                clientDevice.updateCapabilityToken(capabilityToken);
            }

        } catch (Exception e) {
            Log.e(TAG, "An error has occured updating or creating a Device: \n" + e.toString());
        }
    }

    private void retrieveCapabilityToken(final ClientProfile newClientProfile) {


        clientProfile = newClientProfile;

        createDevice(strClientToken);//maraj

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnecting(Connection connection) {
        Log.d(TAG, "onConnecting: ");
    }

    @Override
    public void onConnected(Connection connection) {
        Log.d(TAG, "onConnected: ");
    }

    @Override
    public void onDisconnected(Connection connection) {
        Log.d(TAG, "onDisconnected: ");
    }

    @Override
    public void onDisconnected(Connection connection, int i, String s) {
        Log.d(TAG, "onDisconnected: ");
    }

    @Override
    public void onStartListening(Device device) {
        Log.d(TAG, "Device has started listening for incoming connections");
    }

    @Override
    public void onStopListening(Device device) {
        Log.d(TAG, "Device has stopped listening for incoming connections");
    }

    @Override
    public void onStopListening(Device device, int i, String s) {
        Log.e(TAG, String.format("Device has encountered an error and has stopped" +
                " listening for incoming connections: %s"));
    }

    @Override
    public boolean receivePresenceEvents(Device device) {
        Log.d(TAG, "receivePresenceEvents: ");
        return false;
    }

    @Override
    public void onPresenceChanged(Device device, PresenceEvent presenceEvent) {
        Log.d(TAG, "onPresenceChanged: ");
    }
}
