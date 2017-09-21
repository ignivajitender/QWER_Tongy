package com.igniva.qwer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleLogin {

@SerializedName("statusCode")
@Expose
private Integer statusCode;

@SerializedName("message")
@Expose
private String message;

//@SerializedName("data")
//@Expose
//private DataLogin data;

/**
* 
* @return
* The statusCode
*/
public Integer getStatusCode() {
return statusCode;
}

/**
* 
* @param statusCode
* The statusCode
*/
public void setStatusCode(Integer statusCode) {
this.statusCode = statusCode;
}

/**
* 
* @return
* The message
*/
public String getMessage() {
return message;
}

/**
* 
* @param message
* The message
*/
public void setMessage(String message) {
this.message = message;
}

/**
* 
* @return
* The data
*/
//public DataLogin getData() {
//return data;
//}
//
///**
//*
//* @param data
//* The data
//*/
//public void setData(DataLogin data) {
//this.data = data;
//}

}