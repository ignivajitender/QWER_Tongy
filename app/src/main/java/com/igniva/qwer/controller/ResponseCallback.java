package com.igniva.qwer.controller;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jitender-android on 27/6/17.
 */

public interface ResponseCallback<T> {

    /** Successful HTTP response. */
    void success(T t, Response response);

    /**
     * Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected
     * exception.
     */
    void failure(RetrofitError error);
}