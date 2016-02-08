package example.moosa.com.fragments;

import java.util.ArrayList;

/**
 * Created by Moosa on 6/26/2015.
 */
public class Cities extends ArrayList<City> {
    public Cities() {
        add(new City(R.drawable.karachi, "Karachi", "Karachi city"));
        add(new City(R.drawable.lahore, "Lahore", "Lahore city"));
        add(new City(R.drawable.islamabad, "Islamabad", "Islamabad city"));
        add(new City(R.drawable.peshawar, "Peshawar", "Peshawar city"));
        add(new City(R.drawable.quettalake, "Quetta", "Quetta city"));
    }
}
