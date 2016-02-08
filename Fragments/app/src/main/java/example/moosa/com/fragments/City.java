package example.moosa.com.fragments;

/**
 * Created by Moosa on 6/26/2015.
 */
public class City {
    private int imageId;
    private String title;
    private String description;

    public City(int imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
