package model;

import com.google.gson.Gson;

public class Region {
    private Long id;
    private String name;

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, this.getClass());
    }
}
