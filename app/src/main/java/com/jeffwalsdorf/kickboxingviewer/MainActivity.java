package com.jeffwalsdorf.kickboxingviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.jeffwalsdorf.kickboxingviewer.Utils.ChannelItem;
import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;

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
    CharSequence mDrawerTitle;
    CharSequence mTitle;

    Toolbar mToolbar;

    SharedPreferences mSharedPrefs;
    SharedPreferences.Editor mEditor;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSharedPreferences("VideoCounts", MODE_PRIVATE).edit().clear().apply();

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

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

        mNavRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                mToolbar.setTitle(mChannelList.get(position).getmTitle());

                                mEditor.putInt(mChannelList.get(position).getmTitle(), mChannelList.get(position).getmVideoCount().intValue());
                                mEditor.apply();
                                mNavAdapter.notifyItemChanged(position);


                                Bundle bundle = new Bundle();
                                bundle.putParcelable(VideoListFragment.CHANNEL_ITEM,
                                        mChannelList.get(position));

                                VideoListFragment fragment = new VideoListFragment();
                                fragment.setArguments(bundle);

                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_container, fragment)
                                        .commit();

                                mDrawerLayout.closeDrawers();
                            }
                        }));

        mDrawerLayout.setDrawerListener(mDrawerToggle);

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

//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.place_holder);

            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, new VideoListFragment())
                    .commit();
        }
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
    public void onItemSelected(VideoItem selectedVideo) {

        Intent intent = new Intent(this, YouTubeActivity.class)
                .putExtra(YouTubeFragment.SELECTED_VIDEO, selectedVideo);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<ChannelItem> results) {
        mChannelList = results;

        if (mNavRecyclerView.getAdapter() == null) {
            mNavAdapter = new NavBarAdapter(getApplicationContext(), mChannelList);
            mNavRecyclerView.setAdapter(mNavAdapter);
        } else {
            mNavAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

//        mNavAdapter = new NavBarAdapter(getApplicationContext(), mChannelList);
//        mNavRecyclerView.setAdapter(mNavAdapter);

//        swipeRefreshLayout.setRefreshing(false);
    }

    public void refreshChanList() {
        FetchChannelInfo ftc = new FetchChannelInfo(getApplicationContext());
        ftc.delegate = this;
        ftc.execute(channelString);
    }
}
