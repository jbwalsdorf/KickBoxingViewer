package com.jeffwalsdorf.kickboxingviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;



/**
 * Created by Jeff on 6/9/2015.
 */
public class YouTubeActivity extends AppCompatActivity {

    Toolbar mToolbar;
    private ShareActionProvider mShareActionProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();

            bundle.putParcelable(YouTubeFragment.SELECTED_VIDEO,
                    getIntent().getParcelableExtra(YouTubeFragment.SELECTED_VIDEO));

            YouTubeFragment fragment = new YouTubeFragment();
            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.video_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_youtube, menu);
//
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//
//        mShareActionProvider.setShareIntent(createShareForecastIntent());
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private Intent createShareForecastIntent(){
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
////        shareIntent.putExtra(Intent.EXTRA_TEXT,);
//        return shareIntent;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}