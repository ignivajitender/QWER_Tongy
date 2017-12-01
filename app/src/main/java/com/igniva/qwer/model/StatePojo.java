package com.igniva.qwer.model;

/**
 * Created by igniva-android13 on 26/10/17.
 */

public class StatePojo {
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
    public String name;
    public String country_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
