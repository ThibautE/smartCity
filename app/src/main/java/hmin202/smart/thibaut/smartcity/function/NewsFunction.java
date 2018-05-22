package hmin202.smart.thibaut.smartcity.function;

public class NewsFunction {

    private String urlImage, title, description, url;

    public NewsFunction(String urlImage, String title, String description, String url) {
        this.urlImage = urlImage;
        this.title = title;
        this.description = description;
        this.url = url;
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
    public String getUrl() { return this.url; }
}