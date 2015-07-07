package com.jeffwalsdorf.kickboxingviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
    public static final String FAVORITE_VIDS = "favoriteVids";

    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<VideoItem> mVideoList;
    private ChannelItem mChannel;

    private String playlist;

    @Override
    public void onTaskCompleted(List<VideoItem> results) {
        mVideoList = results;
        mAdapter = new VideoListAdapter(getActivity(), mVideoList);
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

        } else if (arguments != null && arguments.containsKey(FAVORITE_VIDS)) {
            mVideoList = arguments.getParcelableArrayList(FAVORITE_VIDS);
            mAdapter = new VideoListAdapter(getActivity(), mVideoList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView;

        mRootView = inflater.inflate(R.layout.youtube_video_list, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mChannel != null) {

            int density = getActivity().getResources().getDisplayMetrics().densityDpi;

            if (density >= DisplayMetrics.DENSITY_XHIGH) {
                Picasso.with(getActivity()).load(mChannel.getmBannerMobileExtraHD()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
                Picasso.with(getActivity()).load(mChannel.getmThumbnailHigh()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
            } else if (density >= DisplayMetrics.DENSITY_HIGH) {
                Picasso.with(getActivity()).load(mChannel.getmBannerMobileHD()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
                Picasso.with(getActivity()).load(mChannel.getmThumbnailHigh()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
            } else if (density >= DisplayMetrics.DENSITY_MEDIUM) {
                Picasso.with(getActivity()).load(mChannel.getmBannerMobileMed()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
                Picasso.with(getActivity()).load(mChannel.getmThumbnailMedium()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
            } else if (density >= DisplayMetrics.DENSITY_LOW) {
                Picasso.with(getActivity()).load(mChannel.getmBannerMobileLow()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
                Picasso.with(getActivity()).load(mChannel.getmThumbnailDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
            } else {
                Picasso.with(getActivity()).load(mChannel.getmBannerMobileDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
                Picasso.with(getActivity()).load(mChannel.getmThumbnailDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
            }

//            Picasso.with(getActivity()).load(mChannel.getmBannerMobileDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_banner));
//            Picasso.with(getActivity()).load(mChannel.getmThumbnailDefault()).into((ImageView) mRootView.findViewById(R.id.channel_header_icon));
        } else {
            mRecyclerView.setAdapter(mAdapter);
            mRootView.findViewById(R.id.channel_header_banner).setVisibility(View.GONE);
            mRootView.findViewById(R.id.channel_header_icon).setVisibility(View.GONE);
        }

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


        return mRootView;
    }

    @Override
    public void onResume() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        super.onResume();
    }
}
