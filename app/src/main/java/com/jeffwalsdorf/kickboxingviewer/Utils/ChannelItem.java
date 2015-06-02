package com.jeffwalsdorf.kickboxingviewer.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;

/**
 * Created by Jeff on 5/18/2015.
 */
public class ChannelItem implements Parcelable {

    private String mId;
    private String mTitle;
    private String mDesc;
    private String mUploadsKey;

    private String mThumbnailDefault;
    private String mThumbnailMedium;
    private String mThumbnailHigh;

    private String mBannerMobileDefault;
    private String mBannerMobileLow;
    private String mBannerMobileMed;
    private String mBannerMobileHD;
    private String mBannerMobileExtraHD;

    private String mBannerTVDefault;
    private String mBannerTVLow;
    private String mBannerTVMed;
    private String mBannerTVHigh;

//    private long mVideoCount;

    private BigInteger mVideoCount;

    public ChannelItem() {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmUploadsKey() {
        return mUploadsKey;
    }

    public void setmUploadsKey(String mUploadsKey) {
        this.mUploadsKey = mUploadsKey;
    }

    public String getmThumbnailDefault() {
        return mThumbnailDefault;
    }

    public void setmThumbnailDefault(String mThumbnailDefault) {
        this.mThumbnailDefault = mThumbnailDefault;
    }

    public String getmThumbnailMedium() {
        return mThumbnailMedium;
    }

    public void setmThumbnailMedium(String mThumbnailMedium) {
        this.mThumbnailMedium = mThumbnailMedium;
    }

    public String getmThumbnailHigh() {
        return mThumbnailHigh;
    }

    public void setmThumbnailHigh(String mThumbnailHigh) {
        this.mThumbnailHigh = mThumbnailHigh;
    }

    public String getmBannerMobileDefault() {
        return mBannerMobileDefault;
    }

    public void setmBannerMobileDefault(String mBannerMobileDefault) {
        this.mBannerMobileDefault = mBannerMobileDefault;
    }

    public String getmBannerMobileLow() {
        return mBannerMobileLow;
    }

    public void setmBannerMobileLow(String mBannerMobileLow) {
        this.mBannerMobileLow = mBannerMobileLow;
    }

    public String getmBannerMobileMed() {
        return mBannerMobileMed;
    }

    public void setmBannerMobileMed(String mBannerMobileMed) {
        this.mBannerMobileMed = mBannerMobileMed;
    }

    public String getmBannerMobileHD() {
        return mBannerMobileHD;
    }

    public void setmBannerMobileHD(String mBannerMobileHD) {
        this.mBannerMobileHD = mBannerMobileHD;
    }

    public String getmBannerMobileExtraHD() {
        return mBannerMobileExtraHD;
    }

    public void setmBannerMobileExtraHD(String mBannerMobileExtraHD) {
        this.mBannerMobileExtraHD = mBannerMobileExtraHD;
    }

    public String getmBannerTVDefault() {
        return mBannerTVDefault;
    }

    public void setmBannerTVDefault(String mBannerTVDefault) {
        this.mBannerTVDefault = mBannerTVDefault;
    }

    public String getmBannerTVLow() {
        return mBannerTVLow;
    }

    public void setmBannerTVLow(String mBannerTVLow) {
        this.mBannerTVLow = mBannerTVLow;
    }

    public String getmBannerTVMed() {
        return mBannerTVMed;
    }

    public void setmBannerTVMed(String mBannerTVMed) {
        this.mBannerTVMed = mBannerTVMed;
    }

    public String getmBannerTVHigh() {
        return mBannerTVHigh;
    }

    public void setmBannerTVHigh(String mBannerTVHigh) {
        this.mBannerTVHigh = mBannerTVHigh;
    }

    public BigInteger getmVideoCount() {
        return mVideoCount;
    }

    public void setmVideoCount(BigInteger mVideoCount) {
        this.mVideoCount = mVideoCount;
    }

    protected ChannelItem(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDesc = in.readString();
        mUploadsKey = in.readString();
        mThumbnailDefault = in.readString();
        mThumbnailMedium = in.readString();
        mThumbnailHigh = in.readString();
        mBannerMobileDefault = in.readString();
        mBannerMobileLow = in.readString();
        mBannerMobileMed = in.readString();
        mBannerMobileHD = in.readString();
        mBannerMobileExtraHD = in.readString();
        mBannerTVDefault = in.readString();
        mBannerTVLow = in.readString();
        mBannerTVMed = in.readString();
        mBannerTVHigh = in.readString();
        mVideoCount = (BigInteger) in.readValue(BigInteger.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDesc);
        dest.writeString(mUploadsKey);
        dest.writeString(mThumbnailDefault);
        dest.writeString(mThumbnailMedium);
        dest.writeString(mThumbnailHigh);
        dest.writeString(mBannerMobileDefault);
        dest.writeString(mBannerMobileLow);
        dest.writeString(mBannerMobileMed);
        dest.writeString(mBannerMobileHD);
        dest.writeString(mBannerMobileExtraHD);
        dest.writeString(mBannerTVDefault);
        dest.writeString(mBannerTVLow);
        dest.writeString(mBannerTVMed);
        dest.writeString(mBannerTVHigh);
        dest.writeValue(mVideoCount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChannelItem> CREATOR = new Parcelable.Creator<ChannelItem>() {
        @Override
        public ChannelItem createFromParcel(Parcel in) {
            return new ChannelItem(in);
        }

        @Override
        public ChannelItem[] newArray(int size) {
            return new ChannelItem[size];
        }
    };
}