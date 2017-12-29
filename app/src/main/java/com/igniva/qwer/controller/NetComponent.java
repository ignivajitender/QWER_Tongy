package com.igniva.qwer.controller;

import com.igniva.qwer.fcm.MyFirebaseMessagingService;
import com.igniva.qwer.ui.activities.AboutTongyActivity;
import com.igniva.qwer.ui.activities.ChangeEmailActivity;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.activities.CommentsActivity;
import com.igniva.qwer.ui.activities.ConnectionAcceptedActivity;
import com.igniva.qwer.ui.activities.ContactUsActivity;
import com.igniva.qwer.ui.activities.CreateNewPostActivity;
import com.igniva.qwer.ui.activities.CreateOtherPostActivity;
import com.igniva.qwer.ui.activities.CreateTeachingPostActivity;
import com.igniva.qwer.ui.activities.ForgotPasswordActivity;
import com.igniva.qwer.ui.activities.LocationActivity;
import com.igniva.qwer.ui.activities.LoginActivity;
import com.igniva.qwer.ui.activities.MyProfileActivity;
import com.igniva.qwer.ui.activities.NotificationActivity;
import com.igniva.qwer.ui.activities.OtherUserProfileActivity;
import com.igniva.qwer.ui.activities.PostDetailActivity;
import com.igniva.qwer.ui.activities.SearchActivity;
import com.igniva.qwer.ui.activities.SetPreferrencesActivity;
import com.igniva.qwer.ui.activities.SettingsActivity;
import com.igniva.qwer.ui.activities.SignUpActivity;
import com.igniva.qwer.ui.activities.SplashActivity;
import com.igniva.qwer.ui.activities.twilio_chat.ChatClientManager;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatActivity;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatFragment;
import com.igniva.qwer.ui.fragments.ConnectionsFragment;
import com.igniva.qwer.ui.fragments.HomeFragment;
import com.igniva.qwer.ui.fragments.PostsListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(CreateTeachingPostActivity activity);

    void inject(ChangeEmailActivity activity);

    void inject(MyProfileActivity activity);

    void inject(ChangePasswordActivity activity);

    void inject(ContactUsActivity activity);

    void inject(CreateNewPostActivity activity);

    void inject(ForgotPasswordActivity activity);

    void inject(LoginActivity activity);

    void inject(SignUpActivity activity);

    void inject(SetPreferrencesActivity activity);

    void inject(SettingsActivity activity);

    void inject(PostsListFragment fragment);

    void inject(LocationActivity activity);

    void inject(CreateOtherPostActivity activity);

    void inject(SearchActivity activity);

    void inject(PostDetailActivity activity);

    void inject(CommentsActivity commentsActivity);
    void inject(NotificationActivity activity);
    void inject(OtherUserProfileActivity activity);
    void inject(ConnectionsFragment fragment);
    void inject(SplashActivity activity);
    void inject(HomeFragment fragment);
    void inject(ConnectionAcceptedActivity activity);

    void inject(ChatClientManager chatClientManager);

    void inject(MainChatFragment mainChatFragment);

    void inject(MainChatActivity mainChatActivity);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);

    void inject(AboutTongyActivity aboutTongyActivity);
}