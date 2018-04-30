package com.example.thibaut.smartcity.function;

public class NewsFunction {

    private String title;
    private String description;
    private String urlImage;

    public NewsFunction(String title, String description, String urlImage) {
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public String getUrlImage() {
        return this.urlImage;
    }

    public void setTitle(String t) {
        this.title = t;
    }
    public void setDescription(String d) {
        this.description = d;
    }public void setUrlImage(String u) {
        this.urlImage = u;
    }

}