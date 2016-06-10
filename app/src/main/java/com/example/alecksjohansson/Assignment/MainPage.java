package com.example.alecksjohansson.Assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog dpd;
    String date;
    Calendar[] dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Card card = new Card(this);
        CardHeader header = new CardHeader(getApplicationContext());
        CardThumbnail thumb = new CardThumbnail(this.getApplicationContext());
        header.setTitle("Alecks Johanssen");
        thumb.setDrawableResource(R.drawable.icon_travel);
        card.addCardThumbnail(thumb);
        card.addCardHeader(header);
        CardViewNative cardView = (CardViewNative) findViewById(R.id.list_cardId);
        cardView.setCard(card);


    }
    private void setDate()
    {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                MainPage.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dates = new Calendar[13];
        dpd.setHighlightedDays(dates,dates);
        dpd.setThemeDark(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setStartTitle("From");
        dpd.setEndTitle("To");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
         date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

    }

}
