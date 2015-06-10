package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffwalsdorf.kickboxingviewer.Utils.FavoritesHelper;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jeff on 5/13/2015.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<VideoItem> mVideoList;
    private Context mContext;
    private FavoritesHelper mFavoritesHelper;

    private Drawable mStar;
    private Drawable mStarBorder;

//    public VideoListAdapter(Context context, List<VideoItem> videoItems,
//                            ChannelItem channel, RecyclerView view) {

    public VideoListAdapter(Context context, List<VideoItem> videoItems) {

        mVideoList = videoItems;
        mContext = context;
        mFavoritesHelper = new FavoritesHelper();

        mStar = context.getResources().getDrawable(R.drawable.ic_star_black_18dp);
        int color = context.getResources().getColor(R.color.accent);
        mStar.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        mStarBorder = context.getResources().getDrawable(R.drawable.ic_star_border_black_18dp);
        color = context.getResources().getColor(R.color.divider);
        mStarBorder.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Picasso.with(mContext).load(mVideoList.get(i).getThumbnailHigh()).into(viewHolder.videoThumb);
        viewHolder.videoTitle.setText(mVideoList.get(i).getTitle());

        viewHolder.videoDesc.setText(mVideoList.get(i).getDesc());

        Date date = new Date(mVideoList.get(i).getPublishedAt().getValue());

        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d h:mm a", Locale.getDefault());
        viewHolder.videoPubDate.setText(format.format(date));

        if (mFavoritesHelper.isFavorited(mContext, mVideoList.get(i).getVideoId())) {
            viewHolder.videoFav.setImageDrawable(mStar);
        } else {
            viewHolder.videoFav.setImageDrawable(mStarBorder);
        }
    }

    @Override
    public int getItemCount() {
        if (mVideoList != null) {
            return mVideoList.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView videoThumb;
        private final TextView videoTitle;
        private final TextView videoDesc;
        private final TextView videoPubDate;
        private final ImageView videoFav;

        public ViewHolder(View view) {
            super(view);
            videoThumb = (ImageView) view.findViewById(R.id.video_thumbnail);
            videoTitle = (TextView) view.findViewById(R.id.video_title);
            videoDesc = (TextView) view.findViewById(R.id.video_list_desc);
            videoPubDate = (TextView) view.findViewById(R.id.video_list_publish_date);
            videoFav = (ImageView) view.findViewById(R.id.video_list_favs);
        }
    }
}
