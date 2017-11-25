package com.tbv.akramhussain.thebaiganvines.Articles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tbv.akramhussain.thebaiganvines.Model.Category;
import com.tbv.akramhussain.thebaiganvines.Model.Config;
import com.tbv.akramhussain.thebaiganvines.Model.JSONParser;
import com.tbv.akramhussain.thebaiganvines.Model.RecyclerViewFragmentPagerAdaptor;
import com.tbv.akramhussain.thebaiganvines.R;
import org.json.JSONObject;
import java.util.ArrayList;

public class TabLayoutFragment extends Fragment implements SearchView.OnQueryTextListener
{
    private static final String TAG = "TabLayoutFragment";
    private ProgressDialog mProgressDialog;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    protected static ArrayList<Category> categories = null;
    private TabLayoutListener mListener;
    private Snackbar snackbar;

    public TabLayoutFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((PostActivity)getActivity()).setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(1);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        loadCategories();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.d(TAG, "onCreateOptionsMenu()");
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_search)
        {
            searchView.requestFocus();
        }
        return true;
    }

    protected void resetActionBar()
    {
        ((PostActivity)getActivity()).setSupportActionBar(toolbar);
        searchMenuItem.collapseActionView();
    }

    private void loadCategories()
    {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.loading_categories));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Config.CATEGORY_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mProgressDialog.dismiss();
                        categories = JSONParser.parseCategories(jsonObject);
                        RecyclerViewFragmentPagerAdaptor adaptor = new RecyclerViewFragmentPagerAdaptor(getChildFragmentManager(), categories);
                        mViewPager.setAdapter(adaptor);
                        mTabLayout.setupWithViewPager(mViewPager);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "----- Volley Error -----");
                        mProgressDialog.dismiss();
                           snackbar.make(mTabLayout, R.string.error_load_categories,
                                snackbar.LENGTH_INDEFINITE).setAction(R.string.action_retry,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loadCategories();
                                    }
                                }).show();
                        snackbar.setActionTextColor(Color.WHITE);


                    }
                });
       AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        searchView.clearFocus();
        mListener.onSearchSubmitted(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TabLayoutListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement PostListListener");
        }
    }

    public interface TabLayoutListener
    {
        void onSearchSubmitted(String query);
    }

}