package com.igniva.qwer.model;

import java.util.ArrayList;

/**
 * Created by igniva-android13 on 4/10/17.
 */

public class GooglePlaceApiResponsePojo {


    public int statusCode;
    public String message;
    public ErrorPojo error;
    // Variables  End
    public String status;
    public ArrayList<predictionsPojo> predictions = new ArrayList();

    public ArrayList<predictionsPojo> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<predictionsPojo> predictions) {
        this.predictions = predictions;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorPojo getError() {
        return error;
    }

    public void setError(ErrorPojo error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
