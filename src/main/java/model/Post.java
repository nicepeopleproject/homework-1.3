package model;

import java.util.Date;

public class Post {
    private String content;
    private Date created;
    private Date updated;

    public Post(String content) {
        this.content = content;
    }
}
