package com.jeffwalsdorf.kickboxingviewer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.jeffwalsdorf.kickboxingviewer.Utils.VideoItem;


public class MainActivity extends AppCompatActivity implements
        VideoListFragment.Callback {

    private DrawerLayout mDrawerLayout;
    private ListView mChannelList;
    private String[] mChannelNames;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchChannelInfo fci = new FetchChannelInfo(getApplicationContext());


//        mTitle = mDrawerTitle = getTitle();
//
//        mChannelNames = getResources().getStringArray(R.array.channel_names);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mChannelList = (ListView) findViewById(R.id.channel_list);
//
//        mChannelList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mChannelNames));
//
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_dr)


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, new VideoListFragment())
                    .commit();
        }
    }

    private void channelChanger(int position) {
        Fragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putInt(VideoListFragment.CHANNEL_ITEM,position);
        fragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_container,fragment)
                .commit();

        mChannelList.setItemChecked(position,true);
        mDrawerLayout.closeDrawer(mChannelList);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(VideoItem selectedVideo) {

        Intent intent = new Intent(this, YouTubeActivity.class)
                .putExtra(YouTubeFragment.SELECTED_VIDEO, selectedVideo);
        startActivity(intent);
    }
}
