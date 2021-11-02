package model;
//https://habr.com/ru/company/naumen/blog/228279/

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class Writer {
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("posts")
    private List<Post> posts;
    @SerializedName("region")
    Region region;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public Region getRegion() {
        return region;
    }

    public Writer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new LinkedList<>();
        this.region = null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, Writer.class);
    }
}
