package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.style.ReplacementSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.FavoritesHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NavBarAdapter extends RecyclerView.Adapter<NavBarAdapter.ViewHolder> {

    private List<ChannelItem> mChannelList;
    private Context mContext;
    SharedPreferences mSharedPrefs;

    FavoritesHelper mFavHelper;

    DisplayMetrics mMetrics;

    int mThumbSizeDP;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public NavBarAdapter(Context context, List<ChannelItem> channelItems) {
        mContext = context;
        mChannelList = channelItems;
        mSharedPrefs = context.getSharedPreferences("VideoCounts", Context.MODE_PRIVATE);

        mFavHelper = new FavoritesHelper();

        mMetrics = context.getResources().getDisplayMetrics();

        mThumbSizeDP = (int) (80 * mMetrics.density + 0.5f);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position == 0) {

            holder.channelThumb.setVisibility(View.GONE);

            Drawable star = holder.channelThumb.getContext().getResources().getDrawable(R.drawable.ic_star_black_48dp);
            int color = holder.channelThumb.getContext().getResources().getColor(R.color.accent);
//            star.setColorFilter(color, PorterDuff.Mode.SRC_IN);
//            holder.channelThumb.setImageDrawable(star);

            int padding = (int) (16 * mMetrics.density + 0.5f);

            holder.channelThumb.setPadding(padding, padding, padding, padding);
            holder.channelThumb.setVisibility(View.VISIBLE);

            holder.channelName.setText(R.string.favorites);

            holder.newVideosCount.setVisibility(View.GONE);

            int favs;
            if (mFavHelper.loadFavorites(mContext) != null) {
                favs = mFavHelper.loadFavorites(mContext).size();
                holder.newVideosCount.setText(String.valueOf(mFavHelper.loadFavorites(mContext).size()));
            } else {
                holder.newVideosCount.setText("0");
                favs = 0;
            }

            if (favs > 0) {
                star.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } else {
                star.clearColorFilter();
            }

            holder.channelThumb.setImageDrawable(star);
            holder.newVideosCount.setVisibility(View.VISIBLE);

        } else {

            holder.channelThumb.setVisibility(View.GONE);

            if (mMetrics.densityDpi >= DisplayMetrics.DENSITY_400) {

                Picasso.with(mContext).load(mChannelList.get(position - 1).getmThumbnailHigh()).resize(mThumbSizeDP, mThumbSizeDP)
                        .into(holder.channelThumb);
            } else if (mMetrics.densityDpi >= DisplayMetrics.DENSITY_280) {
                Picasso.with(mContext).load(mChannelList.get(position - 1).getmThumbnailMedium()).resize(mThumbSizeDP, mThumbSizeDP)
                        .into(holder.channelThumb);
            } else {
                Picasso.with(mContext).load(mChannelList.get(position - 1).getmThumbnailDefault()).resize(mThumbSizeDP, mThumbSizeDP)
                        .into(holder.channelThumb);
            }

            holder.channelThumb.setPadding(0,0,0,0);

            holder.channelThumb.setVisibility(View.VISIBLE);

            holder.channelName.setVisibility(View.GONE);
            holder.channelName.setText(mChannelList.get(position - 1).getmTitle());
            holder.channelName.setVisibility(View.VISIBLE);

            int nowCount = mChannelList.get(position - 1).getmVideoCount().intValue();
            int thenCount = mSharedPrefs.getInt(mChannelList.get(position - 1).getmTitle(), 0);

            int vidCount = nowCount - thenCount;

            if (vidCount < 1) {
                holder.newVideosCount.setVisibility(View.INVISIBLE);
            } else {
//                Spannable spannable = new SpannableString(String.valueOf(vidCount));
//            spannable.setSpan(new RoundedBackgroundSpan(Color.GRAY, Color.WHITE),
//                    0, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//            holder.newVideosCount.setText(spannable, TextView.BufferType.SPANNABLE);
                holder.newVideosCount.setVisibility(View.GONE);
                holder.newVideosCount.setText(String.valueOf(vidCount));
                holder.newVideosCount.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mChannelList.size() + 1;
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

    public static class RoundedBackgroundSpan extends ReplacementSpan {

        private final int mPadding = 10;
        private int mBackgroundColor;
        private int mTextColor;

        public RoundedBackgroundSpan(int backgroundColor, int textColor) {
            super();
            mBackgroundColor = backgroundColor;
            mTextColor = textColor;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return (int) (mPadding + paint.measureText(text.subSequence(start, end).toString()) + mPadding);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            float width = paint.measureText(text.subSequence(start, end).toString());
            RectF rect = new RectF(x, top + mPadding, x + width + (2 * mPadding), bottom + mPadding);
            paint.setColor(mBackgroundColor);
            canvas.drawRoundRect(rect, mPadding, mPadding, paint);
            paint.setColor(mTextColor);
            canvas.drawText(text, start, end, x + mPadding, y + mPadding, paint);
        }
    }
}
