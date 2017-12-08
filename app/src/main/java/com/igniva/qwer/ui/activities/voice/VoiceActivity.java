//package com.igniva.qwer.ui.activities.voice;
//
//import android.Manifest;
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.support.annotation.NonNull;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Chronometer;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.gson.JsonObject;
//import com.igniva.qwer.R;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.twilio.voice.Call;
//import com.twilio.voice.CallException;
//import com.twilio.voice.CallInvite;
//import com.twilio.voice.RegistrationException;
//import com.twilio.voice.RegistrationListener;
//import com.twilio.voice.Voice;
//
//import java.util.HashMap;
//
//public class VoiceActivity extends AppCompatActivity {
//
//    private static final String TAG = "VoiceActivity";
//
//    /*
//     * You must provide a Twilio Access Token to connect to the Voice service
//     */
//    private static final String TWILIO_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzZmZGFmZDhjZDU5NDkzZWRkYjI2YWM3MGYzN2UzYTU5LTE1MTA2NjUwMjYiLCJpc3MiOiJTSzZmZGFmZDhjZDU5NDkzZWRkYjI2YWM3MGYzN2UzYTU5Iiwic3ViIjoiQUNhOTNkYTkyNmQyMDZiMWIxOWRlMDViOWMzOGNiZjNhMyIsImV4cCI6MTUxMDY2ODYyNiwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiYW5zaHVsIiwidm9pY2UiOnsib3V0Z29pbmciOnsiYXBwbGljYXRpb25fc2lkIjoiQVAzZGRlMjc4Yzk2NTg4ZWI0YjhiNDljNDQ1YmY5ZGFiNiJ9fX19.OzsI-d8LIAGS_QXnCZsBBNHP3ku5EJag6tBegmMD9OU";
////    private static final String TWILIO_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzZmZGFmZDhjZDU5NDkzZWRkYjI2YWM3MGYzN2UzYTU5LTE1MTAxMzQwMzYiLCJpc3MiOiJTSzZmZGFmZDhjZDU5NDkzZWRkYjI2YWM3MGYzN2UzYTU5Iiwic3ViIjoiQUNhOTNkYTkyNmQyMDZiMWIxOWRlMDViOWMzOGNiZjNhMyIsImV4cCI6MTUxMDEzNzYzNiwiZ3JhbnRzIjp7ImlkZW50aXR5Ijoiam9obl9kb2UiLCJ2b2ljZSI6eyJvdXRnb2luZyI6eyJhcHBsaWNhdGlvbl9zaWQiOiJBUDNkZGUyNzhjOTY1ODhlYjRiOGI0OWM0NDViZjlkYWI2In19fX0.U3I8Ck8WX2NK4mjjsED2Y3YzYJPDYJDRnPT39Q1k8Zg";
////    private static final String TWILIO_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6InNjb3BlOmNsaWVudDppbmNvbWluZz9jbGllbnROYW1lPWplbm55IiwiaXNzIjoiQUNhOTNkYTkyNmQyMDZiMWIxOWRlMDViOWMzOGNiZjNhMyIsImV4cCI6MTUxMDEyNjY1MH0.8m9gruHVxg5FTfi0EYaQyf-83XGvdE7x2eqLBR3FzjY";
//
//    private static final int MIC_PERMISSION_REQUEST_CODE = 1;
//    private static final int SNACKBAR_DURATION = 4000;
//
//    private AudioManager audioManager;
//    private int savedAudioMode = AudioManager.MODE_INVALID;
//
//    private boolean isReceiverRegistered = false;
//    private VoiceBroadcastReceiver voiceBroadcastReceiver;
//
//    // Empty HashMap, never populated for the Quickstart
//    HashMap<String, String> twiMLParams = new HashMap<>();
//
//    private CoordinatorLayout coordinatorLayout;
//    private FloatingActionButton callActionFab;
//    private FloatingActionButton hangupActionFab;
//    private FloatingActionButton speakerActionFab;
//    private Chronometer chronometer;
//    private SoundPoolManager soundPoolManager;
//
//    public static final String INCOMING_CALL_INVITE = "INCOMING_CALL_INVITE";
//    public static final String INCOMING_CALL_NOTIFICATION_ID = "INCOMING_CALL_NOTIFICATION_ID";
//    public static final String ACTION_INCOMING_CALL = "ACTION_INCOMING_CALL";
//    public static final String ACTION_FCM_TOKEN = "ACTION_FCM_TOKEN";
//    private NotificationManager notificationManager;
//    private AlertDialog alertDialog;
//    private CallInvite activeCallInvite;
//    private Call activeCall;
//    RegistrationListener registrationListener = registrationListener();
//    Call.Listener callListener = callListener();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_voice);
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
//        callActionFab = (FloatingActionButton) findViewById(R.id.call_action_fab);
//        hangupActionFab = (FloatingActionButton) findViewById(R.id.hangup_action_fab);
//        speakerActionFab = (FloatingActionButton) findViewById(R.id.speakerphone_action_fab);
//        chronometer = (Chronometer) findViewById(R.id.chronometer);
//
//        callActionFab.setOnClickListener(callActionFabClickListener());
//        hangupActionFab.setOnClickListener(hangupActionFabClickListener());
//        speakerActionFab.setOnClickListener(speakerphoneActionFabClickListener());
//
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        soundPoolManager = SoundPoolManager.getInstance(this);
//
//        /*
//         * Setup the broadcast receiver to be notified of FCM Token updates
//         * or incoming call invite in this Activity.
//         */
//        voiceBroadcastReceiver = new VoiceBroadcastReceiver();
//        registerReceiver();
//
//        /*
//         * Needed for setting/abandoning audio focus during a call
//         */
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//
//        /*
//         * Enable changing the volume using the up/down keys during a conversation
//         */
//        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//
//        /*
//         * Setup the UI
//         */
//        resetUI();
//
//        /*
//         * Displays a call dialog if the intent contains a call invite
//         */
//        handleIncomingCallIntent(getIntent());
//
//        /*
//         * Ensure the microphone permission is enabled
//         */
//        if (!checkPermissionForMicrophone()) {
//            requestPermissionForMicrophone();
//        } else {
//            registerForCallInvites();
//        }
////        retrieveAccessTokenfromServer();
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        handleIncomingCallIntent(intent);
//    }
//
//    private RegistrationListener registrationListener() {
//        return new RegistrationListener() {
//            @Override
//            public void onRegistered(String accessToken, String fcmToken) {
//                Log.d(TAG, "Successfully registered FCM " + fcmToken);
//                Snackbar.make(coordinatorLayout, "Registered", SNACKBAR_DURATION).show();
//            }
//
//            @Override
//            public void onError(RegistrationException error, String accessToken, String fcmToken) {
//                Log.e("error ",""+accessToken+" "+fcmToken);
//                String message = String.format("Registration Error: %d, %s", error.getErrorCode(), error.getMessage());
//                Log.e(TAG, message);
//                Snackbar.make(coordinatorLayout, message, SNACKBAR_DURATION).show();
//            }
//        };
//    }
//
//    private Call.Listener callListener() {
//        return new Call.Listener() {
//            @Override
//            public void onConnected(Call call) {
//                setAudioFocus(true);
//                Log.d(TAG, "Connected");
//                activeCall = call;
//            }
//
//            @Override
//            public void onDisconnected(Call call, CallException error) {
//                setAudioFocus(false);
//                Log.d(TAG, "Disconnected");
//                if (error != null) {
//                    String message = String.format("Call Error: %d, %s", error.getErrorCode(), error.getMessage());
//                    Log.e(TAG, message);
//                    Snackbar.make(coordinatorLayout, message, SNACKBAR_DURATION).show();
//                }
//                resetUI();
//            }
//        };
//    }
//
//    /*
//     * The UI state when there is an active call
//     */
//    private void setCallUI() {
//        callActionFab.hide();
//        hangupActionFab.show();
//        speakerActionFab.show();
//        chronometer.setVisibility(View.VISIBLE);
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.start();
//    }
//
//    /*
//     * Reset UI elements
//     */
//    private void resetUI() {
//        if (!audioManager.isSpeakerphoneOn()) {
//            toggleSpeakerPhone();
//        }
//        speakerActionFab.hide();
//        callActionFab.show();
//        hangupActionFab.hide();
//        chronometer.setVisibility(View.INVISIBLE);
//        chronometer.stop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver();
//    }
//
//    @Override
//    public void onDestroy() {
//        soundPoolManager.release();
//        super.onDestroy();
//    }
//
//    private void handleIncomingCallIntent(Intent intent) {
//        if (intent != null && intent.getAction() != null) {
//            if (intent.getAction().equals(ACTION_INCOMING_CALL)) {
//                activeCallInvite = intent.getParcelableExtra(INCOMING_CALL_INVITE);
//                if (activeCallInvite != null && (activeCallInvite.getState() == CallInvite.State.PENDING)) {
//                    soundPoolManager.playRinging();
//                    alertDialog = createIncomingCallDialog(VoiceActivity.this,
//                            activeCallInvite,
//                            answerCallClickListener(),
//                            cancelCallClickListener());
//                    alertDialog.show();
//                    notificationManager.cancel(intent.getIntExtra(INCOMING_CALL_NOTIFICATION_ID, 0));
//                } else {
//                    if (alertDialog != null && alertDialog.isShowing()) {
//                        soundPoolManager.stopRinging();
//                        alertDialog.cancel();
//                    }
//                }
//            } else if (intent.getAction().equals(ACTION_FCM_TOKEN)) {
//                registerForCallInvites();
//            }
//        }
//    }
//
//    private void registerReceiver() {
//        if (!isReceiverRegistered) {
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(ACTION_INCOMING_CALL);
//            intentFilter.addAction(ACTION_FCM_TOKEN);
//            LocalBroadcastManager.getInstance(this).registerReceiver(
//                    voiceBroadcastReceiver, intentFilter);
//            isReceiverRegistered = true;
//        }
//    }
//
//    private void unregisterReceiver() {
//        if (isReceiverRegistered) {
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(voiceBroadcastReceiver);
//            isReceiverRegistered = false;
//        }
//    }
//
//    private class VoiceBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(ACTION_INCOMING_CALL)) {
//                /*
//                 * Handle the incoming call invite
//                 */
//                handleIncomingCallIntent(intent);
//            }
//        }
//    }
//
//    private DialogInterface.OnClickListener answerCallClickListener() {
//        return new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                answer();
//                setCallUI();
//                alertDialog.dismiss();
//            }
//        };
//    }
//
//    private DialogInterface.OnClickListener cancelCallClickListener() {
//        return new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                activeCallInvite.reject(VoiceActivity.this);
//                alertDialog.dismiss();
//            }
//        };
//    }
//
//    public static AlertDialog createIncomingCallDialog(
//            Context context,
//            CallInvite callInvite,
//            DialogInterface.OnClickListener answerCallClickListener,
//            DialogInterface.OnClickListener cancelClickListener) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setIcon(R.drawable.ic_video_call_black_24dp);
//        alertDialogBuilder.setTitle("Incoming Call");
//        alertDialogBuilder.setPositiveButton("Accept", answerCallClickListener);
//        alertDialogBuilder.setNegativeButton("Reject", cancelClickListener);
//        alertDialogBuilder.setMessage(callInvite.getFrom() + " is calling.");
//        return alertDialogBuilder.create();
//    }
//
//    /*
//     * Register your FCM token with Twilio to receive incoming call invites
//     *
//     * If a valid google-services.json has not been provided or the FirebaseInstanceId has not been
//     * initialized the fcmToken will be null.
//     *
//     * In the case where the FirebaseInstanceId has not yet been initialized the
//     * VoiceFirebaseInstanceIDService.onTokenRefresh should result in a LocalBroadcast to this
//     * activity which will attempt registerForCallInvites again.
//     *
//     */
//    private void registerForCallInvites() {
//        final String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        if (fcmToken != null) {
//            Log.i(TAG, "Registering with FCM "+fcmToken);
//            Voice.register(this, TWILIO_ACCESS_TOKEN, fcmToken, registrationListener);
//        }
//    }
//
//    private View.OnClickListener callActionFabClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                twiMLParams.put("To", "maraj");
//                try {
//                    activeCall = Voice.call(VoiceActivity.this, TWILIO_ACCESS_TOKEN, twiMLParams, callListener);
//                    setCallUI();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//
//    private View.OnClickListener hangupActionFabClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                soundPoolManager.playDisconnect();
//                resetUI();
//                disconnect();
//            }
//        };
//    }
//
//    private View.OnClickListener speakerphoneActionFabClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleSpeakerPhone();
//            }
//        };
//    }
//
//    /*
//     * Accept an incoming Call
//     */
//    private void answer() {
//        activeCallInvite.accept(this, callListener);
//    }
//
//    /*
//     * Disconnect from Call
//     */
//    private void disconnect() {
//        if (activeCall != null) {
//            activeCall.disconnect();
//            activeCall = null;
//        }
//    }
//
//    private void toggleSpeakerPhone() {
//        if (audioManager.isSpeakerphoneOn()) {
//            audioManager.setSpeakerphoneOn(false);
//            speakerActionFab.setImageDrawable(ContextCompat.getDrawable(VoiceActivity.this, R.drawable.quantum_ic_volume_off_grey600_36));
//        } else {
//            audioManager.setSpeakerphoneOn(true);
//            speakerActionFab.setImageDrawable(ContextCompat.getDrawable(VoiceActivity.this, R.drawable.quantum_ic_volume_off_white_36));
//        }
//    }
//
//    private void setAudioFocus(boolean setFocus) {
//        if (audioManager != null) {
//            if (setFocus) {
//                savedAudioMode = audioManager.getMode();
//                // Request audio focus before making any device switch.
//                audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
//                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
//
//                /*
//                 * Start by setting MODE_IN_COMMUNICATION as default audio mode. It is
//                 * required to be in this mode when playout and/or recording starts for
//                 * best possible VoIP performance. Some devices have difficulties with speaker mode
//                 * if this is not set.
//                 */
//                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//            } else {
//                audioManager.setMode(savedAudioMode);
//                audioManager.abandonAudioFocus(null);
//            }
//        }
//    }
//
//    private boolean checkPermissionForMicrophone() {
//        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
//        return resultMic == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermissionForMicrophone() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
//            Snackbar.make(coordinatorLayout,
//                    "Microphone permissions needed. Please allow in your application settings.",
//                    SNACKBAR_DURATION).show();
//        } else {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[]{Manifest.permission.RECORD_AUDIO},
//                    MIC_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        /*
//         * Check if microphone permissions is granted
//         */
//        if (requestCode == MIC_PERMISSION_REQUEST_CODE && permissions.length > 0) {
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                Snackbar.make(coordinatorLayout,
//                        "Microphone permissions needed. Please allow in your application settings.",
//                        SNACKBAR_DURATION).show();
//            } else {
//                registerForCallInvites();
//            }
//        }
//    }
//
//
//
//    private void retrieveAccessTokenfromServer() {
//        Ion.with(this)
//                // Make JSON request to server
//                .load("https://demoalready.herokuapp.com/token")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    // Handle response from server
//                    public void onCompleted(Exception e, JsonObject result) {
//                        if (e == null) {
//                            // The identity can be used to receive calls
//                            Log.e("response ",""+result.toString());
//                            String identity = result.get("identity").getAsString();
//                            String accessToken = result.get("token").getAsString();
//                            Log.i(TAG, "Token found: " + accessToken);
//                        } else {
//                            Log.i(TAG, "Error fetching token from server");
//                        }
//                    }
//                });
//    }
//
//
//}
