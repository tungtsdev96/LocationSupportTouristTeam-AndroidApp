package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    private float pageWidth = 0;

    public void setPageWidth(float pageWidth) {
        this.pageWidth = pageWidth;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public float getPageWidth(int position) {
        if (pageWidth == 0) return super.getPageWidth(position);
        else return pageWidth;
    }

    public void clear() {
        for (int i = 0; i < fragments.size(); i++) {
            destroyItem(null, i, fragments.get(i));
        }
        fragments.clear();
        titles.clear();
        notifyDataSetChanged();
    }
}
