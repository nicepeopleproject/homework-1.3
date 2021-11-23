package model;

import com.google.gson.Gson;

import java.util.Date;

public class Post {
    private Long id;
    private Long writerId;
    private String content;
    private Date created;
    private Date updated;

    public Post(Long writerId, String content) {
        this.content = content;
        this.writerId = writerId;
        this.created = new Date();
    }

    public Long getId() {
        return id.longValue();
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, Post.class);
    }

    public void setContent(String content) {
        updated = new Date();
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    public Date getUpdated() {
        return new Date(updated.getTime());
    }
}
