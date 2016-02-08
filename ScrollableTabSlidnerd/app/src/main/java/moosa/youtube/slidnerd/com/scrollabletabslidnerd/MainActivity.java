package moosa.youtube.slidnerd.com.scrollabletabslidnerd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


public class MainActivity extends FragmentActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.mainActivityPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new PageAdaptor(fragmentManager));

    }

    class PageAdaptor extends FragmentPagerAdapter {

        public PageAdaptor(FragmentManager fm) {
            super(fm);
            Log.d("Moosa", "Constructor Called");

        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            Log.d("Moosa", "GetItem Calling position:" + i);

            switch (i) {
                case 0:
                    fragment = new Fragment_A();
                    break;
                case 1:
                    fragment = new Fragment_B();
                    break;
                case 2:
                    fragment = new Fragment_C();
                    break;
                default:
                    fragment = null;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            Log.d("Moosa", "GetCount is Called");

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First";
                case 1:
                    return "Second";
                case 2:
                    return "Third";
                default:
                    break;
            }
            return super.getPageTitle(position);
        }
    }

}
