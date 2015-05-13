package com.jeffwalsdorf.kickboxingviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jeff on 5/13/2015.
 */
public class VideoListFragment extends Fragment {

    private static final String LOG_TAG = VideoListFragment.class.getSimpleName();

    protected RecyclerView mRecyclerView;
    protected VideoListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<VideoItem> mVideoList;

    private String channelId = "UC136PyHqcUNWl1q5ZHVyR9g";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchVideoList fvl = new FetchVideoList(getActivity());
        try {
            mVideoList = fvl.execute(channelId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_video_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoListAdapter(getActivity(), mVideoList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;

    }

//    @Override
//    public void processFinish(List<VideoItem> output) {
//        mVideoList = output;
//    }
}
