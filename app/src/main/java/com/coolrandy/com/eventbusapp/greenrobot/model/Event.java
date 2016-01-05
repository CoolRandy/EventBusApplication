package com.coolrandy.com.eventbusapp.greenrobot.model;

import java.util.List;

/**
 * Created by admin on 2016/1/4.
 */
public class Event {

    public static class ItemListEvent{

        private List<Item> items;

        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }
}
