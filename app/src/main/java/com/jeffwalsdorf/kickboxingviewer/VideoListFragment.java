package com.jeffwalsdorf.kickboxingviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListFragment extends Fragment implements
        FetchVideoList.OnTaskCompleted {

    private static final String LOG_TAG = VideoListFragment.class.getSimpleName();

    public static final String CHANNEL_ITEM = "channelItem";

    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<VideoItem> mVideoList;
    private ChannelItem mChannel;

//    private android.support.v7.app.ActionBar mActionBar;

    private String playlist;

    @Override
    public void onTaskCompleted(List<VideoItem> results) {
        mVideoList = results;
        mAdapter = new VideoListAdapter(getActivity(), mVideoList, mChannel, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface Callback {
        void onItemSelected(VideoItem selectedVideo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(CHANNEL_ITEM)) {
            mChannel = arguments.getParcelable(CHANNEL_ITEM);

            playlist = mChannel.getmUploadsKey();

            FetchVideoList fvl = new FetchVideoList(getActivity());

            fvl.delegate = this;
            fvl.execute(playlist);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView;

        if (mChannel != null) {

            mRootView = inflater.inflate(R.layout.youtube_video_list, container, false);

//            View rootView = inflater.inflate(R.layout.youtube_video_list, container, false);

            Picasso.with(getActivity()).load(mChannel.getmBannerMobileDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
            Picasso.with(getActivity()).load(mChannel.getmThumbnailDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));


//        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    ((Callback) getActivity()).onItemSelected(mVideoList.get(position));
                }
            }));

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            int mLastFirstVisibleItem = 0;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int currentFirstVisibleItem =
//                        ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//
//
//                if (currentFirstVisibleItem > mLastFirstVisibleItem && mActionBar.isShowing()) {
//
//                    mActionBar.hide();
//
//
//
//                } else if (currentFirstVisibleItem < mLastFirstVisibleItem && !mActionBar.isShowing()) {
//
//                    mActionBar.show();
//                }
//
//                mLastFirstVisibleItem = currentFirstVisibleItem;
//            }
//        });
        } else {
            mRootView = inflater.inflate(R.layout.video_list_placeholder, container, false);
//            Picasso.with(getActivity()).load(R.drawable.place_holder).into((ImageView)mRootView.findViewById(R.id.video_list_placeholder_image));
        }

        return mRootView;
    }
}
