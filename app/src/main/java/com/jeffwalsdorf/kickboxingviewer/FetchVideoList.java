package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class FetchVideoList extends AsyncTask<Object, Void, List<VideoItem>> {
    public AsyncRetList delegate = null;

    public interface AsyncRetList {
        void processFinish(List<VideoItem> output);
    }

    @Override
    protected List<VideoItem> doInBackground(Object... params) {

        String channel = (String) params[0];
        Context context = (Context) params[1];
        List<VideoItem> videoList;

        YouTubeConnector ytc = new YouTubeConnector(context);
        videoList = ytc.getChannelVideos(channel);

        return videoList;
    }

    @Override
    protected void onPostExecute(List<VideoItem> videoItems) {

        delegate.processFinish(videoItems);
    }
}
