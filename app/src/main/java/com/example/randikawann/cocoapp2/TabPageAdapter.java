package com.example.randikawann.cocoapp2;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TabPageAdapter extends FragmentPagerAdapter{
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1:
                RequestFragment chatsFragment = new RequestFragment();
                return chatsFragment;
            case 2:
                RequestFragment friendsFragment = new RequestFragment();
                return friendsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle (int position){
        switch (position){
            case 0:
                return "Request";
            case 1:
                return "Chats";
            case 2:
                return "Friends";
            default:
                return null;
        }
    }
}
