package spr.dev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spr.dev.R;

/**
 * Created by hanki on 2017/1/29.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {

    private final LayoutInflater mInflater;

    public CommonAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(mInflater.inflate(R.layout.re_item,parent,false));
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder{

        public CommonViewHolder(View itemView) {
            super(itemView);
        }
    }
}
