package com.jeffwalsdorf.kickboxingviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class FetchVideoList extends AsyncTask<String, Void, List<VideoItem>> {
//    public AsyncRetList delegate = null;
    private Context mContext;
    private ProgressDialog dialog;

//    public interface AsyncRetList {
//        void processFinish(List<VideoItem> output);
//    }

    public FetchVideoList(Context context) {
        mContext = context;
        dialog= new ProgressDialog(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected List<VideoItem> doInBackground(String... params) {

        String channel = (String) params[0];
//        Context context = (Context) params[1];
        List<VideoItem> videoList;

        YouTubeConnector ytc = new YouTubeConnector(mContext);
        videoList = ytc.getChannelVideos(channel);


        return videoList;
    }

    @Override
    protected void onPostExecute(List<VideoItem> videoItems) {


        FetchVideoList mFVL = new FetchVideoList(mContext);



        if(dialog.isShowing()){
            dialog.dismiss();
        }

//        delegate.processFinish(videoItems);
    }
}
