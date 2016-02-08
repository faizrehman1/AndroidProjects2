package Model;

/**
 * Created by Moosa on 7/2/2015.
 */
public class CurrentCondition {
    private int weatherId;
    private String condtion;
    private String description;
    private String icon;
    private float humidty;
    private float maxTemp;
    private float minTemp;
    private float pressure;
    private double temprature;

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getCondtion() {
        return condtion;
    }

    public void setCondtion(String condtion) {
        this.condtion = condtion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getHumidty() {
        return humidty;
    }

    public void setHumidty(float humidty) {
        this.humidty = humidty;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public double getTemprature() {
        return temprature;
    }

    public void setTemprature(double temprature) {
        this.temprature = temprature;
    }
}
