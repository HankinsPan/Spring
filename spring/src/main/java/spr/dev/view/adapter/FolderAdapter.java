package spr.dev.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import spr.dev.R;
import spr.dev.view.bean.Folder;

/**
 * Created by hanki on 2017/2/20.
 */

public class FolderAdapter extends BaseAdapter {

    int mImageSize;
    int lastSelected = 0;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Folder> mFolders = new ArrayList<Folder>();

    public FolderAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
    }

    /**
     * 设置数据集
     *
     * @param folders
     */
    public void setData(List<Folder> folders) {
        if (folders != null && folders.size() > 0) {
            mFolders = folders;
        } else {
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolders.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0)
            return null;

        return mFolders.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_folder, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            if (position == 0) {
                holder.name.setText("所有照片");
                holder.size.setText(getTotalImageSize() + " 张");
                if (mFolders.size() > 0) {
                    Folder f = mFolders.get(0);
                    Picasso.with(mContext)
                            .load(new File(f.cover.path))
                            .error(R.mipmap.default_error)
                            .resize(mImageSize, mImageSize)
                            .centerCrop()
                            .into(holder.cover);
                }
            } else {
                holder.bindData(getItem(position));
            }

            if (lastSelected == position) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private int getTotalImageSize() {
        int result = 0;
        if (mFolders != null && mFolders.size() > 0) {
            for (Folder f : mFolders) {
                result += f.images.size();
            }
        }
        return result;
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i) return;

        lastSelected = i;
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;

        ViewHolder(View view) {
            cover = (ImageView) view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }

        void bindData(Folder data) {
            name.setText(data.name);
            size.setText(data.images.size() + "张");
            // 显示图片
            Picasso.with(mContext)
                    .load(new File(data.cover.path))
                    .placeholder(R.mipmap.default_error)
                    .resize(mImageSize, mImageSize)
                    .centerCrop()
                    .into(cover);
            // TODO 选择标识
        }

//        public void bindData(Object item) {
//
//        }
    }

}
