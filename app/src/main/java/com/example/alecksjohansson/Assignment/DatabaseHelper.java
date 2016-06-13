package com.example.alecksjohansson.Assignment;

/**
 * Created by alecksjohansson on 6/11/16.
 */
import android.content.Context;
import android.widget.Filter;

import com.example.alecksjohansson.Assignment.data.DataSuggestion;
import com.example.alecksjohansson.Assignment.data.DataWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseHelper {

    private static final String COLORS_FILE_NAME = "colors.json";

    private static List<DataWrapper> sDataWrapper = new ArrayList<>();

    private static List<DataSuggestion> sDataSuggestion =
            new ArrayList<>(Arrays.asList(
                    new DataSuggestion("Singapore"),
                    new DataSuggestion("Viet Nam"),
                    new DataSuggestion("Thailand"),
                    new DataSuggestion("United States"),
                    new DataSuggestion("Malaysia")));

    public interface OnFindColorsListener {
        void onResults(List<DataWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<DataSuggestion> results);
    }

    public static List<DataSuggestion> getHistory(Context context, int count) {

        List<DataSuggestion> suggestionList = new ArrayList<>();
        DataSuggestion mDataSuggestion;
        for (int i = 0; i < sDataSuggestion.size(); i++) {
            mDataSuggestion = sDataSuggestion.get(i);
            mDataSuggestion.setIsHistory(true);
            suggestionList.add(mDataSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (DataSuggestion colorSuggestion : sDataSuggestion) {
            colorSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DatabaseHelper.resetSuggestionsHistory();
                List<DataSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (DataSuggestion suggestion : sDataSuggestion) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<DataSuggestion>() {
                    @Override
                    public int compare(DataSuggestion lhs, DataSuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<DataSuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findColors(Context context, String query, final OnFindColorsListener listener) {
        initColorWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<DataWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (DataWrapper color : sDataWrapper) {
                        if (color.getName().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(color);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<DataWrapper>) results.values);
                }
            }
        }.filter(query);

    }

    private static void initColorWrapperList(Context context) {

        if (sDataWrapper.isEmpty()) {
            String jsonString = loadJson(context);
            sDataWrapper = deserializeColors(jsonString);
        }
    }

    private static String loadJson(Context context) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private static List<DataWrapper> deserializeColors(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<DataWrapper>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }

}