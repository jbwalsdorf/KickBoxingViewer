package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jeff on 5/13/2015.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<VideoItem> mVideoList;
    private Context mContext;
//    private int lastPostion = -1;

    public VideoListAdapter(Context context, List<VideoItem> videoItems) {
        mVideoList = videoItems;
        mContext = context;
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

        viewHolder.videoTitle.setSelected(true);
//        setAnimation(viewHolder.container, i);

    }

//    private void setAnimation(View view, int position) {
//        if (position > lastPostion) {
//            Animation animation =
//                    AnimationUtils.loadAnimation(view.getContext(), R.anim.abc_grow_fade_in_from_bottom);
//            view.startAnimation(animation);
//            lastPostion = position;
//        }
//    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView videoThumb;
        private final TextView videoTitle;
        private final TextView videoDesc;
        private final LinearLayout container;

        public ViewHolder(View view) {
            super(view);
            videoThumb = (ImageView) view.findViewById(R.id.video_thumbnail);
            videoTitle = (TextView) view.findViewById(R.id.video_title);
            videoDesc = (TextView) view.findViewById(R.id.video_list_desc);
            container = (LinearLayout) view.findViewById(R.id.video_list_container);
        }
    }
}
