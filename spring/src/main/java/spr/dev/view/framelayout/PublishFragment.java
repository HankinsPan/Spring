package spr.dev.view.framelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import spr.dev.R;
import spr.dev.adapter.PublishAdapter;

/**
 * Created by hanki on 2017/2/22.
 */

public class PublishFragment extends Fragment {
    private static final String TAG = "PublishFragment";

    private View pubView;
    private RecyclerView mRecycView;
    private PublishAdapter adapter;
    private List<AVObject> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        pubView = inflater.inflate(R.layout.fragment_pub, container, false);


        mRecycView = (RecyclerView) pubView.findViewById(R.id.recyc_Pub);
        mRecycView.setHasFixedSize(true);
        mRecycView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PublishAdapter(mList,getContext());
        mRecycView.setAdapter(adapter);
        return pubView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mList.clear();

        AVQuery<AVObject> avQuery = new AVQuery<>("Spring_Pub");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.e(TAG, "== Get Spring List Success ==" + e);
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "== Get Spring List Failed ==" + e);
                    e.printStackTrace();
                }
            }
        });

    }
}
