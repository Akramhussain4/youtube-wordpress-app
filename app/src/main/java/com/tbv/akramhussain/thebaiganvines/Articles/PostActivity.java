package com.tbv.akramhussain.thebaiganvines.Articles;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tbv.akramhussain.thebaiganvines.Model.Post;

public class PostActivity extends AppCompatActivity implements
        RecyclerViewFragment.PostListListener, PostFragment.PostListener,
        TabLayoutFragment.TabLayoutListener, SearchResultFragment.SearchResultListener
         {

    private static final String TAG = PostActivity.class.getSimpleName();
    public static final String TAB_LAYOUT_FRAGMENT_TAG = "TabLayoutFragment";
    public static final String POST_FRAGMENT_TAG = "PostFragment";

    private FragmentManager fm = null;
    private TabLayoutFragment tlf;
    private PostFragment pf;
    private SearchResultFragment srf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_post);
        fm = getSupportFragmentManager();
        tlf = new TabLayoutFragment();
        pf = new PostFragment();
        srf = new SearchResultFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, pf, POST_FRAGMENT_TAG);
        ft.add(android.R.id.content, tlf, TAB_LAYOUT_FRAGMENT_TAG);
        ft.hide(pf);
        ft.commit();
    }


    @Override
    public void onPostSelected(Post post, boolean isSearch) {
        pf = (PostFragment) getSupportFragmentManager().findFragmentByTag(POST_FRAGMENT_TAG);
        Bundle args = new Bundle();
        args.putInt("id", post.getId());
        args.putString("title", post.getTitle());
        args.putString("date", post.getDate());
        args.putString("author", post.getAuthor());
        args.putString("content", post.getContent());
        args.putString("url", post.getUrl());
        args.putString("featuredImage", post.getFeaturedImageUrl());
        pf.setUIArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (!isSearch) {
            ft.hide(tlf);
        } else {
            ft.hide(srf);
        }
        ft.show(pf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onSearchSubmitted(String query) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        srf = SearchResultFragment.newInstance(query);
        ft.add(android.R.id.content, srf);
        ft.hide(tlf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onCommentSelected(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.hide(pf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        resetActionBarIfApplicable();
        super.onBackPressed();
    }

    @Override
    public void onHomePressed() {
        resetActionBarIfApplicable();
        fm.popBackStack();
    }

    private void resetActionBarIfApplicable() {
        Log.d(TAG, "SearchResultFragment is visible: " + srf.isHidden());
        if (srf.isVisible()) {
            tlf.resetActionBar();
        }
    }
}
