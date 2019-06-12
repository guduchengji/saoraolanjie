package com.example.bate23.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.bate23.R;
import com.example.bate23.adapter.MessageAdapter;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.Message;


import java.util.List;


public class MessageFragment extends Fragment {
    private ListView listView;
    private MessageAdapter adapter;
    private List<Message> messageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //从数据库中得到拦截短信的集合
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        messageList = DbManager.getInstance().getMessageList();
        listView = (ListView) view.findViewById(R.id.smslistview);
        adapter = new MessageAdapter(getActivity(), messageList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return view;
    }
}