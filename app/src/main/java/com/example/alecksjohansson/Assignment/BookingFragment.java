package com.example.alecksjohansson.Assignment;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by alecksjohansson on 6/11/16.
 */
public class BookingFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private TextView mtvFlyOut;
    private TextView mTvFlyBack;
    private DatePickerDialog dpd;
    private FragmentActivity myContext;
    private String dateStart;
    private String dateEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booking_fragment,null);
        mtvFlyOut = (TextView) v.findViewById(R.id.tvFlyOut);
        mTvFlyBack = (TextView) v.findViewById(R.id.tvFlyBack);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDate();
    }

    private void updateFly(String dateStart, String dateEnd)
    {
        mTvFlyBack.setText(dateEnd);
        mtvFlyOut.setText(dateStart);
    }
    public static BookingFragment newInstance() {
        return new BookingFragment();
    }
    public void setDate()
    {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                BookingFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.setAccentColor(R.color.purple);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        dpd.setStartTitle("From");
        dpd.setEndTitle("To");
    }
    @SuppressLint("NewApi")
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        dateStart = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        dateEnd = dayOfMonthEnd+"/"+(monthOfYearEnd+1)+"/"+yearEnd;

        if(dateStart == null && dateEnd == null) {

            Toast.makeText(getContext(),"Please choose Dates",Toast.LENGTH_SHORT).show();
        }
        else{
            updateFly(dateStart,dateEnd);
        }



    }
}
