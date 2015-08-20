package com.anddevbg.lawa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.anddevbg.lawa.model.WeatherData;
import com.anddevbg.lawa.ui.fragment.BaseWeatherFragment;
import com.anddevbg.lawa.ui.fragment.CurrentLocationWeatherFragment;
import com.anddevbg.lawa.ui.fragment.FavoriteCityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpenkov on 15.6.2015 г..
 */
public class WeatherFragmentAdapter extends FragmentStatePagerAdapter {

    private List<WeatherData> mWeatherData;

    public WeatherFragmentAdapter(FragmentManager fm) {
        super(fm);
        mWeatherData = new ArrayList<>();
    }

    public void setWeatherData(List<WeatherData> data) {
        mWeatherData.clear();
        mWeatherData.addAll(data);

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return BaseWeatherFragment.createInstance(mWeatherData.get(position));
    }

    @Override
    public int getCount() {
        return mWeatherData.size();
    }
}
