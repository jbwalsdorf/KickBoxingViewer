package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.os.AsyncTask;

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.YouTubeConnector;

import java.util.List;

/**
 * Created by Jeff on 5/19/2015.
 */
public class FetchChannelInfo extends AsyncTask<String, Void, List<ChannelItem>> {

    private Context mContext;

    public FetchChannelInfo(Context context) {
        mContext = context;
    }

    @Override
    protected List<ChannelItem> doInBackground(String... params) {

        String channels = params[0];
        List<ChannelItem> channelList;

        YouTubeConnector ytc = new YouTubeConnector(mContext);
        channelList = ytc.getChannelInfo(channels);

        return channelList;
    }
}
