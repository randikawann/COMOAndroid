package com.example.randikawann.cocoapp2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.randikawann.cocoapp2.MainActivity;
import com.example.randikawann.cocoapp2.fragments.ChatsFragment;
import com.example.randikawann.cocoapp2.fragments.FriendsFragment;
import com.example.randikawann.cocoapp2.fragments.RequestFragment;

import java.util.Map;

public class TabPageAdapter extends FragmentPagerAdapter{

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mfragmentManager;
    private Context mContext;

    public TabPageAdapter(FragmentManager fm) {

        super(fm);
        // for refreshing fragment
        mfragmentManager = fm;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                MainActivity mainActivity = new MainActivity();
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1:
                MainActivity mainActivity1 = new MainActivity();
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
            case 2:
                MainActivity mainActivity2 = new MainActivity();
                FriendsFragment friendsFragment = new FriendsFragment();
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
