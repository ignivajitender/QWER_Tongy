package com.igniva.qwer.model;

/**
 * Created by tanmey on 28/11/17.
 */

public class TokenPojo {
//    {"status":200,"description":"Token created.","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2VkMDIzMGI4ZjY0MzQ1ZWIyMWFkNzdkOWQwOTZlNjU0LTE1MTE4NjAzNzciLCJpc3MiOiJTS2VkMDIzMGI4ZjY0MzQ1ZWIyMWFkNzdkOWQwOTZlNjU0Iiwic3ViIjoiQUM0Mjk0YTI4MWQxMDM0ZTE0YTM2OThiOGZmMjNiOTBiNyIsImV4cCI6MTUxMTg2Mzk3NywiZ3JhbnRzIjp7ImlkZW50aXR5IjoiMTAzIiwidmlkZW8iOnsicm9vbSI6IjEwMyJ9fX0.L14SzCn6jEXATzHxF04SVGwk04YpEDSxi68jHKBB1ak"}

int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String description,token;


}
