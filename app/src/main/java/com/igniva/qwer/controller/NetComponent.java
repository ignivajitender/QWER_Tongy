package com.igniva.qwer.controller;

import com.igniva.qwer.ui.activities.ChangeEmailActivity;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.activities.ContactUsActivity;
import com.igniva.qwer.ui.activities.CreateNewPostActivity;
import com.igniva.qwer.ui.activities.CreateOtherPostActivity;
import com.igniva.qwer.ui.activities.CreateTeachingPostActivity;
import com.igniva.qwer.ui.activities.ForgotPasswordActivity;
import com.igniva.qwer.ui.activities.LocationActivity;
import com.igniva.qwer.ui.activities.LoginActivity;
import com.igniva.qwer.ui.activities.MyProfileActivity;
import com.igniva.qwer.ui.activities.PostDetailActivity;
import com.igniva.qwer.ui.activities.SearchActivity;
import com.igniva.qwer.ui.activities.SetPreferrencesActivity;
import com.igniva.qwer.ui.activities.SettingsActivity;
import com.igniva.qwer.ui.activities.SignUpActivity;
import com.igniva.qwer.ui.fragments.NewsFeedFragment;

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

    void inject(NewsFeedFragment fragment);
    void inject(LocationActivity activity);
    void inject(CreateOtherPostActivity activity);
    void inject(SearchActivity activity);
    void inject(PostDetailActivity activity);

}