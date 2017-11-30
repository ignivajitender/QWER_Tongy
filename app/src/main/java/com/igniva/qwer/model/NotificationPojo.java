package com.igniva.qwer.model;

import java.util.List;

/**
 * Created by igniva-android13 on 20/11/17.
 */

public class NotificationPojo {
    /**
     * status : 200
     * description : Notifications fetched  successfully.
     * current_page : 1
     * from : 1
     * last_page : 1
     * next_page_url : null
     * path : http://tongy.ignivastaging.com/api/users/getNotifications
     * per_page : 15
     * prev_page_url : null
     * to : 1
     * total : 1
     * data : [{"id":100,"is_read":"0","post_id":"144","sender_id":98,"sender_name":"Jack","message":"Commented on your post.","created_at":"1510903493"}]
     */

    private int status;
    private String description;
    private int current_page;
    private int from;
    private int last_page;
    private Object next_page_url;
    private String path;
    private int per_page;
    private Object prev_page_url;
    private int to;
    private int total;
    private List<NotificationDataPojo> data;

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

    public Object getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Object next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
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

    public List<NotificationDataPojo> getData() {
        return data;
    }

    public void setData(List<NotificationDataPojo> data) {
        this.data = data;
    }

    public static class NotificationDataPojo {
        /**
         * id : 100
         * is_read : 0
         * post_id : 144
         * sender_id : 98
         * sender_name : Jack
         * message : Commented on your post.
         * created_at : 1510903493
         */

        private int id;
        private String is_read;
        private int post_id;
        private int sender_id;
        private String sender_name;
        private String message;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public int getSender_id() {
            return sender_id;
        }

        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
