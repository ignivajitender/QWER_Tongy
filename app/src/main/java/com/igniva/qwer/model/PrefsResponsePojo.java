package com.igniva.qwer.model;

import java.util.ArrayList;

/**
 * Wrapper class -  to wrap ant response from webservice
 */
public class PrefsResponsePojo {
     /**
     * status : 200
     * description : Prefrences  fetch successfully.
     * speak : [{"language_id":"76","proficiency":"BEGINNER","languages":{"id":76,"name":"Dutch"}},{"language_id":"77","proficiency":"INTERMEDIATE","languages":{"id":77,"name":"Spanish"}},{"language_id":"78","proficiency":"PROFESSIONAL","languages":{"id":78,"name":"Arabic"}}]
     * learn : [{"language_id":"26","languages":{"id":26,"name":"English"}},{"language_id":"74","languages":{"id":74,"name":"French"}},{"language_id":"75","languages":{"id":75,"name":"German"}}]
     * prefrences : {"id":30,"user_id":"144","prefered_gender":"male","prefered_age_from":"25","prefered_age_to":"55","prefered_area_type":"worldwide","area_km":"0"}
     */

    private int status;
    private String description;
    private PrefrencesBean prefrences;
    private ArrayList<PrefInputPojo.LanguagesProficiency> speak;
    private ArrayList<PrefInputPojo.LanguagesProficiency> learn;

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

    public PrefrencesBean getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(PrefrencesBean prefrences) {
        this.prefrences = prefrences;
    }

    public ArrayList<PrefInputPojo.LanguagesProficiency> getSpeak() {
        return speak;
    }

    public void setSpeak(ArrayList<PrefInputPojo.LanguagesProficiency> speak) {
        this.speak = speak;
    }

    public ArrayList<PrefInputPojo.LanguagesProficiency> getLearn() {
        return learn;
    }

    public void setLearn(ArrayList<PrefInputPojo.LanguagesProficiency> learn) {
        this.learn = learn;
    }

    public static class PrefrencesBean {
        /**
         * id : 30
         * user_id : 144
         * prefered_gender : male
         * prefered_age_from : 25
         * prefered_age_to : 55
         * prefered_area_type : worldwide
         * area_km : 0
         */

        private int id;
        private String user_id;
        private String prefered_gender;
        private String prefered_age_from;
        private String prefered_age_to;
        private String prefered_area_type;
        private String area_km;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPrefered_gender() {
            return prefered_gender;
        }

        public void setPrefered_gender(String prefered_gender) {
            this.prefered_gender = prefered_gender;
        }

        public String getPrefered_age_from() {
            return prefered_age_from;
        }

        public void setPrefered_age_from(String prefered_age_from) {
            this.prefered_age_from = prefered_age_from;
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
    }

 }
