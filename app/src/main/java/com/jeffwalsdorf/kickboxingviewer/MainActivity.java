package com.jeffwalsdorf.kickboxingviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.FavoritesHelper;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        VideoListFragment.Callback,
        FetchChannelInfo.OnTaskCompleted {

    RecyclerView mNavRecyclerView;
    RecyclerView.Adapter mNavAdapter;
    RecyclerView.LayoutManager mNavLayoutManager;
    DrawerLayout mDrawerLayout;
    List<ChannelItem> mChannelList;

    String channelString =
            "UC136PyHqcUNWl1q5ZHVyR9g," +
                    "UCkyx5g1im6Q1FL26XDxJYBg," +
                    "UCk3THNGRpNmCsRbCaWNeWSA," +
                    "UCKj5FIgxeihLRLDpVqKp_aA," +
                    "UCwsdMe9N96Bmh-EXnCTQ_ng," +
                    "UCtmsXw9ZtWfFnGZdKVTmowA";

    ActionBarDrawerToggle mDrawerToggle;

    Toolbar mToolbar;

    SharedPreferences mSharedPrefs;
    SharedPreferences.Editor mEditor;

    private AdView mAdView;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static GoogleAnalytics mAnalytics;
    public static Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        delete shared prefs for dubug purposes
//        this.getSharedPreferences("VideoCounts", MODE_PRIVATE).edit().clear().apply();
//        this.getSharedPreferences(FavoritesHelper.PREFS_NAME, MODE_PRIVATE).edit().clear().apply();

        mAnalytics = GoogleAnalytics.getInstance(this);
        mAnalytics.setLocalDispatchPeriod(1800);
        mTracker = mAnalytics.newTracker(YouTubeKey.ANALYTICS_KEY);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableAutoActivityTracking(true);
        mTracker.setScreenName("main screen");

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        mAdView.loadAd(adRequest);

        mSharedPrefs = this.getSharedPreferences("VideoCounts", MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();

        refreshChanList();

        mNavRecyclerView = (RecyclerView) findViewById(R.id.drawer_recyclerview);
        mNavRecyclerView.setHasFixedSize(true);

        mNavLayoutManager = new LinearLayoutManager(this);
        mNavRecyclerView.setLayoutManager(mNavLayoutManager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);

        mNavRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (position == 0) {

                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel("Favorites")
                                    .build());

                            mToolbar.setTitle("Favorites");

                            FavoritesHelper favoritesHelper = new FavoritesHelper();
                            ArrayList<VideoItem> favVids = (ArrayList) favoritesHelper.loadFavorites(getApplicationContext());

                            if (favVids != null) {
                                Collections.sort(favVids);
                            }

                            mNavAdapter.notifyItemChanged(position);

                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(VideoListFragment.FAVORITE_VIDS, favVids);

                            VideoListFragment fragment = new VideoListFragment();
                            fragment.setArguments(bundle);

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.main_container, fragment)
                                    .commit();

                            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                            mDrawerToggle.setDrawerIndicatorEnabled(true);

                            mDrawerLayout.closeDrawers();

                        } else {

                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel(mChannelList.get(position - 1).getmTitle())
                                    .build());

                            mToolbar.setTitle(mChannelList.get(position - 1).getmTitle());

                            //Update view counts on nav drawer
                            mEditor.putInt(mChannelList.get(position - 1).getmTitle(),
                                    mChannelList.get(position - 1).getmVideoCount().intValue());
                            mEditor.apply();
                            mNavAdapter.notifyItemChanged(position);
//                            mNavAdapter.notifyDataSetChanged();

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(VideoListFragment.CHANNEL_ITEM,
                                    mChannelList.get(position - 1));

                            VideoListFragment fragment = new VideoListFragment();
                            fragment.setArguments(bundle);

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.main_container, fragment)
                                    .commit();

                            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                            mDrawerToggle.setDrawerIndicatorEnabled(true);

                            mDrawerLayout.closeDrawers();
                        }
                    }
                }));

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        mDrawerToggle.syncState();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.drawer_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshChanList();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);

        if (savedInstanceState == null) {

            getSupportActionBar().setTitle("Choose a channel");
            mDrawerLayout.openDrawer(GravityCompat.START);

//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.place_holder);

            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, new VideoListFragment())
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }

        if (mNavAdapter != null) {
            mNavAdapter.notifyItemChanged(0);
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onItemSelected(VideoItem selectedVideo) {

        Intent intent = new Intent(this, YouTubeActivity.class)
                .putExtra(YouTubeFragment.SELECTED_VIDEO, selectedVideo);
        startActivity(intent);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable(YouTubeFragment.SELECTED_VIDEO,
//                selectedVideo);
//
//        YouTubeFragment fragment = new YouTubeFragment();
//        fragment.setArguments(bundle);
//
//        mDrawerToggle.setDrawerIndicatorEnabled(false);
//
//        getFragmentManager().beginTransaction()
//                .replace(R.id.main_container, fragment)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onTaskCompleted(List<ChannelItem> results) {
        mChannelList = results;
//
//        if (mNavRecyclerView.getAdapter() == null) {
//            mNavAdapter = new NavBarAdapter(getApplicationContext(), mChannelList);
//            mNavRecyclerView.setAdapter(mNavAdapter);
//        } else {
//            mNavAdapter.notifyDataSetChanged();
//            swipeRefreshLayout.setRefreshing(false);
//        }

        mNavAdapter = new NavBarAdapter(getApplicationContext(), mChannelList);
        mNavRecyclerView.setAdapter(mNavAdapter);

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void refreshChanList() {
        FetchChannelInfo ftc = new FetchChannelInfo(getApplicationContext());
        ftc.delegate = this;
        ftc.execute(channelString);
    }
}
