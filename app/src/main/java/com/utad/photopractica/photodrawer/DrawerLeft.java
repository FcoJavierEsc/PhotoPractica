package com.utad.photopractica.photodrawer;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.utad.photopractica.Comun;
import com.utad.photopractica.ImageViewActivity;
import com.utad.photopractica.LocalMapsActivity;
import com.utad.photopractica.R;

public class DrawerLeft implements DrawerAdapter.OnItemClickListener {

    //private Activity mActivity;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    CharSequence mTitulo;
    private Activity mActivity;

    public DrawerLeft(Activity activity,
                      DrawerLayout drawerLayout,
                      RecyclerView recyclerView,
                      CharSequence titulo,
                      ActionBar actionBar) {

        mActivity = activity;
        mDrawerLayout = drawerLayout;
        mRecyclerView = recyclerView;
        mTitulo = titulo;
        //mActivity = activity;
        mActionBar = actionBar;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);//(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(new DrawerAdapter(Comun.getOptions(), this));


        mDrawerToggle = new ActionBarDrawerToggle(
                mActivity,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                null,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                Log.v("PASA", "PASA1");
                //getActionBar().setTitle(getTitle());
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                Log.v("PASA", "PASA2 " + mDrawerLayout.getChildCount());
                //getActionBar().setTitle(getTitle());
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        try {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(mRecyclerView);
    }

    /*public CharSequence getTitle(){
        return mTitulo;
    }
    */
    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private void selectItem(int position) {

        Log.v("POLO", "Estamos bien " + position);

        switch (position) {
            case 0:
                Intent intent = new Intent(mActivity, LocalMapsActivity.class);
                mActivity.startActivity(intent);
                break;
            case 1:
                Intent startIntent = new Intent(mActivity, ImageViewActivity.class);
                mActivity.startActivity(startIntent);//, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case 2:
                Log.v("comp", "FACEbook");
                break;
            case 3:
                Log.v("comp", "twiter");
                break;
        }


        // update selected item title, then close the drawer
        // setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mRecyclerView);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void syncState() {
        mDrawerToggle.syncState();
    }

    public void setTitleAB(CharSequence title) {
        Log.v("PONEMOS", "el tituo" + title);
        mActionBar.setTitle(title);
    }


}
