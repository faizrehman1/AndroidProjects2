package material.saylani.com.materialsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Moosa on 10/18/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class TabPagerAdaptor extends FragmentPagerAdapter {
private String[] names;
    public TabPagerAdaptor(FragmentManager fm,String[] names) {
        super(fm);
        this.names=names;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
        }
        return null;
    }

    @Override
    public int getCount() {
        return names.length;
    }
}
