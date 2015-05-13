package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jeff on 5/13/2015.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<VideoItem> mVideoList;
    private Context mContext;

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
        Picasso.with(mContext).load(mVideoList.get(i).getThumbnailURL()).into(viewHolder.imageView);
        viewHolder.textView.setText(mVideoList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.video_thumbnail);
            textView = (TextView) view.findViewById(R.id.video_title);
        }
    }
}
