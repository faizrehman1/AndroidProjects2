package Model;

/**
 * Created by Moosa on 7/2/2015.
 */
public class Weather {
    public Place place;
    public String iconData;
    public CurrentCondition currentCondition= new CurrentCondition();
    public Temprature temprature = new Temprature();
    public Wind wind=new Wind();
    public Snow snow=new Snow();
    public Clouds clouds=new Clouds();

}
