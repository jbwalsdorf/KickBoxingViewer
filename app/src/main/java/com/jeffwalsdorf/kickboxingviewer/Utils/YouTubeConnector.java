package com.jeffwalsdorf.kickboxingviewer.Utils;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.jeffwalsdorf.kickboxingviewer.R;
import com.jeffwalsdorf.kickboxingviewer.YouTubeKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeConnector {

    private YouTube youtube;

    private YouTube.Search.List query;

    private YouTube.PlaylistItems.List channelVids;

    private YouTube.Channels.List channelInfo;

    static final String API_KEY = YouTubeKey.DEVELOPER_KEY;
    static final long MAX_VIDEOS = 50;

    public YouTubeConnector(Context context) {

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            channelInfo = youtube.channels().list("contentDetails,snippet,brandingSettings,statistics");
            channelInfo.setKey(API_KEY);
        } catch (IOException e) {
            Log.e("Channel Info", "Could not initialize: " + e);
            e.printStackTrace();
        }

        try {
            channelVids = youtube.playlistItems().list("snippet");
            channelVids.setKey(API_KEY);
        } catch (IOException e) {
            Log.e("Channel Vids", "Could not initialize: " + e);
            e.printStackTrace();
        }

//        try {
//            query = youtube.search().list("id,snippet");
//            query.setKey(API_KEY);
//            query.setType("video");
//            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails)");
//            query.setOrder("date");
//        } catch (IOException e) {
//            Log.d("YouTube Search", "Could not initialize: " + e);
//            e.printStackTrace();
//        }
    }

//    public List<VideoItem> search(String channel, String keywords) {
//        query.setQ(keywords);
//        query.setChannelId(channel);
//
//        try {
//            SearchListResponse response = query.execute();
//            List<SearchResult> results = response.getItems();
//
//            List<VideoItem> items = new ArrayList<>();
//            for (SearchResult result : results) {
//                VideoItem item = new VideoItem();
//                item.setTitle(result.getSnippet().getTitle());
//                item.setDescription(result.getSnippet().getDescription());
//                item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());
//                item.setId(result.getId().getVideoId());
//                items.add(item);
//            }
//            return items;
//
//        } catch (IOException e) {
//            Log.e("YouTubeConnector", "Could not search: " + e);
//            return null;
//        }
//    }

    public List<ChannelItem> getChannelInfo(String channelList) {
        channelInfo.setId(channelList);

        try {
            ChannelListResponse response = channelInfo.execute();
            List<Channel> results = response.getItems();

            List<ChannelItem> items = new ArrayList<>();
            for (Channel result : results) {
                ChannelItem item = new ChannelItem();

                item.setmId(result.getId());
                item.setmTitle(result.getSnippet().getTitle());
                item.setmDesc(result.getSnippet().getDescription());
                item.setmUploadsKey(result.getContentDetails().getRelatedPlaylists().getUploads());

                item.setmThumbnailDefault(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setmThumbnailMedium(result.getSnippet().getThumbnails().getMedium().getUrl());
                item.setmThumbnailHigh(result.getSnippet().getThumbnails().getHigh().getUrl());

                item.setmBannerMobileDefault(result.getBrandingSettings().getImage().getBannerMobileImageUrl());
                item.setmBannerMobileLow(result.getBrandingSettings().getImage().getBannerMobileLowImageUrl());
                item.setmBannerMobileMed(result.getBrandingSettings().getImage().getBannerMobileMediumHdImageUrl());
                item.setmBannerMobileHD(result.getBrandingSettings().getImage().getBannerMobileHdImageUrl());
                item.setmBannerMobileExtraHD(result.getBrandingSettings().getImage().getBannerMobileExtraHdImageUrl());

                item.setmBannerTVDefault(result.getBrandingSettings().getImage().getBannerTvImageUrl());
                item.setmBannerTVLow(result.getBrandingSettings().getImage().getBannerTvLowImageUrl());
                item.setmBannerTVMed(result.getBrandingSettings().getImage().getBannerTvMediumImageUrl());
                item.setmBannerTVHigh(result.getBrandingSettings().getImage().getBannerTvHighImageUrl());

                item.setmVideoCount(result.getStatistics().getVideoCount());

                items.add(item);
            }

            return items;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<VideoItem> getChannelVideos(String playlistId) {
        channelVids.setMaxResults(MAX_VIDEOS);
        channelVids.setPlaylistId(playlistId);

        try {
            PlaylistItemListResponse response = channelVids.execute();
            List<PlaylistItem> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();
            for (PlaylistItem result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDesc(result.getSnippet().getDescription());
                item.setNextPage(response.getNextPageToken());
                item.setThumbnailDefault(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setThumbnailMedium(result.getSnippet().getThumbnails().getMedium().getUrl());
                item.setThumbnailHigh(result.getSnippet().getThumbnails().getHigh().getUrl());
//                item.setThumbnailStandard(result.getSnippet().getThumbnails().getStandard().getUrl());
                item.setVideoId(result.getSnippet().getResourceId().getVideoId());
                item.setPublishedAt(result.getSnippet().getPublishedAt());
                items.add(item);
            }

            return items;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

