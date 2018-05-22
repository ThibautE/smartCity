package hmin202.smart.thibaut.smartcity.function;

public class NewsFunction {

    private String urlImage, titre, description, url;

    public NewsFunction(String urlImage, String titre, String description, String url) {
        this.urlImage = urlImage;
        this.titre = titre;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return this.titre;
    }
    public String getDescription() {
        return this.description;
    }
    public String getUrlImage() {
        return this.urlImage;
    }
    public String getUrl() { return this.url; }
}