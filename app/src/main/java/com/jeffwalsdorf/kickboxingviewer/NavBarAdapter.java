package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NavBarAdapter extends RecyclerView.Adapter<NavBarAdapter.ViewHolder> {

    private List<ChannelItem> mChannelList;
    private Context mContext;
    SharedPreferences mSharedPrefs;
    SharedPreferences.Editor mEditor;


    public NavBarAdapter(Context context, List<ChannelItem> channelItems) {
        mContext = context;
        mChannelList = channelItems;
        mSharedPrefs = context.getSharedPreferences("VideoCounts", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mChannelList.get(position).getmThumbnailHigh())
                .into(holder.channelThumb);

        holder.channelName.setText(mChannelList.get(position).getmTitle());

        int nowCount = mChannelList.get(position).getmVideoCount().intValue();
        int thenCount = mSharedPrefs.getInt(mChannelList.get(position).getmTitle(), 0);

        int vidCount = nowCount - thenCount;
        if (vidCount < 0) {
            vidCount = 0;
        }


        Spannable spannable = new SpannableString(String.valueOf(vidCount));
        spannable.setSpan(new BackgroundColorSpan(R.color.accent),0,1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        holder.newVideosCount.setText(spannable);


    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView channelThumb;
        private final TextView channelName;
        private final TextView newVideosCount;

        public ViewHolder(View view) {
            super(view);
            channelThumb = (ImageView) view.findViewById(R.id.drawer_channel_icon);
            channelName = (TextView) view.findViewById(R.id.drawer_channel_name);
            newVideosCount = (TextView) view.findViewById(R.id.drawer_channel_new_vids);
        }
    }

    private void setAnimation(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        view.startAnimation(animation);
    }
}
