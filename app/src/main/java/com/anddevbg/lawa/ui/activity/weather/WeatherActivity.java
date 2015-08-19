package com.anddevbg.lawa.ui.activity.weather;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.anddevbg.lawa.R;
import com.anddevbg.lawa.adapter.WeatherFragmentAdapter;
import com.anddevbg.lawa.animation.ZoomPagerTransformation;
import com.anddevbg.lawa.model.SearchActivity;
import com.anddevbg.lawa.model.WeatherData;
import com.anddevbg.lawa.ui.fragment.BaseWeatherFragment;
import com.anddevbg.lawa.ui.fragment.FavoriteCityFragment;
import com.anddevbg.lawa.weather.FavoriteCurrentWeatherWrapper;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    WeatherFragmentAdapter mWeatherAdapter;
    ViewPager viewPager;
    SearchView searchView;
    ArrayList<WeatherData> result;
    WeatherData city1Data;
    int search_request_code = 1;

    private List<WeatherData> getWeatherData() {
        result = new ArrayList<>();
        city1Data = new WeatherData();
        result.add(city1Data);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSearchRequested() {
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("name", searchView.getQuery());
        startActivity(i);
        return super.onSearchRequested();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        //Button add = (Button) menu.findItem(R.id.action_add).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("weather_array", result);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result = (ArrayList<WeatherData>) savedInstanceState.getSerializable("weather_array");
        mWeatherAdapter.setWeatherData(result);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent searchActivityIntent = new Intent(this, SearchCityActivity.class);
                startActivityForResult(searchActivityIntent, search_request_code);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == search_request_code) {
            if (resultCode == RESULT_OK) {
                Log.d("asd", "in weather activity result " + data.getStringExtra("c1name"));
                WeatherData mNewWeather = new WeatherData();
                result.add(mNewWeather);
                FavoriteCityFragment.createInstance(mNewWeather);
                mWeatherAdapter.setWeatherData(result);
                mWeatherAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(result.size(), true);
            }
        }
    }

    private void initControls() {
        mWeatherAdapter = new WeatherFragmentAdapter(getSupportFragmentManager());
        mWeatherAdapter.setWeatherData(getWeatherData());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mWeatherAdapter);
        viewPager.setPageTransformer(false, new ZoomPagerTransformation());
        viewPager.setOffscreenPageLimit(2);
    }

}

