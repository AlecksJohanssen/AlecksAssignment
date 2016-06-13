package com.example.alecksjohansson.Assignment.data;

/**
 * Created by alecksjohansson on 6/11/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataWrapper implements Parcelable {

    @SerializedName("hex")
    @Expose
    private String hex;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;

    private DataWrapper(Parcel in) {
        hex = in.readString();
        name = in.readString();
        country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hex);
        dest.writeString(name);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @return
     * The hex
     */
    public String getHex() {
        return hex;
    }

    /**
     *
     * @param hex
     * The hex
     */
    public void setHex(String hex) {
        this.hex = hex;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The rgb
     */
    public String getRgb() {
        return country;
    }

    /**
     *
     * @param rgb
     * The rgb
     */
    public void setRgb(String rgb) {
        this.country = country;
    }

    public static final Creator<DataWrapper> CREATOR = new Creator<DataWrapper>() {
        @Override
        public DataWrapper createFromParcel(Parcel in) {
            return new DataWrapper(in);
        }

        @Override
        public DataWrapper[] newArray(int size) {
            return new DataWrapper[size];
        }
    };
}