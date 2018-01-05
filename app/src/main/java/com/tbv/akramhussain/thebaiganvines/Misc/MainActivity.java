package com.tbv.akramhussain.thebaiganvines.Misc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.tbv.akramhussain.thebaiganvines.Articles.PostActivity;
import com.tbv.akramhussain.thebaiganvines.R;
import com.tbv.akramhussain.thebaiganvines.Web.Facebook;
import com.tbv.akramhussain.thebaiganvines.Web.Instagram;
import com.tbv.akramhussain.thebaiganvines.Web.TheBaiganVinesExtras;
import com.tbv.akramhussain.thebaiganvines.Web.Twitter;
import com.tbv.akramhussain.thebaiganvines.Web.Website;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AccountHeader headerResult =null;
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView title;
    private String Titles[];
    private String Desc[];
    private String Imagepath[];
    private String Url1[];
    String data1="";
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private TextView error;
    private ImageView sigpic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        error = (TextView) findViewById(R.id.error);
        error.setVisibility(View.GONE);
        sigpic = (ImageView)findViewById(R.id.sigpic);
        sigpic.setVisibility(View.GONE);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState)
                .build();
        new DrawerBuilder().withActivity(MainActivity.this).build();
        new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("The Baigan Vines").withIdentifier(1).withSelectable(false).withIcon(R.drawable.icon1),
                        new PrimaryDrawerItem().withName("The Baigan Vines Extras").withIdentifier(2002).withSelectable(false).withIcon(R.drawable.greenicon),
                        new PrimaryDrawerItem().withName("Articles").withIdentifier(10).withSelectable(false).withIcon(R.drawable.world),
                        new PrimaryDrawerItem().withName("TBV Store").withIdentifier(99).withSelectable(false).withIcon(R.drawable.logo),
                        new PrimaryDrawerItem().withName("Facebook").withIdentifier(2).withSelectable(false).withIcon(R.drawable.facebookicon),
                        new PrimaryDrawerItem().withName("Instagram").withIdentifier(3).withSelectable(false).withIcon(R.drawable.instaicon),
                        new PrimaryDrawerItem().withName("Twitter").withIdentifier(4).withSelectable(false).withIcon(R.drawable.twittericon),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.infoicon).withIdentifier(5).withSelectable(false)).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null) {
                    Intent intent = null;
                    if (drawerItem.getIdentifier() == 2) {
                        intent = new Intent(MainActivity.this, Facebook.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 3) {
                        intent = new Intent(MainActivity.this, Instagram.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 4) {
                        intent = new Intent(MainActivity.this, Twitter.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 2002) {
                        intent = new Intent(MainActivity.this, TheBaiganVinesExtras.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 99) {
                        intent = new Intent(MainActivity.this, Website.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 5) {
                        intent = new Intent(MainActivity.this, About.class);
                        startActivity(intent);
                    } else if (drawerItem.getIdentifier() == 10) {
                        intent = new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                    }
                }
                return false;
            }
        }).build();



        JSON();

    }

    private void JSON()
    {
        if(!isNetworkAvailabe())
        {
            error.setVisibility(View.VISIBLE);
            sigpic.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(drawerLayout, "Network Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    JSON();
                }
            });
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
        }

                title = (TextView) findViewById(R.id.tvMovie);
                String videosUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails%2Cstatus&maxResults=50&playlistId=UUsHhB0DI59ru6T4fp5lb4Jw&key=AIzaSyD-xsp18wkg_Ju3uSpJjejQYwhMHRRP9oY";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(videosUrl).build();
                Call call = client.newCall(request);

                call.enqueue(new Callback()
                {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                    if (response.isSuccessful()) {
                        data1 = getCurrentDetails(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });
                    } else {
                        alertUserAboutError();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception Caught:", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception Caught:", e);
                }
            }
        });
    }



    private void updateDisplay() {
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                RecyclerAdapter resc = new RecyclerAdapter(MainActivity.this, Titles, Desc, Imagepath, Url1);
                recyclerView.setAdapter(resc);
    }

    private String getCurrentDetails(final String jsonData) throws JSONException {

         JSONObject videos = null;
                try {
                    videos = new JSONObject(jsonData);
                    JSONArray items = videos.getJSONArray("items");

                    Titles = new String[items.length()];
                    Desc = new String[items.length()];
                    Imagepath = new String[items.length()];
                    Url1 = new String[items.length()];
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject snippet = c.getJSONObject("snippet");
                        Titles[i] = snippet.getString("title");
                        Desc[i] = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        JSONObject res = snippet.getJSONObject("resourceId");
                        Url1[i] = res.getString("videoId");
                        Imagepath[i] = medium.getString("url");
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
        return "Ok";

    }

    public boolean isNetworkAvailabe() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}