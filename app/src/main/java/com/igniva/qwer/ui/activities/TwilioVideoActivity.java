package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.TwilioCameraCapturerCompat;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.callBack.DeclinCallback;
import com.twilio.video.AudioTrack;
import com.twilio.video.CameraCapturer.CameraSource;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.Participant;
import com.twilio.video.Room;
import com.twilio.video.RoomState;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoConstraints;
import com.twilio.video.VideoDimensions;
import com.twilio.video.VideoRenderer;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import java.util.Collections;

public class TwilioVideoActivity extends AppCompatActivity implements View.OnClickListener, DeclinCallback {

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "TwilioVideoActivity";
    public static int memberCount = 1;
    /*
     * You must provide a Twilio Access Token to connect to the Video service
     */
    private static String TWILIO_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2MwNDg3OGNlYjA5YzA3YTJlZDYzNDUxNDBkYWYzMmFiLTE1MDg5MDk2ODEiLCJpc3MiOiJTS2MwNDg3OGNlYjA5YzA3YTJlZDYzNDUxNDBkYWYzMmFiIiwic3ViIjoiQUNhOTNkYTkyNmQyMDZiMWIxOWRlMDViOWMzOGNiZjNhMyIsImV4cCI6MTUwODkxMzI4MSwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiVTIiLCJ2aWRlbyI6eyJyb29tIjoicm9vbSJ9fX0.MGTVwYnVOlvBYb8vP6sjUYKK4snXCP4c2OZkBSbd790";
    Ringtone ringTone;
    Uri uriRingtone;
    long pattern[] = {0, 200, 100, 300, 400};
    int member = 0;
    CountDownTimer countDownTimer;
    //        Toolbar toolbar;
    TextView tvTitle;
    private Context mContext = TwilioVideoActivity.this;
    private boolean isIncomming = false;
    private Vibrator vib;
    //    private MediaPlayer mp;
//    private MediaPlayer mp1;
    private Chronometer chronometer;
    /*
     * Access token used to connect. This field will be set either from the console generated token
     * or the  request to the token server.
     */
    private String accessToken;
    /*
     * A Room represents communication between a local participant and one or more participants.
     */
    private Room room;
    private LocalParticipant localParticipant;
    /*
     * A VideoView receives frames from a local or remote video track and renders them
     * to an associated view.
     */
    private VideoView primaryVideoView1;
    private VideoView primaryVideoView;
    private VideoView thumbnailVideoView;
    /*
     * Android application UI elements
     */
    private TextView videoStatusTextView;
    private TwilioCameraCapturerCompat cameraCapturerCompat;
    private LocalAudioTrack localAudioTrack;
    private LocalVideoTrack localVideoTrack;
    //    private FloatingActionButton btnCallGreen;
    private FloatingActionButton switchCameraActionFab;
    private FloatingActionButton localVideoActionFab;
    private FloatingActionButton muteActionFab;
    private FloatingActionButton speakerphoneActionFab;
    //    private FloatingActionButton btnCallRed;
    private android.support.v7.app.AlertDialog alertDialog;
    private AudioManager audioManager;
    private String participantIdentity;
    private int previousAudioMode;
    private boolean previousMicrophoneMute;
    private VideoRenderer localVideoView;
    private boolean disconnectedFromOnDestroy;
    private String user_id = "";
    private String room_name = "";
    private FloatingActionButton btnAccept, btnReject;
    private Boolean isCallActive = false;
    private Boolean callInitiate = false;
    private int speakerOn = 1;
    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Inten
            String token = intent.getStringExtra(Constants.TWILIO_TOKEN);
            String room_name = intent.getStringExtra(Constants.TWILIO_ROOM);
            Log.d(TAG, "TOKEN: " + token);
            Log.d(TAG, "ROOM: " + room_name);
            accessToken = token;
            connectToRoom(room_name);
            isIncomming = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twilio_activity_video);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //accept & reject Buttons
        btnAccept = (FloatingActionButton) findViewById(R.id.btn_accept);
        btnAccept.setOnClickListener(this);
        btnReject = (FloatingActionButton) findViewById(R.id.btn_reject);
        btnReject.setOnClickListener(this);

        primaryVideoView = (VideoView) findViewById(R.id.primary_video_view_1);
        primaryVideoView = (VideoView) findViewById(R.id.primary_video_view);
        thumbnailVideoView = (VideoView) findViewById(R.id.thumbnail_video_view);
        videoStatusTextView = (TextView) findViewById(R.id.video_status_textview);
//        toolbar  = (Toolbar)findViewById(R.id.toolbar);
//        tvTitle = (TextView)findViewById(R.id.tvTitle);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("");
//        if(getIntent().hasExtra(Constants.SKIPROOM_TITLE)) {
//        tvTitle.setText(StringEscapeUtils.unescapeJava(getIntent().getStringExtra(Constants.SKIPROOM_TITLE)));
//        Log.d(TAG, "onCreate: "+getIntent().getStringExtra(Constants.SKIPROOM_TITLE));
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        onBackPressed();
//        }
//        });

//        btnCallRed = (FloatingActionButton) findViewById(R.id.connect_action_fab_red);

//        btnCallGreen = (FloatingActionButton) findViewById(R.id.connect_action_fab_greeen);

        switchCameraActionFab = (FloatingActionButton) findViewById(R.id.switch_camera_action_fab);
        localVideoActionFab = (FloatingActionButton) findViewById(R.id.local_video_action_fab);
        muteActionFab = (FloatingActionButton) findViewById(R.id.mute_action_fab);
        speakerphoneActionFab = (FloatingActionButton) findViewById(R.id.speakerphone_action_fab);

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        /*
         * Enable changing the volume using the up/down keys during a conversation
         */
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        /*
         * Needed for setting/abandoning audio focus during call
         */
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        audioManager.setStreamVolume(AudioManager.MODE_NORMAL, 20, 0);


        /*
         * Check camera and microphone permissions. Needed in Android M.
         */
        if (!checkPermissionForCameraAndMicrophone()) {
            requestPermissionForCameraAndMicrophone();
        } else {
            createAudioAndVideoTracks();
//            setAccessToken();
        }

//        btnCallRed.setOnClickListener(disconnectClickListener());
        /*
         * Set the initial state of the UI
         */
//        intializeUI();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.VIDEO_CALL_RECEAVER));
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringTone = RingtoneManager
                .getRingtone(getApplicationContext(), uriRingtone);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (getIntent().hasExtra(Constants.TWILIO_INCOMMING)) {
            ringTone.play();
            vib.vibrate(pattern, 0);
            setAccessToken();
            Log.e("Tag", "Calling");
        } else {
            ringTone.play();
            btnAccept.setVisibility(View.GONE);
            callInitiate = true;
            setAccessToken();
            intializeUI();
        }

        //timer to decline the call
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                Log.e(TAG, "onTick: " + l / 1000);
            }

            @Override
            public void onFinish() {
                btnReject.performClick();
            }
        }.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            boolean cameraAndMicPermissionGranted = true;

            for (int grantResult : grantResults) {
                cameraAndMicPermissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }

            if (cameraAndMicPermissionGranted) {
                createAudioAndVideoTracks();
//                setAccessToken();
            } else {
                Toast.makeText(this,
                        R.string.permissions_needed,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * If the local video track was released when the app was put in the background, recreate.
         */
        if (localVideoTrack == null && checkPermissionForCameraAndMicrophone()) {
            localVideoTrack = LocalVideoTrack.create(this, true, cameraCapturerCompat.getVideoCapturer());
            localVideoTrack.addRenderer(localVideoView);

            /*
             * If connected to a Room then share the local video track.
             */
            if (localParticipant != null) {
                localParticipant.addVideoTrack(localVideoTrack);
            }
        }

    }

    @Override
    protected void onPause() {
        /*
         * Release the local video track before going in the background. This ensures that the
         * camera can be used by other applications while this app is in the background.
         */
        if (localVideoTrack != null) {
            /*
             * If this local video track is being shared in a Room, remove from local
             * participant before releasing the video track. Participants will be notified that
             * the track has been removed.
             */
            if (localParticipant != null) {
                localParticipant.removeVideoTrack(localVideoTrack);
            }

            localVideoTrack.release();
            localVideoTrack = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        if (room != null && room.getState() != RoomState.DISCONNECTED) {
            room.disconnect();
            disconnectedFromOnDestroy = true;
        }

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        if (localAudioTrack != null) {
            localAudioTrack.release();
            localAudioTrack = null;
        }
        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }
// Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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


        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this,
                    R.string.permissions_needed,
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_MIC_PERMISSION_REQUEST_CODE);
        }
    }


    private void createAudioAndVideoTracks() {

        // Setup video constraints
        VideoConstraints videoConstraints = new VideoConstraints.Builder()
                .aspectRatio(VideoConstraints.ASPECT_RATIO_16_9)
                .minVideoDimensions(VideoDimensions.CIF_VIDEO_DIMENSIONS)
                .maxVideoDimensions(VideoDimensions.HD_720P_VIDEO_DIMENSIONS)
                .minFps(5)
                .maxFps(24)
                .build();

        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(this, true);
        // Share your camera
        cameraCapturerCompat = new TwilioCameraCapturerCompat(this, CameraSource.FRONT_CAMERA);
        localVideoTrack = LocalVideoTrack.create(this, true, cameraCapturerCompat.getVideoCapturer(), videoConstraints);
        primaryVideoView.setMirror(true);
        localVideoTrack.addRenderer(primaryVideoView);
        localVideoView = primaryVideoView;
    }

    private void setAccessToken() {
        // OPTION 1- Generate an access token from the getting started portal
        // https://www.twilio.com/console/video/dev-tools/testing-tools
//        this.accessToken = TWILIO_ACCESS_TOKEN;

        if (getIntent().hasExtra(Constants.TWILIO_TOKEN)) {
            accessToken = getIntent().getStringExtra(Constants.TWILIO_TOKEN);
            connectToRoom(getIntent().getStringExtra(Constants.TWILIO_ROOM));
        }
        if (!isIncomming) {
            /*ApiControllerClass.getVideoToken((Activity) mContext, user_id, room_name, new TwilioVideoTokenCallBack() {
                @Override
                public void twilioVideoToken(String token) {
                    accessToken = token;
                    connectToRoom(room_name);
                }
            });*/

        }
        // OPTION 2- Retrieve an access token from your own web app
        // retrieveAccessTokenfromServer();
    }

    private void connectToRoom(String roomName) {
        configureAudio(true);
        ConnectOptions.Builder connectOptionsBuilder = new ConnectOptions.Builder(accessToken)
                .roomName(roomName);

        /*
         * Add local audio track to connect options to share with participants.
         */
        if (localAudioTrack != null) {
            connectOptionsBuilder
                    .audioTracks(Collections.singletonList(localAudioTrack));
        }

        /*
         * Add local video track to connect options to share with participants.
         */
        if (localVideoTrack != null) {
            connectOptionsBuilder.videoTracks(Collections.singletonList(localVideoTrack));
        }

        // if call accept by the user
        if (isCallActive)
            room = Video.connect(this, connectOptionsBuilder.build(), roomListener());

        // if one user dial a call
        if (callInitiate)
            room = Video.connect(this, connectOptionsBuilder.build(), roomListener());


//        try {
//            //off the tone
//            if (mp != null && mp.isPlaying()) {
//                mp.stop();
//                mp.release();
//                vib.cancel();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    /*
     * The initial state when there is no active room.
     */
    private void intializeUI() {

        switchCameraActionFab.setVisibility(View.VISIBLE);
        localVideoActionFab.setVisibility(View.VISIBLE);
        muteActionFab.setVisibility(View.VISIBLE);
        speakerphoneActionFab.setVisibility(View.VISIBLE);

        switchCameraActionFab.show();
        switchCameraActionFab.setOnClickListener(switchCameraClickListener());
        localVideoActionFab.show();
        localVideoActionFab.setOnClickListener(localVideoClickListener());
        muteActionFab.show();
        muteActionFab.setOnClickListener(muteClickListener());
        speakerphoneActionFab.show();
        speakerphoneActionFab.setOnClickListener(speakerActionClickListner());
    }

    private View.OnClickListener speakerActionClickListner() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakerOn == 1) {
                    speakerOn = 0;
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.MODE_NORMAL, 0, 0);
                    speakerphoneActionFab.setImageDrawable(ContextCompat.getDrawable(
                            TwilioVideoActivity.this, R.drawable.quantum_ic_volume_off_white_36));
                } else {
                    speakerOn = 1;
                    audioManager.setSpeakerphoneOn(true);
                    audioManager.setMode(AudioManager.MODE_IN_CALL);
                    audioManager.setStreamVolume(AudioManager.MODE_NORMAL, 20, 0);
                    speakerphoneActionFab.setImageDrawable(ContextCompat.getDrawable(
                            TwilioVideoActivity.this, R.drawable.ic_volume_up_white_24dp));
                }


            }
        };
    }

    /*
     * The actions performed during disconnect.
     */
    private void setDisconnectAction() {

    }

    /*
     * Creates an connect UI dialog
     */

    private void showConnectDialog() {
//        EditText roomEditText = new EditText(this);
//        alertDialog =  Dialog.createConnectDialog(roomEditText,
//        connectClickListener(roomEditText), cancelConnectDialogClickListener(), this);
//        alertDialog.show();
    }

    /*
     * Called when participant joins the room
     */

    private void addParticipant(Participant participant) {

        countDownTimer.cancel();
        /*
         * This app only displays video for one additional participant per Room
         */

        if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
            Utility.showToastMessageShort((Activity) mContext, "Multiple participants are not currently support in this UI");
            return;
        }
        participantIdentity = participant.getIdentity();
        videoStatusTextView.setText("Participant " + participantIdentity + " joined");

        /*
         * Add participant renderer
         */

        if (participant.getVideoTracks().size() > 0) {
            Log.e(TAG, "addParticipant: " + participant.getVideoTracks().size());
            switch (participant.getVideoTracks().size()) {
                case 1:
                    addParticipantVideo(participant.getVideoTracks().get(0));
                    break;
                case 2:
                    addParticipantVideo1(participant.getVideoTracks().get(1));
                    break;
            }

            if (ringTone != null) {
                ringTone.stop();
                vib.cancel();
                ringTone = null;
            }
        }
        //start time for call logs
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        /*
         * Start listening for participant events
         */
        intializeUI();
        participant.setListener(participantListener());
    }

    /*
     * Set primary view as renderer for participant video track
     */

    private void addParticipantVideo(VideoTrack videoTrack) {
        moveLocalVideoToThumbnailView();
        primaryVideoView.setMirror(false);
        videoTrack.addRenderer(primaryVideoView);
    }

    /*
     * Set primary view as renderer for participant 2 video track
     */

    private void addParticipantVideo1(VideoTrack videoTrack) {
//        moveLocalVideoToThumbnailView();
        primaryVideoView1.setVisibility(View.VISIBLE);
        primaryVideoView1.setMirror(false);
        videoTrack.addRenderer(primaryVideoView1);
    }

    private void moveLocalVideoToThumbnailView() {
        if (thumbnailVideoView.getVisibility() == View.GONE) {
            thumbnailVideoView.setVisibility(View.VISIBLE);
            localVideoTrack.removeRenderer(primaryVideoView);
            localVideoTrack.addRenderer(thumbnailVideoView);
            localVideoView = thumbnailVideoView;
            thumbnailVideoView.setMirror(cameraCapturerCompat.getCameraSource() ==
                    CameraSource.FRONT_CAMERA);
        }
    }

    /*
     * Called when participant leaves the room
     */
    private void removeParticipant(Participant participant) {
        videoStatusTextView.setText("Participant " + participant.getIdentity() + " left.");
        if (!participant.getIdentity().equals(participantIdentity)) {
            return;
        }

        /*
         * Remove participant renderer
         */
        if (participant.getVideoTracks().size() > 0) {
            switch (participant.getVideoTracks().size()) {
                case 1:
                    removeParticipantVideo(participant.getVideoTracks().get(0));
                    break;
                case 2:
                    removeParticipantVideo1(participant.getVideoTracks().get(1));
                    break;
            }
        }
        moveLocalVideoToPrimaryView();
    }

    private void removeParticipantVideo(VideoTrack videoTrack) {
        videoTrack.removeRenderer(primaryVideoView);
    }

    private void removeParticipantVideo1(VideoTrack videoTrack) {
        videoTrack.removeRenderer(primaryVideoView1);
        primaryVideoView1.setVisibility(View.GONE);
    }

    private void moveLocalVideoToPrimaryView() {
        try {
            if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
                localVideoTrack.removeRenderer(thumbnailVideoView);
                thumbnailVideoView.setVisibility(View.GONE);
                localVideoTrack.addRenderer(primaryVideoView);
                localVideoView = primaryVideoView;
                primaryVideoView.setMirror(cameraCapturerCompat.getCameraSource() ==
                        CameraSource.FRONT_CAMERA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Room events listener
     */
    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                Log.e(TAG, "Connected");
                Log.e(TAG, "ROOM : - NAME" + room.getName());
                Log.e(TAG, "ROOM : - SID" + room.getSid());
                Log.e(TAG, "ROOM : - STATE" + room.getState());
                if (!room.getSid().isEmpty() && room.getSid().length() > 0) {
//        ApiControllerClass.saveSIdData(TwilioVideoActivity.this, new SaveSIDModel("video", AllFollowersTwilioFragment.callerId, room.getSid()));
                }

                localParticipant = room.getLocalParticipant();
                videoStatusTextView.setText("Connected to " + room.getName());
                setTitle(room.getName());
                for (Participant participant : room.getParticipants()) {
                    addParticipant(participant);
                    break;
                }
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                Log.e(TAG, "Failed to connect");
                videoStatusTextView.setText("Failed to connect");
                configureAudio(false);
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                Log.e(TAG, "-----Disconnected ------ " + room.getState());
                localParticipant = null;
                videoStatusTextView.setText("Disconnected from " + room.getName());
                TwilioVideoActivity.this.room = null;
                if (room != null) {
                    room.disconnect();
                }
                btnReject.performClick();
                finish();

                // Only reinitialize the UI if disconnect was not called from onDestroy()
                if (!disconnectedFromOnDestroy) {
                    configureAudio(false);
                    intializeUI();
                    moveLocalVideoToPrimaryView();
                }
            }

            @Override
            public void onParticipantConnected(Room room, Participant participant) {
                Log.e(TAG, room.getName() + "onParticipantConnected" + participant.getIdentity().toString());
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
                addParticipant(participant);

            }

            @Override
            public void onParticipantDisconnected(Room room, Participant participant) {
                Log.e(TAG, room.getName() + "onParticipantDisconnected" + participant.getIdentity().toString());
                removeParticipant(participant);
                chronometer.stop();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnReject.performClick();
                    }
                }, 2000);

            }

            @Override
            public void onRecordingStarted(Room room) {
                /*
                 * Indicates when media shared to a Room is being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.e(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
                /*
                 * Indicates when media shared to a Room is no longer being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.e(TAG, "onRecordingStopped");
            }
        };
    }

    private Participant.Listener participantListener() {
        return new Participant.Listener() {
            @Override
            public void onAudioTrackAdded(Participant participant, AudioTrack audioTrack) {
                videoStatusTextView.setText("onAudioTrackAdded");
            }

            @Override
            public void onAudioTrackRemoved(Participant participant, AudioTrack audioTrack) {
                videoStatusTextView.setText("onAudioTrackRemoved");
            }

            @Override
            public void onVideoTrackAdded(Participant participant, VideoTrack videoTrack) {
                videoStatusTextView.setText("onVideoTrackAdded");
                addParticipantVideo(videoTrack);
            }

            @Override
            public void onVideoTrackRemoved(Participant participant, VideoTrack videoTrack) {
                videoStatusTextView.setText("onVideoTrackRemoved");
                removeParticipantVideo(videoTrack);
            }

            @Override
            public void onAudioTrackEnabled(Participant participant, AudioTrack audioTrack) {

            }

            @Override
            public void onAudioTrackDisabled(Participant participant, AudioTrack audioTrack) {

            }

            @Override
            public void onVideoTrackEnabled(Participant participant, VideoTrack videoTrack) {

            }

            @Override
            public void onVideoTrackDisabled(Participant participant, VideoTrack videoTrack) {

            }
        };
    }

    private DialogInterface.OnClickListener connectClickListener(final EditText roomEditText) {
        return new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                 * Connect to room
                 */

                connectToRoom(roomEditText.getText().toString());
            }
        };
    }

    private DialogInterface.OnClickListener cancelConnectDialogClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intializeUI();
                alertDialog.dismiss();
            }
        };
    }

    private View.OnClickListener switchCameraClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraCapturerCompat != null) {
                    CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
                    cameraCapturerCompat.switchCamera();
                    if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
                        thumbnailVideoView.setMirror(cameraSource == CameraSource.BACK_CAMERA);
                    } else {
                        primaryVideoView.setMirror(cameraSource == CameraSource.BACK_CAMERA);
                    }
                }
            }
        };
    }

    private View.OnClickListener localVideoClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Enable/disable the local video track
                 */
                if (localVideoTrack != null) {
                    boolean enable = !localVideoTrack.isEnabled();
                    localVideoTrack.enable(enable);
                    int icon;
                    if (enable) {
                        icon = R.drawable.ic_videocam_white_24dp;
                        switchCameraActionFab.show();
                    } else {
                        icon = R.drawable.ic_videocam_off_black_24dp;
                        switchCameraActionFab.hide();
                    }
                    localVideoActionFab.setImageDrawable(
                            ContextCompat.getDrawable(TwilioVideoActivity.this, icon));
                }
            }
        };
    }

    private View.OnClickListener muteClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Enable/disable the local audio track. The results of this operation are
                 * signaled to other Participants in the same Room. When an audio track is
                 * disabled, the audio is muted.
                 */
                if (localAudioTrack != null) {
                    boolean enable = !localAudioTrack.isEnabled();
                    localAudioTrack.enable(enable);
                    int icon = enable ?
                            R.drawable.ic_mic_white_24dp : R.drawable.ic_mic_off_black_24dp;
                    muteActionFab.setImageDrawable(ContextCompat.getDrawable(
                            TwilioVideoActivity.this, icon));
                }
            }
        };
    }

    private void configureAudio(boolean enable) {
        if (enable) {
            previousAudioMode = audioManager.getMode();
            // Request audio focus before making any device switch.
            audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            /*
             * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
             * to be in this mode when playout and/or recording starts for the best
             * possible VoIP performance. Some devices have difficulties with
             * speaker mode if this is not set.
             */
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            /*
             * Always disable microphone mute during a WebRTC call.
             */
            previousMicrophoneMute = audioManager.isMicrophoneMute();
            audioManager.setMicrophoneMute(false);
        } else {
            audioManager.setMode(previousAudioMode);
            audioManager.abandonAudioFocus(null);
            audioManager.setMicrophoneMute(previousMicrophoneMute);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnAccept) {
            Log.e("Tag", "Maraj connect");
            if (ringTone != null) {
                ringTone.stop();
                vib.cancel();
                ringTone = null;
            }
            isCallActive = true;
            setAccessToken();

            view.setVisibility(View.GONE);
        } else if (view == btnReject) {
             /*
              *   Disconnect from room
              */

            if (room != null && member == 0) {

//        JSONObject jsonObject = new JSONObject();
//        try {
//        jsonObject.put("user_id", AllFollowersTwilioFragment.arrayData);
//        jsonObject.put("status", 2);
//        jsonObject.put("caller_id", AllFollowersTwilioFragment.callerId);
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        ApiControllerClass.saveDeclineNotification(TwilioVideoActivity.this, jsonObject.toString(), this);
//        } else if (member >= 1) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//        jsonObject.put("user_id", AllFollowersTwilioFragment.arrayData);
//        jsonObject.put("status", 1);
//        jsonObject.put("caller_id", AllFollowersTwilioFragment.callerId);
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        ApiControllerClass.saveDeclineNotification(TwilioVideoActivity.this, jsonObject.toString(), this);
//        }
//
                if (ringTone != null) {
                    ringTone.stop();
                    vib.cancel();
                    ringTone = null;
                }
                finish();

            }
        }

    }

    @Override
    public void videoCallDecline(boolean result) {
        if (result) {
            if (room != null) {
                room.disconnect();
                finish();
            }
        }
    }


}

