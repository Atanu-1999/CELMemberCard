package com.AltAir.celmembercard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.listener.NotificationListener;
import com.AltAir.celmembercard.response.NotificationResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    public static List<NotificationResponse.Datum> ItemList;
    private Context context;
    NotificationListener listerner;

    public NotificationAdapter(Context context, List<NotificationResponse.Datum> ItemList, NotificationListener listerner) {
        this.ItemList = ItemList;
        this.context = context;
        this.listerner = listerner;
    }
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(ItemList.get(position).getTitle());
        holder.tv_des.setText(ItemList.get(position).getDescription());
        String read = ItemList.get(position).getReadStatus();
        if (Objects.equals(read, "Y"))
        {
            holder.ll_heading3.setBackgroundResource(R.color.white);
            holder.ll_color.setBackgroundResource(R.color.white);
        }
        else {
            holder.ll_heading3.setBackgroundResource(R.color.orange2);
            holder.ll_color.setBackgroundResource(R.color.orange2);
        }
//        created":"2024-02-12 11:23:55
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date9 = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
//            date = dateFormat.parse("2017-04-26T20:55:00.000Z");
            date9 = dateFormat1.parse(ItemList.get(position).getCreated());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        DateFormat formatter9 = new SimpleDateFormat("dd-MM-yyyy | hh:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter9.format(date9);

        Log.d("datecheck",dateStr);

        holder.tv_time.setText(dateStr);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_des,tv_time;
        LinearLayout item,ll_heading3,ll_color;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            item = (LinearLayout) itemView.findViewById(R.id.item);
            ll_heading3 = (LinearLayout) itemView.findViewById(R.id.ll_heading3);
            ll_color = (LinearLayout) itemView.findViewById(R.id.ll_color);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listerner.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
