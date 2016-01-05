package com.coolrandy.com.eventbusapp.greenrobot.fragment;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.coolrandy.com.eventbusapp.greenrobot.model.Event;
import com.coolrandy.com.eventbusapp.greenrobot.model.Item;
import de.greenrobot.event.EventBus;
/**
 * Created by admin on 2016/1/5.
 */

public class ItemListFragment extends ListFragment
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // 开启线程加载列表
        new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(2000); // 模拟延时
                    // 发布事件，在后台线程发的事件
                    EventBus.getDefault().post(new Event.ItemListEvent(Item.ITEMS));
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public void onEventMainThread(Event.ItemListEvent event)
    {
        setListAdapter(new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id)
    {
        super.onListItemClick(listView, view, position, id);
        EventBus.getDefault().post(getListView().getItemAtPosition(position));
    }

}