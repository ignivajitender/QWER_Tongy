package com.igniva.qwer.model;

/**
 * Created by igniva-andriod-11 on 4/5/16.
 */
public class CommentPojo {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public PostDetailPojo.DataPojo.PostUserPojo getComment_by_user() {
        return comment_by_user;
    }

    public void setComment_by_user(PostDetailPojo.DataPojo.PostUserPojo comment_by_user) {
        this.comment_by_user = comment_by_user;
    }

    String comment;
    int id, status, post_id, user_id;
    PostDetailPojo.DataPojo.PostUserPojo comment_by_user;


}

