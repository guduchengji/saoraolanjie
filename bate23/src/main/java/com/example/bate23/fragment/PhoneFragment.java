package com.example.bate23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.bate23.R;
import com.example.bate23.adapter.PhoneCallAdapter;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.PhoneCall;

import java.util.List;

public class PhoneFragment extends Fragment {
    private ListView listView;
    private PhoneCallAdapter adapter;
    private List<PhoneCall> phoneCallList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //从数据库中得到拦截电话的集合
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        phoneCallList = DbManager.getInstance().getPhoneCallList();
        listView = (ListView) view.findViewById(R.id.tonghuaListView);
        adapter = new PhoneCallAdapter(getActivity(), phoneCallList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
