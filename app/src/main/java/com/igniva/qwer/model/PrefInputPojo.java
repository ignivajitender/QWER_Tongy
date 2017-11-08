package com.igniva.qwer.model;

import java.util.ArrayList;

/**
 * Created by tanmey on 8/11/17.
 */

public class PrefInputPojo {

    String prefered_age_from, prefered_gender, prefered_age_to, prefered_area_type, area_km;
    ArrayList<LanguagesProficiency> speak, learn;

    public String getPrefered_age_from() {
        return prefered_age_from;
    }

    public void setPrefered_age_from(String prefered_age_from) {
        this.prefered_age_from = prefered_age_from;
    }

    public String getPrefered_gender() {
        return prefered_gender;
    }

    public void setPrefered_gender(String prefered_gender) {
        this.prefered_gender = prefered_gender;
    }

    public String getPrefered_age_to() {
        return prefered_age_to;
    }

    public void setPrefered_age_to(String prefered_age_to) {
        this.prefered_age_to = prefered_age_to;
    }

    public String getPrefered_area_type() {
        return prefered_area_type;
    }

    public void setPrefered_area_type(String prefered_area_type) {
        this.prefered_area_type = prefered_area_type;
    }

    public String getArea_km() {
        return area_km;
    }

    public void setArea_km(String area_km) {
        this.area_km = area_km;
    }

    public ArrayList<LanguagesProficiency> getSpeak() {
        return speak;
    }

    public void setSpeak(ArrayList<LanguagesProficiency> speak) {
        this.speak = speak;
    }

    public ArrayList<LanguagesProficiency> getLearn() {
        return learn;
    }

    public void setLearn(ArrayList<LanguagesProficiency> learn) {
        this.learn = learn;
    }

    public void addLearn(String lanID, String lanPro) {
        LanguagesProficiency languagesProficiency = new LanguagesProficiency();
        languagesProficiency.setLanguage_id(lanID);
        languagesProficiency.setProficiency(lanPro);
        this.learn.add(languagesProficiency);
    }

    public void addSpeak(String lanID, String lanPro) {
        LanguagesProficiency languagesProficiency = new LanguagesProficiency();
        languagesProficiency.setLanguage_id(lanID);
        languagesProficiency.setProficiency(lanPro);
        this.speak.add(languagesProficiency);
    }

    public class LanguagesProficiency {
        String language_id, proficiency;

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getProficiency() {
            return proficiency;
        }

        public void setProficiency(String proficiency) {
            this.proficiency = proficiency;
        }
    }
}
