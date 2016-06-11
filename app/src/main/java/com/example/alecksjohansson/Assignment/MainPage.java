package com.example.alecksjohansson.Assignment;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainPage extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog dpd;
    String dateStart;
    String dateEnd;
    TextView mtvFlyOut;
    TextView mTvFlyBack;
    private TabLayout mTabLayout;
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        //mtvFlyOut = (TextView) findViewById(R.id.tvFlyOut);
       // mTvFlyBack = (TextView) findViewById(R.id.tvFlyBack);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        Drawable mDrawable = getDrawable(R.drawable.background3);

        mViewPager.setBackground(mDrawable);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {


            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    //case 0:
                    //    return RecyclerViewFragment.newInstance();
                    //case 1:
                    //    return RecyclerViewFragment.newInstance();
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "Selection";
                    case 1:
                        return "Actualités";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                getResources().getDrawable(R.drawable.wallpaper));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                getResources().getDrawable(R.drawable.wallpaper1));
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void updateFly(String dateStart,String dateEnd)
    {
       // mTvFlyBack.setText(dateEnd);
      //  mtvFlyOut.setText(dateStart);
    }

    public void setDate(View view)


    {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                MainPage.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setStartTitle("From");
        dpd.setEndTitle("To");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
         dateStart = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            dateEnd = dayOfMonthEnd+"/"+(monthOfYearEnd+1)+"/"+yearEnd;

        if(dateStart == null && dateEnd == null) {

            Toast.makeText(MainPage.this,"Please choose Dates",Toast.LENGTH_SHORT).show();
        }
        else{
            updateFly(dateStart,dateEnd);
        }



    }

}
