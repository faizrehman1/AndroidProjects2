package material.saylani.com.materialsample;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
String[] names={"ONE","TWO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabPagerAdaptor tabPagerAdaptor=new TabPagerAdaptor(getSupportFragmentManager(),names);
        ViewPager viewPager=(ViewPager)findViewById(R.id.myViewPagerAdaptor);
        PagerTabStrip pagerTabStrip=(PagerTabStrip)findViewById(R.id.tabPagerStrip);
        viewPager.setAdapter(tabPagerAdaptor);


    }

}
