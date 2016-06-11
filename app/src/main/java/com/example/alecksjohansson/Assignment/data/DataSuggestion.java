package com.example.alecksjohansson.Assignment.data;

import android.os.Parcel;

import com.example.alecksjohansson.Assignment.models.SearchSuggestion;

/**
 * Created by alecksjohansson on 6/11/16.
 */
public class DataSuggestion implements com.arlib.floatingsearchview.suggestions.model.SearchSuggestion {

    private String mPlaceName;
    private boolean mIsHistory = false;

    public DataSuggestion(String suggestion) {
        this.mPlaceName = suggestion.toLowerCase();
    }

    public DataSuggestion(Parcel source) {
        this.mPlaceName = source.readString();
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
        return mPlaceName;
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
        dest.writeString(mPlaceName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
