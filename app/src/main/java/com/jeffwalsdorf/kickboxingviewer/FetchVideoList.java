package com.jeffwalsdorf.kickboxingviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.YouTubeConnector;

import java.util.List;

public class FetchVideoList extends AsyncTask<String, Void, List<VideoItem>> {
    public OnTaskCompleted delegate = null;
    private Context mContext;
    private ProgressDialog dialog;

    public interface OnTaskCompleted {
        void onTaskCompleted(List<VideoItem> results);
    }

    public FetchVideoList(Context context) {
        mContext = context;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading videos...");
        dialog.show();
    }

    @Override
    protected List<VideoItem> doInBackground(String... params) {

        String channel = params[0];
        List<VideoItem> videoList;

        YouTubeConnector ytc = new YouTubeConnector(mContext);
        videoList = ytc.getChannelVideos(channel);

        return videoList;
    }

    @Override
    protected void onPostExecute(List<VideoItem> videoItems) {

        delegate.onTaskCompleted(videoItems);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }
}
