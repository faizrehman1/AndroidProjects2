package example.moosa.com.fragments2prac;

/**
 * Created by Moosa on 6/27/2015.
 */
public class City {
    private String name;
    private String description;
    private int imageId;

    public City(String name, String description, int imageId) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return name;
    }
}
