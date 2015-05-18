package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeConnector {

    private YouTube youtube;
    private YouTube.Search.List query;
    private YouTube.Search.List channelVids;

    static final String API_KEY = YouTubeKey.DEVELOPER_KEY;
    static final long MAX_VIDEOS = 50;

    public YouTubeConnector(Context context) {

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            channelVids = youtube.search().list("id,snippet");
            channelVids.setKey(API_KEY);
            channelVids.setType("video");
            channelVids.setMaxResults(MAX_VIDEOS);
            channelVids.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails)");
            channelVids.setOrder("date");

        } catch (IOException e) {
            Log.e("Channel Vids", "Could not initialize: " + e);
            e.printStackTrace();
        }

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(API_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails)");
            query.setOrder("date");
        } catch (IOException e) {
            Log.d("YouTube Search", "Could not initialize: " + e);
            e.printStackTrace();
        }
    }

    public List<VideoItem> search(String channel, String keywords) {
        query.setQ(keywords);
        query.setChannelId(channel);

        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            return items;

        } catch (IOException e) {
            Log.e("YouTubeConnector", "Could not search: " + e);
            return null;
        }
    }

    public List<VideoItem> getChannelVideos(String channelId) {
        channelVids.setChannelId(channelId);

        try {
            SearchListResponse response = channelVids.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }

            return items;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

