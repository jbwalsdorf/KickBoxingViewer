package com.jeffwalsdorf.kickboxingviewer.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.DateTime;

public class VideoItem implements Parcelable {

    private String title;
    private String desc;
    private String nextPage;

    private String thumbnailDefault;
    private String thumbnailMedium;
    private String thumbnailHigh;

    private String videoId;
    private DateTime publishedAt;

    public VideoItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getThumbnailDefault() {
        return thumbnailDefault;
    }

    public void setThumbnailDefault(String thumbnailDefault) {
        this.thumbnailDefault = thumbnailDefault;
    }

    public String getThumbnailMedium() {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(String thumbnailMedium) {
        this.thumbnailMedium = thumbnailMedium;
    }

    public String getThumbnailHigh() {
        return thumbnailHigh;
    }

    public void setThumbnailHigh(String thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }


    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    protected VideoItem(Parcel in) {
        title = in.readString();
        desc = in.readString();
        nextPage = in.readString();
        thumbnailDefault = in.readString();
        thumbnailMedium = in.readString();
        thumbnailHigh = in.readString();
        videoId = in.readString();
        publishedAt = (DateTime) in.readValue(DateTime.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(nextPage);
        dest.writeString(thumbnailDefault);
        dest.writeString(thumbnailMedium);
        dest.writeString(thumbnailHigh);
        dest.writeString(videoId);
        dest.writeValue(publishedAt);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VideoItem> CREATOR = new Parcelable.Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };
}
