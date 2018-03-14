package com.igniva.qwer.model;

/**
 * Created by tanmey on 22/12/17.
 */

 public class ViewPojo {



    private String view;
    private UsersResponsePojo.UsersPojo.UserDataPojo data;
    private String user_id;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public UsersResponsePojo.UsersPojo.UserDataPojo getData() {
        return data;
    }

    public void setData(UsersResponsePojo.UsersPojo.UserDataPojo data) {
        this.data = data;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

 }
