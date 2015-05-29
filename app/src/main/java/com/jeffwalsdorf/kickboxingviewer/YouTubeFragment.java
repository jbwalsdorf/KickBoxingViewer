package com.jeffwalsdorf.kickboxingviewer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;

public class YouTubeFragment extends Fragment {

    private FragmentActivity mContext;

    String vidId;
    private TextView vidTitle;
    private TextView vidDesc;

    public static final String API_KEY = YouTubeKey.DEVELOPER_KEY;

    public static final String SELECTED_VIDEO = "selected_video";

//    public static YouTubeFragment newInstance(String video_id) {
//        YouTubeFragment f = new YouTubeFragment();
//        Bundle args = new Bundle();
//        args.putString(index, video_id);
//        f.setArguments(args);
//        return f;
//    }

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

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(SELECTED_VIDEO)) {

            VideoItem videoItem = arguments.getParcelable(SELECTED_VIDEO);

            vidId= videoItem.getVideoId();
            vidTitle.setText(videoItem.getTitle());

            vidDesc.setText(Html.fromHtml(videoItem.getDesc()));
            vidDesc.setMovementMethod(LinkMovementMethod.getInstance());
        }

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
}
