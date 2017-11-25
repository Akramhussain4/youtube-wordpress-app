package com.tbv.akramhussain.thebaiganvines.Misc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tbv.akramhussain.thebaiganvines.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void insta1(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/hampireddy96")));
    }

    public void facebook(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/TrendingTech")));
    }
    public void insta(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/akramhussain07")));
    }
    public void twitter(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/akramhussain07")));
    }

}
