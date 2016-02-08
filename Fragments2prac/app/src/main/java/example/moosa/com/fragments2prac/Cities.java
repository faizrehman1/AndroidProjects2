package example.moosa.com.fragments2prac;

import java.util.ArrayList;

/**
 * Created by Moosa on 6/27/2015.
 */
public class Cities extends ArrayList<City> {
    Cities(){
        add(new City("Karachi","Karachi City",R.drawable.karachi));
        add(new City("Peshawar","Peshawar City",R.drawable.peshawar));
        add(new City("Lahore","Lahore City",R.drawable.lahore));
        add(new City("Islamabad","Islamabad City",R.drawable.islamabad));
        add(new City("Quetta","Quetta City",R.drawable.quettalake));

    }
}
