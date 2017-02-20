package spr.dev.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import spr.dev.R;
import spr.dev.constant.SP_Constant;
import spr.dev.util.Tools;
import spr.dev.view.ViewHolder;

/**
 * Created by hanki on 2017/2/20.
 */

public class NineSquareAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public NineSquareAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_published_grida, null);
        }

        ImageView iv_image = ViewHolder.get(convertView, R.id.item_grida_image);

        if (list.get(position) != null) {
            Picasso.with(context)
                    .load(new File(list.get(position)))
                    .resize(Tools.dp2px(context, SP_Constant.DETAIL_WIDTH),
                            Tools.dp2px(context, SP_Constant.DETAIL_HIGH))
                    .centerCrop()
                    .into(iv_image);

        } else {
            Picasso.with(context)
                    .load(R.mipmap.pic_bg)
                    .resize(Tools.dp2px(context, SP_Constant.DETAIL_WIDTH),
                            Tools.dp2px(context, SP_Constant.DETAIL_HIGH))
                    .centerCrop()
                    .into(iv_image);
        }
        return convertView;
    }
}
