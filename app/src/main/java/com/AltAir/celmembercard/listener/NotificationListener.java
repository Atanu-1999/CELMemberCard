package com.AltAir.celmembercard.listener;

import com.AltAir.celmembercard.response.NotificationResponse;

public interface NotificationListener {
    void onItemClickedItem(NotificationResponse.Datum item, int position);
}
