package com.igniva.qwer.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tanmey on 8/11/17.
 */

public class PrefInputPojo {

    String prefered_gender, prefered_area_type;
    int prefered_age_from, prefered_age_to, area_km;
    ArrayList<LanguagesProficiency> speak, learn;

    public int getPrefered_age_from() {
        return prefered_age_from;
    }

    public void setPrefered_age_from(int prefered_age_from) {
        this.prefered_age_from = prefered_age_from;
    }

    public String getPrefered_gender() {
        return prefered_gender;
    }

    public void setPrefered_gender(String prefered_gender) {
        this.prefered_gender = prefered_gender;
    }

    public int getPrefered_age_to() {
        return prefered_age_to;
    }

    public void setPrefered_age_to(int prefered_age_to) {
        this.prefered_age_to = prefered_age_to;
    }

    public String getPrefered_area_type() {
        return prefered_area_type;
    }

    public void setPrefered_area_type(String prefered_area_type) {
        this.prefered_area_type = prefered_area_type;
    }

    public int getArea_km() {
        return area_km;
    }

    public void setArea_km(int area_km) {
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
    public static class LanguagesBean {
        /**
         * id : 26
         * name : English
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class LanguagesProficiency implements Serializable {
        String language_id;
        String proficiency;
        String name;
        LanguagesBean languages;

        public LanguagesBean getLanguages() {
            return languages;
        }

        public void setLanguages(LanguagesBean languages) {
            this.languages = languages;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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
