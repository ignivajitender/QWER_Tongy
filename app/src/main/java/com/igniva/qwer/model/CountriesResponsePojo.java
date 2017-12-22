package com.igniva.qwer.model;

import java.util.ArrayList;

/**
 * Wrapper class -  to wrap ant response from webservice
 */
public class CountriesResponsePojo {

    private int status;
    private String description;
    private ArrayList<CountriesPojo> data;
    private ErrorPojo error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<CountriesPojo> getData() {
        return data;
    }

    public void setData(ArrayList<CountriesPojo> data) {
        this.data = data;
    }

    public ErrorPojo getError() {
        return error;
    }

    public void setError(ErrorPojo error) {
        this.error = error;
    }

}
