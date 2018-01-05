package com.tbv.akramhussain.thebaiganvines.Web;

import android.app.ListActivity;
import android.os.Bundle;

import com.tbv.akramhussain.thebaiganvines.R;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class Twitter extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        com.twitter.sdk.android.core.Twitter.initialize(this);
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("TheBaiganVines")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);

    }
}