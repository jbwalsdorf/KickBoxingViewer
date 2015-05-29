package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Jeff on 5/20/2015.
 */

public class NavBarAdapter extends RecyclerView.Adapter<NavBarAdapter.ViewHolder> {

    private List<ChannelItem> mChannelList;
    private Context mContext;

    public NavBarAdapter(Context context, List<ChannelItem> channelItems) {
        mContext = context;
        mChannelList = channelItems;
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

//        setAnimation(holder.navCardView,position);
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView channelThumb;
        private final TextView channelName;
        private final CardView navCardView;

        public ViewHolder(View view) {
            super(view);
            channelThumb = (ImageView) view.findViewById(R.id.drawer_channel_icon);
            channelName = (TextView) view.findViewById(R.id.drawer_channel_name);
            navCardView = (CardView) view.findViewById(R.id.nav_card_view);
        }
    }

    private void setAnimation(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        view.startAnimation(animation);
    }
}
