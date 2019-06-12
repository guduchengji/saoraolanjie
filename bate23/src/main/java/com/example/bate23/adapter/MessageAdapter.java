package com.example.bate23.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bate23.R;
import com.example.bate23.pojo.Message;


import java.util.List;


public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<Message> list;

    public MessageAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sms_item, null);
            viewHolder = new ViewHolder();
            viewHolder.phone = (TextView) view.findViewById(R.id.smsnumber);
            viewHolder.content = (TextView) view.findViewById(R.id.smscontent);
            viewHolder.time = (TextView) view.findViewById(R.id.smstime);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.phone.setText(list.get(i).getPhonenumber());
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.content.setText(list.get(i).getContent());
        return view;
    }
    private class ViewHolder {
        private TextView phone;
        private TextView content;
        private TextView time;
    }
}
