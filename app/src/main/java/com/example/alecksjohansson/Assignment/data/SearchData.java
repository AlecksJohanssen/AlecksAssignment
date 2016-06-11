package com.example.alecksjohansson.Assignment.data;

import android.os.Parcel;

import com.example.alecksjohansson.Assignment.models.SearchSuggestion;

/**
 * Created by alecksjohansson on 6/11/16.
 */
public class SearchData implements SearchSuggestion {

    private String mColorName;
    private boolean mIsHistory = false;

    public SearchData(String suggestion) {
        this.mColorName = suggestion.toLowerCase();
    }

    public SearchData(Parcel source) {
        this.mColorName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mColorName;
    }

    public static final Creator<DataSuggestion> CREATOR = new Creator<DataSuggestion>() {
        @Override
        public DataSuggestion createFromParcel(Parcel in) {
            return new DataSuggestion(in);
        }

        @Override
        public DataSuggestion[] newArray(int size) {
            return new DataSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mColorName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
