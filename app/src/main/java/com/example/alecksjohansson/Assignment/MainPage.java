package com.example.alecksjohansson.Assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog dpd;

    String dateStart;
    String dateEnd;
    TextView mtvFlyOut;
    TextView mTvFlyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mtvFlyOut = (TextView) findViewById(R.id.tvFlyOut);
        mTvFlyBack = (TextView) findViewById(R.id.tvFlyBack);


    }private void updateFly(String dateStart,String dateEnd)
    {
        mTvFlyBack.setText(dateEnd);
        mtvFlyOut.setText(dateStart);
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
