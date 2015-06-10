package com.jeffwalsdorf.kickboxingviewer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.jeffwalsdorf.kickboxingviewer.Utils.FavoritesHelper;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;

public class YouTubeFragment extends Fragment {

    private FragmentActivity mContext;

    String vidId;
    private TextView vidTitle;
    private TextView vidDesc;
    private CheckBox favButton;
    private VideoItem mVideoItem;


    public static final String API_KEY = YouTubeKey.DEVELOPER_KEY;

    public static final String SELECTED_VIDEO = "selected_video";

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof FragmentActivity) {
            mContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        View rootView = layoutInflater.inflate(R.layout.youtube_fragment, viewGroup, false);
        vidDesc = (TextView) rootView.findViewById(R.id.video_desc);
        vidTitle = (TextView) rootView.findViewById(R.id.video_title);
        favButton = (CheckBox) rootView.findViewById(R.id.add_to_favorites);

        changeFavColor();

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(SELECTED_VIDEO)) {

            mVideoItem = arguments.getParcelable(SELECTED_VIDEO);
            vidId = mVideoItem.getVideoId();
            vidTitle.setText(mVideoItem.getTitle());
            vidDesc.setText(Html.fromHtml(mVideoItem.getDesc()));
            vidDesc.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mVideoItem.getTitle());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            FavoritesHelper mFavoritesHelper;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFavoritesHelper = new FavoritesHelper();

                if (isChecked) {
                    changeFavColor();
                    mFavoritesHelper.addFavorite(mContext, mVideoItem);
                } else {
                    changeFavColor();
                    mFavoritesHelper.removeFavorite(mContext, mVideoItem);
                }
            }
        });

        YouTubePlayerFragment playerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, playerFragment).commit();

        playerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.cueVideo(vidId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        return rootView;
    }

    private void changeFavColor(){
        int color;
        Drawable star = getResources().getDrawable(R.drawable.favorite_button);


        if(favButton.isChecked()){
            color = getResources().getColor(R.color.accent);
            favButton.setTextColor(color);
            star.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            favButton.setButtonDrawable(star);


        } else {
            color = getResources().getColor(R.color.primary_text);
            favButton.setTextColor(color);
            star.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            favButton.setButtonDrawable(star);
        }
    }
}
