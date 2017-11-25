package com.tbv.akramhussain.thebaiganvines.Web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.tbv.akramhussain.thebaiganvines.Misc.DeveloperKey;
import com.tbv.akramhussain.thebaiganvines.R;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoDisplay extends YouTubeFailureRecoveryActivity {

    private String TAG = VideoDisplay.class.getSimpleName();
    InterstitialAd mInterstitialAd;



private TextView Title,Desc;
    private ImageView img;
    private Activity context;
  public String url = "";
    String data1="";
    private String Names[];
    private String Comnt[];
    private TextView name,cmnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);
        //setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(false);
        Title = (TextView) findViewById(R.id.title_result);
        Desc = (TextView) findViewById(R.id.desc_result);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        Bundle b = getIntent().getExtras();
        String title = b.getString("title");
        String desc = b.getString("desc");
        url = b.getString("url");
        Title.setText(title);
        Desc.setText(desc);
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        adRequest = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
       // mInterstitialAd.loadAd(adRequest);
      /*  mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });*/
       /* String comments = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&videoId="+url+"&key=AIzaSyD-xsp18wkg_Ju3uSpJjejQYwhMHRRP9oY&maxResults=100";
        if (isNetworkAvailabe()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(comments).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            data1=getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        } else {
                            //gj
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught:", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception Caught:", e);
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
        }*/

    }

  /*  private String getCurrentDetails(String jsonData) throws JSONException {

        JSONObject videos = new JSONObject(jsonData);
        JSONArray items = videos.getJSONArray("items");

        Names =new String[items.length()];
        Comnt =new String[items.length()];

        for (int i = 0; i < items.length(); i++) {
            JSONObject c = items.getJSONObject(i);
            JSONObject topcmnt = c.getJSONObject("topLevelComment");
            JSONObject snippet2 = topcmnt.getJSONObject("snippet");
            Names[i]=snippet2.getString("authorDisplayName");
            Comnt[i]=snippet2.getString("description");
            name.setText(Names[i]);
            cmnt.setText(Comnt[i]);
            Toast.makeText(this, Names[i], Toast.LENGTH_LONG).show();
        }
        return "Ok";
    }
    private boolean isNetworkAvailabe() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }*/
 /* private void showInterstitial() {
      if (mInterstitialAd.isLoaded()) {
          mInterstitialAd.show();
      }
  }*/
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        if (!wasRestored) {
            player.cueVideo(url);
        }
    }
    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
