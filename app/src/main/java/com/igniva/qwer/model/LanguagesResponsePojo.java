package com.igniva.qwer.model;

import java.util.ArrayList;

/**
 * Created by tanmey on 8/11/17.
 */

public class LanguagesResponsePojo {

    int status;
    ArrayList<LanguagesPojo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<LanguagesPojo> getData() {
        return data;
    }

    public void setData(ArrayList<LanguagesPojo> data) {
        this.data = data;
    }

    public class LanguagesPojo {
    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
}
