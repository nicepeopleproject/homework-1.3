package model;
//https://habr.com/ru/company/naumen/blog/228279/

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    Region region;


    public void addPost(Post post) {
        posts.add(post);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getId() {
        return id.longValue();
    }

    public List<Post> getPosts() {
        return new ArrayList(posts);
    }

    public Region getRegion() {
        return region;
    }

    public Writer(String firstName, String lastName, Region region) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.region = region;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, Writer.class);
    }
}
