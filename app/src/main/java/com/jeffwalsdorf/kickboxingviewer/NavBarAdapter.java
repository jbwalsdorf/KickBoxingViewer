package com.jeffwalsdorf.kickboxingviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ReplacementSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        if (vidCount < 1) {
            holder.newVideosCount.setVisibility(View.GONE);
        } else {
            Spannable spannable = new SpannableString(String.valueOf(vidCount));
            spannable.setSpan(new RoundedBackgroundSpan(Color.GRAY, Color.WHITE),
                    0, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            holder.newVideosCount.setText(spannable, TextView.BufferType.SPANNABLE);
        }

//        if (vidCount < 0) {
//            vidCount = 0;
//        }

//        Spannable spannable = new SpannableString(String.valueOf(vidCount));
//        spannable.setSpan(new RoundedBackgroundSpan(Color.GRAY, Color.WHITE),
//                0, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//        holder.newVideosCount.setText(spannable, TextView.BufferType.SPANNABLE);


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
//            canvas.drawRoundRect(rect, mPadding, mPadding, paint);
            paint.setColor(mTextColor);
            canvas.drawText(text, start, end, x + mPadding, y + mPadding, paint);
        }
    }
}
