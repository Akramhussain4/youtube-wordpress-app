package com.tbv.akramhussain.thebaiganvines.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tbv.akramhussain.thebaiganvines.Articles.RecyclerViewFragment;

import java.util.ArrayList;

/**
 * Created by Hussain on 02-Jul-17.
 */

public class RecyclerViewFragmentPagerAdaptor extends FragmentPagerAdapter
{
    private ArrayList<Category> categories;
    public RecyclerViewFragmentPagerAdaptor(FragmentManager fm, ArrayList<Category> categories)
    {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position)
    {
        return RecyclerViewFragment.newInstance(categories.get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
