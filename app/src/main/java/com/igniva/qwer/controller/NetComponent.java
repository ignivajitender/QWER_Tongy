package com.igniva.qwer.controller;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {


    //void inject(SignupActivity activity);


}