package com.igniva.qwer.ui.activities.voice;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.callBack.DeclinCallback;
import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;

import java.util.HashMap;
import java.util.Map;

public class TwilioVoiceClientActivity extends AppCompatActivity implements DeviceListener, ConnectionListener, DeclinCallback {

    private static final String TAG = "TwilioVoiceClient";
    private static final int MIC_PERMISSION_REQUEST_CODE = 1;
    /*
     * You must provide a publicly accessible server to generate a Capability Token to connect to the Client service
     * Refer to website documentation for additional details: https://www.twilio.com/docs/quickstart/php/android-client
     */
    private static final String TOKEN_SERVICE_URL = "TOKEN_SERVICE_URL";
    Ringtone ringTone;
    Uri uriRingtone;
    long pattern[] = {0, 200, 100, 300, 400};
    TextView tvTitle;
    //for showing the connect event
    private boolean isCallButtonPressed = false;
    private boolean isCallStarted = false;
    /*
     * A Device is the primary entry point to Twilio Services
     */
    private Device clientDevice;
    /*
     * A Connection represents a connection between a Device and Twilio Services.
     * Connections are either outgoing or incoming, and not created directly.
     * An outgoing connection is created by Device.connect()
     * An incoming connection are created internally by a Device and hanged to the registered PendingIntent
     */
    private Connection activeConnection;
    private Connection pendingConnection;
    private AudioManager audioManager;
    private int savedAudioMode = AudioManager.MODE_INVALID;
    /*
     * Android application UI elements
     */
    private FloatingActionButton callActionFab;
    private FloatingActionButton muteActionFab;
    private FloatingActionButton speakerActionFab;
    private FloatingActionButton hangupActionFab;
    private FloatingActionButton acceptActionFab;
    private ClientProfile clientProfile;
    private AlertDialog alertDialog;
    private Chronometer chronometer;
    private View callView;
    private View capabilityPropertiesView;
    private boolean muteMicrophone;
    private boolean speakerPhone;
    private String strClientReceaverName = "null";
    private String strClientName = "null";
    private String strClientToken = "null";
    private TextView tvConnecting;
    private ImageView imgReceaverImage;
    private Vibrator vib;
     @Override
    public void videoCallDecline(boolean result) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twilio_voice_client);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


//        if(getIntent().hasExtra(Constants.TWILIO_CALLER_ID)){
//            AllFollowersTwilioFragment.callerId = getIntent().getStringExtra(Constants.TWILIO_CALLER_ID);
//        }
//
//        if(getIntent().hasExtra(Constants.TWILIO_SENDER_ID)){
//            JSONArray jsonArray = new JSONArray();
//            try {
//                jsonArray.put(new JSONObject().put("id", getIntent().getStringExtra(Constants.TWILIO_SENDER_ID)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            AllFollowersTwilioFragment.arrayData = jsonArray;
//        }
        callView = (View) findViewById(R.id.call_layout);
        capabilityPropertiesView = (View) findViewById(R.id.capability_properties);
        imgReceaverImage = (ImageView) findViewById(R.id.iv_user_image);
        tvConnecting = (TextView) findViewById(R.id.tv_connecting);
        callActionFab = (FloatingActionButton) findViewById(R.id.call_action_fab);
        hangupActionFab = (FloatingActionButton) findViewById(R.id.hangup_action_fab);
        acceptActionFab = (FloatingActionButton) findViewById(R.id.accept_action_fab);
        muteActionFab = (FloatingActionButton) findViewById(R.id.mute_action_fab);
        speakerActionFab = (FloatingActionButton) findViewById(R.id.speaker_action_fab);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        tvTitle = (TextView) findViewById(R.id.client_name_registered_text);
         if (getIntent().hasExtra(Constants.TWILIO_INCOMMING)) {
            tvTitle.setText(getIntent().getStringExtra(Constants.TWILIO_SENDER_NAME));
//            Log.d(TAG, "onCreate: "+getIntent().getStringExtra(Constants.SKIPROOM_TITLE));
        } else {
            tvTitle.setText(getIntent().getStringExtra(Constants.TWILIO_RECEAVER_NAME));
        }
        if(getIntent().hasExtra(Constants.TWILIO_RECEAVER_IMAGE)) {
             Glide.with(this)
                    .load(getIntent().getStringExtra(Constants.TWILIO_RECEAVER_IMAGE))
                     .into(imgReceaverImage);
            Log.d(TAG, "onCreate: "+getIntent().getStringExtra(Constants.TWILIO_RECEAVER_IMAGE));
        }

        if (getIntent().hasExtra(Constants.TWILIO_SENDER_NAME)) {
            Log.d(TAG, "TWILIO_SENDER_NAME: " + getIntent().getStringExtra(Constants.TWILIO_SENDER_NAME));
            Log.d(TAG, "TWILIO_TOKEN: " + getIntent().getStringExtra(Constants.TWILIO_TOKEN));
            strClientName = getIntent().getStringExtra(Constants.TWILIO_SENDER_NAME);
            strClientReceaverName = getIntent().getStringExtra(Constants.TWILIO_RECEAVER_NAME);
            strClientToken = getIntent().getStringExtra(Constants.TWILIO_TOKEN);
        }
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringTone = RingtoneManager
                .getRingtone(getApplicationContext(), uriRingtone);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (getIntent().hasExtra(Constants.TWILIO_INCOMMING)) {
            ringTone.play();
            vib.vibrate(pattern, 0);
            acceptActionFab.show();
        } else {
            acceptActionFab.hide();
        }


        /*
         * Create a default profile (name=jenny, allowOutgoing=true, allowIncoming=true)
         */
        clientProfile = new ClientProfile(strClientName, true, true);
//        clientProfile = new ClientProfile("anshul", true, true);

        /*
         * Needed for setting/abandoning audio focus during call
         */
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /*
         * Check microphone permissions. Needed in Android M.
         */
        if (!checkPermissionForMicrophone()) {
            requestPermissionForMicrophone();
        } else {
            /*
             * Initialize the Twilio Client SDK
             */
            initializeTwilioClientSDK();
        }

        /*
         * Set the initial state of the UI
         */
        setCallAction();
    }

    /*
     * Initialize the Twilio Client SDK
     */
    private void initializeTwilioClientSDK() {

        if (!Twilio.isInitialized()) {
            Twilio.initialize(getApplicationContext(), new Twilio.InitListener() {

                /*
                 * Now that the SDK is initialized we can register using a Capability Token.
                 * A Capability Token is a JSON Web Token (JWT) that specifies how an associated Device
                 * can interact with Twilio services.
                 */
                @Override
                public void onInitialized() {
                    Twilio.setLogLevel(Log.DEBUG);
                    /*
                     * Retrieve the Capability Token from your own web server
                     */
                    retrieveCapabilityToken(clientProfile);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, e.toString());
                    Toast.makeText(TwilioVoiceClientActivity.this, "Failed to initialize the Twilio Client SDK", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            retrieveCapabilityToken(clientProfile);
        }
    }

    /*
     * Create a Device or update the capabilities of the current Device
     */
    private void createDevice(String capabilityToken) {
        try {
            if (clientDevice == null) {
                clientDevice = Twilio.createDevice(capabilityToken, this);
                clientDevice.setIncomingSoundEnabled(false);
                /*
                 * Providing a PendingIntent to the newly created Device, allowing you to receive incoming calls
                 *
                 *  What you do when you receive the intent depends on the component you set in the Intent.
                 *
                 *  If you're using an Activity, you'll want to override Activity.onNewIntent()
                 *  If you're using a Service, you'll want to override Service.onStartCommand().
                 *  If you're using a BroadcastReceiver, override BroadcastReceiver.onReceive().
                 */

                Intent intent = new Intent(getApplicationContext(), TwilioVoiceClientActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                clientDevice.setIncomingIntent(pendingIntent);
            } else {
                clientDevice.updateCapabilityToken(capabilityToken);
            }


//            clientNameTextView.setText(strClientReceaverName.replace("_", " ") + " Calling...");
//
//            TextView outgoingCapabilityTextView = (TextView) capabilityPropertiesView.findViewById(R.id.outgoing_capability_registered_text);
//            outgoingCapabilityTextView.setText("Outgoing Capability: " + Boolean.toString(TwilioVoiceClientActivity.this.clientProfile.isAllowOutgoing()));
//
//            TextView incomingCapabilityTextView = (TextView) capabilityPropertiesView.findViewById(R.id.incoming_capability_registered_text);
//            incomingCapabilityTextView.setText("Incoming Capability: " + Boolean.toString(TwilioVoiceClientActivity.this.clientProfile.isAllowIncoming()));
//
//            TextView libraryVersionTextView = (TextView) capabilityPropertiesView.findViewById(R.id.library_version_text);
//            libraryVersionTextView.setText("Library Version: " + Twilio.getVersion());

            if (getIntent().hasExtra(Constants.TWILIO_INCOMMING)) {

            } else {
                //call to receaver user
                // Create an outgoing connection
                connect(strClientName, false);
            }

        } catch (Exception e) {
            Log.e(TAG, "An error has occured updating or creating a Device: \n" + e.toString());
//            Toast.makeText(TwilioVoiceClientActivity.this, "Device error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        Intent intent = getIntent();
        Log.e(TAG, "onResume: " + "INTENT ==="+intent);

        if (intent != null) {
            /*
             * Determine if the receiving Intent has an extra for the incoming connection. If so,
             * remove it from the Intent to prevent handling it again next time the Activity is resumed
             */
            Device device = intent.getParcelableExtra(Device.EXTRA_DEVICE);
            Connection incomingConnection = intent.getParcelableExtra(Device.EXTRA_CONNECTION);

            if (incomingConnection == null && device == null) {
                return;
            }
            intent.removeExtra(Device.EXTRA_DEVICE);
            intent.removeExtra(Device.EXTRA_CONNECTION);

            pendingConnection = incomingConnection;
            pendingConnection.setConnectionListener(this);
            Log.d(TAG, "onResume: CallSIDKey " + pendingConnection.getParameters().get(Connection.IncomingParameterCallSIDKey));
            if (isCallButtonPressed) {
                isCallButtonPressed = false;
                Log.d(TAG, "onResume: HIDE CONNECTING...");
                tvConnecting.setVisibility(View.GONE);
                answer();
                setCallUI();
            } else {
                isCallButtonPressed = true;
                showIncomingDialog();
            }
            Log.d(TAG, "onResume: INCOMING CALL");
        }
    }

    /*
     * Receive intent for incoming call from Twilio Client Service
     * Android will only call Activity.onNewIntent() if `android:launchMode` is set to `singleTop`.
     */
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: " + "INTENT ==="+intent);

        setIntent(intent);
    }

    /*
     * Request a Capability Token from your public accessible server
     */
    private void retrieveCapabilityToken(final ClientProfile newClientProfile) {
        // Update the current Client Profile to represent current properties
        TwilioVoiceClientActivity.this.clientProfile = newClientProfile;

        // Create a Device with the Capability Token
        createDevice(strClientToken);//maraj
    }

    /*
     * Create an outgoing connection
     */
    private void connect(String contact, boolean isPhoneNumber) {
        // Determine if you're calling another client or a phone number
        if (!isPhoneNumber) {
            contact = "client:" + contact.trim();
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("To", contact);

        if (clientDevice != null) {
            // Create an outgoing connection
            activeConnection = clientDevice.connect(params, this);
            setCallUI();

            //save sid
        } else {
            Toast.makeText(TwilioVoiceClientActivity.this, "No existing device", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Disconnect an active connection
     */
    private void disconnect() {

        try {
            if (pendingConnection != null) {
                Log.d(TAG, "disconnect: pendingConnection--reject");
                pendingConnection.reject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activeConnection != null) {
            Log.d(TAG, "activeConnection: disconnect");
            activeConnection.disconnect();
            activeConnection = null;
        }
        if (clientDevice != null) {
            Log.d(TAG, "disconnect: clientDevice--disconnectAll");
            clientDevice.disconnectAll();
            clientDevice = null;
        }
        finish();
    }

    /*
     * Accept an incoming connection
     */
    private void answer() {
        // Only one connection can exist at time, disconnecting any active connection.
        if (activeConnection != null) {
            activeConnection.disconnect();
        }

        isCallStarted = true;
        pendingConnection.accept();
        activeConnection = pendingConnection;
        pendingConnection = null;
    }

    /*
     * The initial state when there is no active connection
     */
    private void setCallAction() {
//        callActionFab.setOnClickListener(callActionFabClickListener());
        hangupActionFab.setOnClickListener(hangupActionFabClickListener());
        acceptActionFab.setOnClickListener(acceptActionFabClickListener());
        muteActionFab.setOnClickListener(muteMicrophoneFabClickListener());
        speakerActionFab.setOnClickListener(toggleSpeakerPhoneFabClickListener());
    }

    /*
     * Reset UI elements
     */
    private void resetUI() {
        capabilityPropertiesView.setVisibility(View.VISIBLE);

        hangupActionFab.hide();
        acceptActionFab.hide();
//        callActionFab.show();
        callView.setVisibility(View.INVISIBLE);
        chronometer.setVisibility(View.INVISIBLE);

        muteMicrophone = false;
        speakerPhone = false;

        muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.ic_mic_white_24dp));
        speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.ic_vol_type_speaker_dark));

        setAudioFocus(false);
        audioManager.setSpeakerphoneOn(speakerPhone);

        chronometer.stop();
    }

    /*
     * The UI state when there is an active connection
     */
    private void setCallUI() {

        capabilityPropertiesView.setVisibility(View.INVISIBLE);
        hangupActionFab.show();
//        acceptActionFab.show();
//        callActionFab.hide();
        callView.setVisibility(View.VISIBLE);
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /*
     * Creates an incoming call UI dialog
     */
    private void showIncomingDialog() {
        hangupActionFab.show();
        acceptActionFab.show();
//        callActionFab.hide();
//        alertDialog = TwilioDialog.createIncomingCallDialog(answerCallClickListener(), cancelCallClickListener(), this);
//        alertDialog.show();
    }

    /*
     * Creates an update token UI dialog
     */
//    private void updateClientProfileDialog() {
//        alertDialog = TwilioDialog.createRegisterDialog(updateTokenClickListener(), cancelCallClickListener(), clientProfile, this);
//        alertDialog.show();
//    }
//
//    /*
//     * Create an outgoing call UI dialog
//     */
//    private void showCallDialog() {
//        alertDialog = TwilioDialog.createCallDialog(callClickListener(), cancelCallClickListener(), this);
//        alertDialog.show();
//    }

    private DialogInterface.OnClickListener cancelCallClickListener() {
        return new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (pendingConnection != null) {
                    pendingConnection.reject();
                }
                alertDialog.dismiss();
            }
        };
    }

//    private DialogInterface.OnClickListener callClickListener() {
//        return new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                /*
//                 * Making an outgoing call
//                 */
//                EditText contact = (EditText) ((AlertDialog) dialog).findViewById(R.id.contact);
//                Spinner spinner = (Spinner) ((AlertDialog) dialog).findViewById(R.id.typeSpinner);
//                boolean isPhoneNumber = spinner.getSelectedItemPosition() == 1 ? true : false;
//
//                // Create an outgoing connection
//                connect(contact.getText().toString(), isPhoneNumber);
//                alertDialog.dismiss();
//            }
//        };
//    }

//    private DialogInterface.OnClickListener updateTokenClickListener() {
//        return new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                EditText clientEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.client_name_edittext);
//                String clientName = clientEditText.getText().toString();
//
//                CheckBox outgoingCheckBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.outgoing_checkbox);
//                boolean allowOutgoing = outgoingCheckBox.isChecked();
//
//                CheckBox incomingCheckBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.incoming_checkbox);
//                boolean allowIncoming = incomingCheckBox.isChecked();
//
//                ClientProfile newClientProfile = new ClientProfile(clientName, allowOutgoing, allowIncoming);
//                alertDialog.dismiss();
//                retrieveCapabilityToken(newClientProfile);
//            }
//        };
//    }

    private View.OnClickListener muteMicrophoneFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Mute/unmute microphone
                 */
                muteMicrophone = !muteMicrophone;
                if (activeConnection != null) {
                    activeConnection.setMuted(muteMicrophone);
                }
                if (muteMicrophone) {
                    muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.ic_mic_off_black_24dp));
                } else {
                    muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.ic_mic_white_24dp));
                }
            }
        };
    }

    private View.OnClickListener toggleSpeakerPhoneFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Audio routing to speakerphone or headset
                 */
                speakerPhone = !speakerPhone;

                setAudioFocus(true);
                audioManager.setSpeakerphoneOn(speakerPhone);

                if (speakerPhone) {
                    speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.quantum_ic_volume_up_white_36));
                } else {
                    speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientActivity.this, R.drawable.quantum_ic_volume_off_white_36));
                }
            }
        };
    }

    private View.OnClickListener hangupActionFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetUI();
                disconnect();

            }
        };
    }

    private View.OnClickListener acceptActionFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + isCallButtonPressed);
                try {
                    //off the tone
                    if (ringTone != null) {
                        ringTone.stop();
                        vib.cancel();
                        ringTone = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!isCallButtonPressed) {
                    tvConnecting.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onClick: SHOW CONNECTING....");
                    acceptActionFab.hide();
                    isCallButtonPressed = true;
                    return;
                } else {
                    acceptActionFab.hide();
                }
                answer();
                setCallUI();
            }
        };
    }

    /* Device Listener */
    @Override
    public void onStartListening(Device device) {
        Log.d(TAG, "Device has started listening for incoming connections");
    }

    /* Device Listener */
    @Override
    public void onStopListening(Device device) {
        Log.d(TAG, "Device has stopped listening for incoming connections");
    }

    /* Device Listener */
    @Override
    public void onStopListening(Device device, int errorCode, String error) {
        Log.e(TAG, String.format("Device has encountered an error and has stopped" +
                " listening for incoming connections: %s", error));
    }

    /* Device Listener */
    @Override
    public boolean receivePresenceEvents(Device device) {
        Log.d(TAG, "receivePresenceEvents: ");
        return false;
    }

    /* Device Listener */
    @Override
    public void onPresenceChanged(Device device, PresenceEvent presenceEvent) {
        Log.d(TAG, "onPresenceChanged: ");
    }

    /* Connection Listener */
    @Override
    public void onConnecting(Connection connection) {
        Log.d(TAG, "Attempting to connect");
    }

    /* Connection Listener */
    @Override
    public void onConnected(Connection connection) {
        Log.d(TAG, "Connected");
//        try{
//            ApiControllerClass.saveSIdData(TwilioVoiceClientActivity.this, new SaveSIDModel("voice", AllFollowersTwilioFragment.callerId, connection.getParameters().get(Connection.IncomingParameterCallSIDKey)));
//            Log.d(TAG, "Connected API CALLED TO SAVE SID");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    /* Connection Listener */
    @Override
    public void onDisconnected(Connection connection) {
        Log.d(TAG, "onDisconnected: a");
        Log.d(TAG, "onDisconnected: a" + connection.getParameters().toString());
        Log.d(TAG, "onDisconnected: a" + connection.getState().name());
        // Remote participant may have disconnected an incoming call before the local participant was able to respond, rejecting any existing pendingConnections
        if (connection == pendingConnection) {
            pendingConnection = null;
            resetUI();
//            alertDialog.dismiss();
        } else if (activeConnection != null && connection != null) {
            if (activeConnection == connection) {
                activeConnection = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetUI();
                    }
                });
            }
            Log.d(TAG, "Disconnect");
        }
//        if (isCallStarted) {
            finish();
//        }
//
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("user_id", AllFollowersTwilioFragment.arrayData);
//                jsonObject.put("status", 1);
//                jsonObject.put("caller_id", AllFollowersTwilioFragment.callerId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            ApiControllerClass.saveDeclineNotification(TwilioVoiceClientActivity.this, jsonObject.toString(), this);
//         }else {
//
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("user_id", AllFollowersTwilioFragment.arrayData);
//                jsonObject.put("status", 2);
//                jsonObject.put("caller_id", AllFollowersTwilioFragment.callerId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            ApiControllerClass.saveDeclineNotification(TwilioVoiceClientActivity.this, jsonObject.toString(), this);
//
//        }
//
    }

    /* Connection Listener */
    @Override
    public void onDisconnected(Connection connection, int errorCode, String error) {
        Log.d(TAG, "onDisconnected: b");
        Log.d(TAG, "onDisconnected: b" + connection.getParameters().toString());
        Log.d(TAG, "onDisconnected: b" + connection.getState().name());
        // A connection other than active connection could have errored out.
        if (activeConnection != null && connection != null) {
            if (activeConnection == connection) {
                activeConnection = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetUI();
                    }
                });
            }
            Log.e(TAG, String.format("Connection error: %s", error));
            finish();

        }
    }

    private boolean checkPermissionForMicrophone() {
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (resultMic == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissionForMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this,
                    "Microphone permissions needed. Please allow in App Settings for additional functionality.",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MIC_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*
         * Check if microphone permissions is granted
         */
        if (requestCode == MIC_PERMISSION_REQUEST_CODE && permissions.length > 0) {
            boolean granted = true;
            if (granted) {
                /*
                * Initialize the Twilio Client SDK
                */
                initializeTwilioClientSDK();
            } else {
                Toast.makeText(this,
                        "Microphone permissions needed. Please allow in App Settings for additional functionality.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setAudioFocus(boolean setFocus) {
        if (audioManager != null) {
            if (setFocus) {
                savedAudioMode = audioManager.getMode();
                // Request audio focus before making any device switch.
                audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                /*
                 * Start by setting MODE_IN_COMMUNICATION as default audio mode. It is
                 * required to be in this mode when playout and/or recording starts for
                 * best possible VoIP performance. Some devices have difficulties with speaker mode
                 * if this is not set.
                 */
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            } else {
                audioManager.setMode(savedAudioMode);
                audioManager.abandonAudioFocus(null);
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (pendingConnection != null) {
            pendingConnection.reject();
        }
        if (activeConnection != null) {
            activeConnection.disconnect();
            activeConnection = null;
        }
        try {
            //off the tone
            if (ringTone != null) {
                ringTone.stop();
                vib.cancel();
                ringTone = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /*
     * A representation of the current properties of a client token
     */
    protected class ClientProfile {
        private String name;
        private boolean allowOutgoing = true;
        private boolean allowIncoming = true;


        public ClientProfile(String name, boolean allowOutgoing, boolean allowIncoming) {
            this.name = name;
            this.allowOutgoing = allowOutgoing;
            this.allowIncoming = allowIncoming;
        }

        public String getName() {
            return name;
        }

        public boolean isAllowOutgoing() {
            return allowOutgoing;
        }

        public boolean isAllowIncoming() {
            return allowIncoming;
        }
    }
}
