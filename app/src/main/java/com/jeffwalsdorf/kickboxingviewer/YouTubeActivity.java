package com.jeffwalsdorf.kickboxingviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jeff on 5/14/2015.
 */
public class YouTubeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();

            bundle.putParcelable(YouTubeFragment.SELECTED_VIDEO,
                    getIntent().getParcelableExtra(YouTubeFragment.SELECTED_VIDEO));

            YouTubeFragment fragment = new YouTubeFragment();
            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.video_container,fragment)
                    .commit();
        }
    }
}
