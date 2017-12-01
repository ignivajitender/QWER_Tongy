package com.igniva.qwer.ui.activities.voice;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Constants;
import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;



public class TwilioVoiceClientIncomingActivity extends AppCompatActivity implements DeviceListener, ConnectionListener {

    private static final String TAG = "TwilioVoiceClient";

    private static final int MIC_PERMISSION_REQUEST_CODE = 1;

    /*
     * A Device is the primary entry point to Twilio Services
     */
//    private Device MyApplication.clientDevice;

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
    private AlertDialog alertDialog;
    private Chronometer chronometer;
    private View callView;
    private View capabilityPropertiesView;

    private boolean muteMicrophone;
    private boolean speakerPhone;

    private String strClientName = "null";
    private String strClientToken = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twilio_voice_client);

        if(getIntent().hasExtra(Constants.TWILIO_SENDER_NAME)) {
            Log.d(TAG, "TWILIO_SENDER_NAME: " + getIntent().getStringExtra(Constants.TWILIO_SENDER_NAME));
            Log.d(TAG, "TWILIO_TOKEN: " + getIntent().getStringExtra(Constants.TWILIO_TOKEN));
            strClientName = getIntent().getStringExtra(Constants.TWILIO_SENDER_NAME);
            strClientToken = getIntent().getStringExtra(Constants.TWILIO_TOKEN);
        }

        callView = (View) findViewById(R.id.call_layout);
        capabilityPropertiesView = (View) findViewById(R.id.capability_properties);

        callActionFab = (FloatingActionButton) findViewById(R.id.call_action_fab);
        hangupActionFab = (FloatingActionButton) findViewById(R.id.hangup_action_fab);
        acceptActionFab = (FloatingActionButton) findViewById(R.id.accept_action_fab);
        muteActionFab = (FloatingActionButton) findViewById(R.id.mute_action_fab);
        speakerActionFab = (FloatingActionButton) findViewById(R.id.speaker_action_fab);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

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
            createDevice();
        }

    }

    /*
     * Create a Device or update the capabilities of the current Device
     */
    private void createDevice() {
        try {

            TextView clientNameTextView = (TextView) capabilityPropertiesView.findViewById(R.id.client_name_registered_text);
//            clientNameTextView.setText(MyApplication.clientProfile.getName()+" Calling...");

            TextView outgoingCapabilityTextView = (TextView) capabilityPropertiesView.findViewById(R.id.outgoing_capability_registered_text);
//            outgoingCapabilityTextView.setText("Outgoing Capability: " + Boolean.toString(TwilioVoiceClientIncomingActivity.this.clientProfile.isAllowOutgoing()));

            TextView incomingCapabilityTextView = (TextView) capabilityPropertiesView.findViewById(R.id.incoming_capability_registered_text);
//            incomingCapabilityTextView.setText("Incoming Capability: " + Boolean.toString(TwilioVoiceClientIncomingActivity.this.clientProfile.isAllowIncoming()));

            TextView libraryVersionTextView = (TextView) capabilityPropertiesView.findViewById(R.id.library_version_text);
//            libraryVersionTextView.setText("Library Version: " + Twilio.getVersion());

            if(getIntent().hasExtra(Constants.TWILIO_INCOMMING)){
                //do something
               Intent intent = getIntent();
                if (intent != null) {

//             * Determine if the receiving Intent has an extra for the incoming connection. If so,
//             * remove it from the Intent to prevent handling it again next time the Activity is resumed

                    Device device = intent.getParcelableExtra(Device.EXTRA_DEVICE);
                    Connection incomingConnection = intent.getParcelableExtra(Device.EXTRA_CONNECTION);

                    Log.d(TAG, "createDevice: "+device);
                    Log.d(TAG, "createDevice: "+incomingConnection);
                    if (incomingConnection == null && device == null) {
                        return;
                    }
                    intent.removeExtra(Device.EXTRA_DEVICE);
                    intent.removeExtra(Device.EXTRA_CONNECTION);

                    pendingConnection = incomingConnection;
                    pendingConnection.setConnectionListener(this);

                    showIncomingDialog();
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "An error has occured updating or creating a Device: \n" + e.toString());
            Toast.makeText(TwilioVoiceClientIncomingActivity.this, "Device error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /*
     * Request a Capability Token from your public accessible server
     */
   /* private void retrieveCapabilityToken(final ClientProfile newClientProfile) {

                            // Update the current Client Profile to represent current properties
                            TwilioVoiceClientIncomingActivity.this.clientProfile = newClientProfile;

                            // Create a Device with the Capability Token
                            createDevice(strClientToken);//maraj

    }*/
    /*
     * Disconnect an active connection
     */
    private void disconnect() {
        try {
            if (pendingConnection != null) {
                pendingConnection.reject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         if (activeConnection != null) {
            activeConnection.disconnect();
            activeConnection = null;
        }
//        if (MyApplication.clientDevice != null) {
//            MyApplication.clientDevice.disconnectAll();
//            MyApplication.clientDevice = null;
//        }
    }

    /*
     * Accept an incoming connection
     */
    private void answer() {
        // Only one connection can exist at time, disconnecting any active connection.
        if( activeConnection != null ){
            activeConnection.disconnect();
        }

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

        muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_mic_white_24dp));
        speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_vol_type_speaker_dark));

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
        acceptActionFab.show();
//        callActionFab.hide();
        callView.setVisibility(View.VISIBLE);
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /*
     * Create an outgoing call UI dialog
     */
    private void showCallDialog() {
//        alertDialog = TwilioDialog.createCallDialog(callClickListener(), cancelCallClickListener(), this);
//        alertDialog.show();
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

    private DialogInterface.OnClickListener answerCallClickListener() {
        return new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                 * Accept an incoming call
                 */
                answer();
                setCallUI();
                alertDialog.dismiss();
            }
        };
    }

    private DialogInterface.OnClickListener callClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                 * Making an outgoing call
                 */
                EditText contact = (EditText) ((AlertDialog) dialog).findViewById(R.id.contact);
//                Spinner spinner = (Spinner) ((AlertDialog) dialog).findViewById(R.id.typeSpinner);
//                boolean isPhoneNumber = spinner.getSelectedItemPosition() == 1 ? true : false;

                // Create an outgoing connection
//                connect(contact.getText().toString(), isPhoneNumber);
                alertDialog.dismiss();
            }
        };
    }

    /*private DialogInterface.OnClickListener updateTokenClickListener() {
        return new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText clientEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.client_name_edittext);
                String clientName = clientEditText.getText().toString();

                CheckBox outgoingCheckBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.outgoing_checkbox);
                boolean allowOutgoing = outgoingCheckBox.isChecked();

                CheckBox incomingCheckBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.incoming_checkbox);
                boolean allowIncoming = incomingCheckBox.isChecked();

                ClientProfile newClientProfile = new ClientProfile(clientName, allowOutgoing, allowIncoming);
                alertDialog.dismiss();
                retrieveCapabilityToken(newClientProfile);
            }
        };
    }*/

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
                    muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_mic_off_black_24dp));
                } else {
                    muteActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_mic_white_24dp));
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
                    speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_vol_type_speaker_light));
                } else {
                    speakerActionFab.setImageDrawable(ContextCompat.getDrawable(TwilioVoiceClientIncomingActivity.this, R.drawable.ic_vol_type_speaker_dark));
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
    }private View.OnClickListener acceptActionFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer();
                setCallUI();
            }
        };
    }

    private View.OnClickListener callActionFabClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showCallDialog();
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
    }

    /* Connection Listener */
    @Override
    public void onDisconnected(Connection connection) {
        Log.d(TAG, "onDisconnected: ");
        // Remote participant may have disconnected an incoming call before the local participant was able to respond, rejecting any existing pendingConnections
        if( connection == pendingConnection ) {
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
        finish();
    }

    /* Connection Listener */
    @Override
    public void onDisconnected(Connection connection, int errorCode, String error) {
        Log.d(TAG, "onDisconnected: ");
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
//                initializeTwilioClientSDK();
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
        if (activeConnection != null) {
            activeConnection.disconnect();
            activeConnection = null;
        }
        super.onDestroy();
    }
}
