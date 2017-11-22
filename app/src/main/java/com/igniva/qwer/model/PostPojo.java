package com.igniva.qwer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by igniva-android13 on 7/11/17.
 */

public class PostPojo implements Serializable{


    /**
     * status : 200
     * description : Posts fetch successfully.
     * data : {"current_page":1,"data":[{"id":45,"user_id":"110","post_type":"teaching","title":"teaching awesome great","description":"Great and great brothers","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510058258","post_user":{"id":110,"name":"amit","user_image":[{"id":41,"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0","created_at":"1510055417"}]},"post_comment_count":[],"post_fav":[]},{"id":44,"user_id":"110","post_type":"teaching","title":"teaching awesome great","description":"Great and great sons","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510058010","post_user":{"id":110,"name":"amit","user_image":[{"id":41,"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0","created_at":"1510055417"}]},"post_comment_count":[],"post_fav":[]},{"id":43,"user_id":"109","post_type":"teaching","title":"teaching awesome","description":"Awesome goog","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057915","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":42,"user_id":"109","post_type":"teaching","title":"teaching sectioning","description":"Awesome environment","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057826","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":41,"user_id":"109","post_type":"teaching","title":"teaching sectioning","description":"Awesome environment","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057823","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":40,"user_id":"98","post_type":"teaching","title":"teaching section my","description":"deascriii my end","price":"20","currency":"usd","start_date_time":"1508493600","end_date_time":"1511175600","class_type":"online","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510050838","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":39,"user_id":"98","post_type":"teaching","title":"teaching section my","description":"deascriii","price":"20","currency":"usd","start_date_time":"1508493600","end_date_time":"1511175600","class_type":"online","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510050822","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":38,"user_id":"98","post_type":"teaching","title":"teaching sectionssss","description":"decent environmenting","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1509974578","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":37,"user_id":"98","post_type":"meeting","title":"meeting section","description":"nice view and collections thank you","price":"0","currency":"","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"","class_location":"chandigarh","lat":"20.220000","lng":"20.000000","status":"1","created_at":"1509974487","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":36,"user_id":"98","post_type":"teaching","title":"teaching sectionssss","description":"decent environmenting","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1509974317","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]}],"from":1,"last_page":4,"next_page_url":"http://tongy.ignivastaging.com/api/nonUsers/post?page=2","path":"http://tongy.ignivastaging.com/api/nonUsers/post","per_page":"10","prev_page_url":null,"to":10,"total":40}
     */

    private int status;
    private String description;
    private PostDataPojo data;

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

    public PostDataPojo getData() {
        return data;
    }

    public void setData(PostDataPojo data) {
        this.data = data;
    }

    public static class PostDataPojo implements Serializable{
        /**
         * current_page : 1
         * data : [{"id":45,"user_id":"110","post_type":"teaching","title":"teaching awesome great","description":"Great and great brothers","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510058258","post_user":{"id":110,"name":"amit","user_image":[{"id":41,"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0","created_at":"1510055417"}]},"post_comment_count":[],"post_fav":[]},{"id":44,"user_id":"110","post_type":"teaching","title":"teaching awesome great","description":"Great and great sons","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510058010","post_user":{"id":110,"name":"amit","user_image":[{"id":41,"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0","created_at":"1510055417"}]},"post_comment_count":[],"post_fav":[]},{"id":43,"user_id":"109","post_type":"teaching","title":"teaching awesome","description":"Awesome goog","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057915","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":42,"user_id":"109","post_type":"teaching","title":"teaching sectioning","description":"Awesome environment","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057826","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":41,"user_id":"109","post_type":"teaching","title":"teaching sectioning","description":"Awesome environment","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510057823","post_user":{"id":109,"name":"larry","user_image":[{"id":40,"user_id":"109","image":"http://tongy.ignivastaging.com/images/profile_image/1510054753-343B8132.jpg","is_cover_image":"0","created_at":"1510054753"}]},"post_comment_count":[],"post_fav":[]},{"id":40,"user_id":"98","post_type":"teaching","title":"teaching section my","description":"deascriii my end","price":"20","currency":"usd","start_date_time":"1508493600","end_date_time":"1511175600","class_type":"online","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510050838","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":39,"user_id":"98","post_type":"teaching","title":"teaching section my","description":"deascriii","price":"20","currency":"usd","start_date_time":"1508493600","end_date_time":"1511175600","class_type":"online","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1510050822","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":38,"user_id":"98","post_type":"teaching","title":"teaching sectionssss","description":"decent environmenting","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1509974578","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":37,"user_id":"98","post_type":"meeting","title":"meeting section","description":"nice view and collections thank you","price":"0","currency":"","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"","class_location":"chandigarh","lat":"20.220000","lng":"20.000000","status":"1","created_at":"1509974487","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]},{"id":36,"user_id":"98","post_type":"teaching","title":"teaching sectionssss","description":"decent environmenting","price":"5000","currency":"usd","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"physical","class_location":"","lat":"0.000000","lng":"0.000000","status":"1","created_at":"1509974317","post_user":{"id":98,"name":"jack off to the side of build","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment_count":[],"post_fav":[]}]
         * from : 1
         * last_page : 4
         * next_page_url : http://tongy.ignivastaging.com/api/nonUsers/post?page=2
         * path : http://tongy.ignivastaging.com/api/nonUsers/post
         * per_page : 10
         * prev_page_url : null
         * to : 10
         * total : 40
         */

        private int current_page;
        private int from;
        private int last_page;
        private String next_page_url;
        private String path;
        private String per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<PostDetailPojo.DataPojo> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<PostDetailPojo.DataPojo> getData() {
            return data;
        }

        public void setData(List<PostDetailPojo.DataPojo> data) {
            this.data = data;
        }
    }
}
