package com.tbv.akramhussain.thebaiganvines.Articles;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tbv.akramhussain.thebaiganvines.R;

public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";

    private int id;
    private String title;
    private String content;
    private String url;
    private String featuredImageUrl;
    private WebView webView;
    private ImageView featuredImageView;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;
    private PostListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        //((MainActivity)getActivity()).setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbarLayout);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        featuredImageView = (ImageView) rootView.findViewById(R.id.featuredImage);
        webView = (WebView) rootView.findViewById(R.id.webview_post);
        return rootView;
    }

    public void setUIArguments(final Bundle args) {

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        webView.loadData("", "text/html; charset=UTF-8", null);
                        featuredImageView.setImageBitmap(null);
                        id = args.getInt("id");
                        title = args.getString("title");
                        String date = args.getString("date");
                        String author = args.getString("author");
                        content = args.getString("content");
                        url = args.getString("url");
                        featuredImageUrl = args.getString("featuredImage");
                        Glide.with(PostFragment.this)
                                .load(featuredImageUrl)
                                .centerCrop()
                                .into(featuredImageView);
                        String html = "<style>img{max-width:100%;height:auto;} " +
                                "iframe{width:100%;}</style> ";
                        html += "<h2>" + title + "</h2> ";
                        html += "<h4>" + date + " " + author + "</h4>";
                        html += content;
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setWebChromeClient(new WebChromeClient());
                        webView.loadData(html, "text/html; charset=UTF-8", null);
                        Log.d(TAG, "Showing post, ID: " + id);
                        Log.d(TAG, "Featured Image: " + featuredImageUrl);
                        ((PostActivity) getActivity()).setSupportActionBar(toolbar);
                        ((PostActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        expandToolbar();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nestedScrollView.smoothScrollTo(0, 0);
                            }
                        }, 500);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.d(TAG, "onCreateOptionsMenu()");
        inflater.inflate(R.menu.menu_post, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
        shareActionProvider.setShareIntent(i);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case android.R.id.home:
                mListener.onHomePressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void expandToolbar()
    {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if(behavior!=null) {
            behavior.setTopAndBottomOffset(0);
            behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, 1, new int[2]);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PostListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PostListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PostListener {
        void onCommentSelected(int id);
        void onHomePressed();
    }

}

