package com.igniva.qwer.model;

/**
 * Created by igniva-android13 on 26/10/17.
 */

public class CountriesPojo {
   /* {
        "status": 200,
            "description": "Countries fetch successfully.",
            "data": [
        {
            "id": 1,
                "country": "Afghanistan",
                "country_flag": "img/countries_flag/Afghanistan.png"
        },*/

   public String id;
    public String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String country_flag;

}
