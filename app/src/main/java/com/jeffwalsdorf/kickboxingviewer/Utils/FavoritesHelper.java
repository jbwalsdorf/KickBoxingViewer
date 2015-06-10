package com.jeffwalsdorf.kickboxingviewer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesHelper {

    public static final String PREFS_NAME = "FAVORITES_LIST";
    public static final String FAVORITES = "FAV_VIDEO_LIST";


    public FavoritesHelper() {
        super();
    }

    public boolean isFavorited(Context context, String vidId) {
        List<VideoItem> favorites = loadFavorites(context);
        if (favorites != null) {

            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getVideoId().equals(vidId)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void storeFavorites(Context context, List<VideoItem> favorites) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
    }

    public List<VideoItem> loadFavorites(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        List<VideoItem> favorites;

        if (preferences.contains(FAVORITES)) {
            String jsonFavorites = preferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            Type type = new TypeToken<List<VideoItem>>() {
            }.getType();
            favorites = gson.fromJson(jsonFavorites, type);

        } else {
            return null;
        }

        return favorites;
    }

    public void addFavorite(Context context, VideoItem videoItem) {
        List<VideoItem> favorites = loadFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<>();
        }
        favorites.add(videoItem);
        storeFavorites(context, favorites);
    }

    public void removeFavorite(Context context, VideoItem videoItem) {
        List<VideoItem> favorites = loadFavorites(context);
        if (favorites != null) {

            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getVideoId().equals(videoItem.getVideoId())) {
                    favorites.remove(i);
                    i--;
                }
            }

            storeFavorites(context, favorites);
        }
    }
}
