package com.igniva.qwer.model;

import java.io.Serializable;

/**
 * Created by igniva-andriod-11 on 4/5/16.
 */
public class CommentPojo implements Serializable{
    //    {
//        "id": 10,
//            "user_id": "103",
//            "post_id": "101",
//            "comment": "Nice",
//            "status": "0",
//            "created_at": "1510661381",
//            "comment_by_user": {
//        "id": 103,
//                "name": "tanmey",
//                "user_image": [
//
//          ]
//    }
//    }
    String created_at;
    String comment, status, post_id, user_id;
    int id;
    PostDetailPojo.DataPojo.PostUserPojo comment_by_user;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public PostDetailPojo.DataPojo.PostUserPojo getComment_by_user() {
        return comment_by_user;
    }

    public void setComment_by_user(PostDetailPojo.DataPojo.PostUserPojo comment_by_user) {
        this.comment_by_user = comment_by_user;
    }


}

