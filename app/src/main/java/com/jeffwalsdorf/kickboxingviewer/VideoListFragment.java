package com.jeffwalsdorf.kickboxingviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class VideoListFragment extends Fragment {

    private static final String LOG_TAG = VideoListFragment.class.getSimpleName();

    public static final String CHANNEL_ITEM = "channelItem";

    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<VideoItem> mVideoList;

    private android.support.v7.app.ActionBar mActionBar;

    private String playlist;

    public interface Callback {
        void onItemSelected(VideoItem selectedVideo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(CHANNEL_ITEM)) {
            ChannelItem channelItem = arguments.getParcelable(CHANNEL_ITEM);

            playlist = channelItem.getmUploadsKey();
        } else {
            playlist = "UUKj5FIgxeihLRLDpVqKp_aA";
        }

        FetchVideoList fvl = new FetchVideoList(getActivity());

        try {
            mVideoList = fvl.execute(playlist).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_video_list, container, false);

        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoListAdapter(getActivity(), mVideoList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), mVideoList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                ((Callback) getActivity()).onItemSelected(mVideoList.get(position));

            }
        }));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentFirstVisibleItem =
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


                if (currentFirstVisibleItem > mLastFirstVisibleItem && mActionBar.isShowing()) {

                    mActionBar.hide();

                } else if (currentFirstVisibleItem < mLastFirstVisibleItem && !mActionBar.isShowing()) {

                    mActionBar.show();
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;
            }
        });

        return rootView;
    }
}
