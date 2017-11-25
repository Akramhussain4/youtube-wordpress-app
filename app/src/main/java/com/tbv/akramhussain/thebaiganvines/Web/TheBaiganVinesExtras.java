package com.tbv.akramhussain.thebaiganvines.Web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tbv.akramhussain.thebaiganvines.Misc.AlertDialogFragment;
import com.tbv.akramhussain.thebaiganvines.Misc.MainActivity;
import com.tbv.akramhussain.thebaiganvines.Misc.RecyclerAdapter;
import com.tbv.akramhussain.thebaiganvines.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TheBaiganVinesExtras extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private TextView title;
    private String Titles[];
    private String Desc[];
    private String Imagepath[];
    private String Url[];
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        JSON();
    }

    private void JSON(){
        if(!isNetworkAvailabe()) {


            title = (TextView) findViewById(R.id.tvMovie);
            String videosUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails%2Cstatus&maxResults=50&playlistId=UUlcyhW2x45yij-nR9DTEBVQ&key=AIzaSyD-xsp18wkg_Ju3uSpJjejQYwhMHRRP9oY";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(videosUrl).build();
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
    }

    private void updateDisplay() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter resc = new RecyclerAdapter(this, Titles,Desc,Imagepath,Url);
        recyclerView.setAdapter(resc);
    }

    private String getCurrentDetails(String jsonData) throws JSONException {

        JSONObject videos = new JSONObject(jsonData);
        JSONArray items = videos.getJSONArray("items");

        Titles =new String[items.length()];
        Desc =new String[items.length()];
        Imagepath =new String[items.length()];
        Url = new String[items.length()];


        for (int i = 0; i < items.length(); i++) {
            JSONObject c = items.getJSONObject(i);
            JSONObject snippet = c.getJSONObject("snippet");
            Titles[i]=snippet.getString("title");
            Desc[i]=snippet.getString("description");
            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject medium = thumbnails.getJSONObject("medium");
            JSONObject details = c.getJSONObject("contentDetails");
            Url[i] = details.getString("videoId");
            Imagepath[i]=medium.getString("url");
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

}
