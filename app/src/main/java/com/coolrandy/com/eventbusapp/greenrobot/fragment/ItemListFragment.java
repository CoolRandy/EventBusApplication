package com.coolrandy.com.eventbusapp.greenrobot.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 2015/12/31.
 * 目录Fragment
 */
public class ItemListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册监听器
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销监听器
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
