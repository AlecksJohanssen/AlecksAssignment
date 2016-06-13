package com.example.alecksjohansson.Assignment;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.alecksjohansson.Assignment.data.DataSuggestion;
import com.example.alecksjohansson.Assignment.data.DataWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alecksjohansson on 6/11/16.
 */
public class BookingFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private TextView mtvFlyOut;
    private TextView mTvFlyBack;
    private DatePickerDialog dpd;
    private String dateStart;
    private String dateEnd;
    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private boolean mIsDarkSearchTheme = false;


    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private FloatingSearchView mSearchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booking_fragment,null);
        mtvFlyOut = (TextView) v.findViewById(R.id.tvFlyOut);
        mTvFlyBack = (TextView) v.findViewById(R.id.tvFlyBack);
        mSearchView = (FloatingSearchView) v.findViewById(R.id.floating_search_view);
        mSearchResultsList = (RecyclerView) v.findViewById(R.id.search_results_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpFloatingSearch();
        checkDate();
        setDateOnClickListener();

    }
    private void checkDate()
    {
        if(mTvFlyBack != null || mtvFlyOut != null)
        {
            mTvFlyBack.setText(date());
            mtvFlyOut.setText(date());
            Log.d("FLY","FLY"+date());

        }else {
            Log.d("NOTNULL","NOTNULl");
        }
    }
    private void updateFly(String dateStart, String dateEnd)
    {
        mTvFlyBack.setText(dateEnd);
        mtvFlyOut.setText(dateStart);
    }
    private String date()
    {
        String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy ");
        Datetime = dateformat.format(c.getTime());
        System.out.println(Datetime);
        return Datetime;
    }
    public static BookingFragment newInstance() {
        return new BookingFragment();
    }
    public void setDate(View v)
    {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                BookingFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(true);
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
        else {
            updateFly(dateStart, dateEnd);
        }
    }
    private void setUpFloatingSearch()
    {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    mSearchView.showProgress();
                    DatabaseHelper.findSuggestions(getActivity(), newQuery, 5, FIND_SUGGESTION_SIMULATED_DELAY, new DatabaseHelper.OnFindSuggestionsListener() {

                        @Override
                        public void onResults(List<DataSuggestion> results) {

                            mSearchView.swapSuggestions(results);

                            mSearchView.hideProgress();
                        }
                    });
                }
                Log.d("BookingFragment", "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

                DataSuggestion colorSuggestion = (DataSuggestion) searchSuggestion;
                DatabaseHelper.findColors(getActivity(), colorSuggestion.getBody(),
                        new DatabaseHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<DataWrapper> results) {
                                mSearchResultsAdapter.swapData(results);
                            }

                        });
                Log.d("TAG", "onSuggestionClicked()");
            }

            @Override
            public void onSearchAction(String query) {

                DatabaseHelper.findColors(getActivity(), query,
                        new DatabaseHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<DataWrapper> results) {
                                mSearchResultsAdapter.swapData(results);
                            }

                        });
                Log.d("TAG", "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                mSearchView.clearQuery();

                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(DatabaseHelper.getHistory(getActivity(), 3));

                Log.d("TAG", "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                Log.d("TAG", "onFocusCleared()");
            }
        });


        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.action_change_colors) {

                    mIsDarkSearchTheme = true;

                    //demonstrate setting colors for items
                    mSearchView.setBackgroundColor(Color.parseColor("#787878"));
                    mSearchView.setViewTextColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setHintTextColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setActionMenuOverflowColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setMenuItemIconColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setClearBtnColor(Color.parseColor("#e9e9e9"));
                    mSearchView.setDividerColor(Color.parseColor("#BEBEBE"));
                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
                } else {

                    //just print action
                    Toast.makeText(getContext(), item.getTitle(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void setDateOnClickListener()
    {
        mtvFlyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        mTvFlyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
    }
}