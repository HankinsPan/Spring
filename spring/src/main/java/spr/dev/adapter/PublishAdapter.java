package spr.dev.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import spr.dev.R;
import spr.dev.activity.publish.PublishDetail;
import spr.dev.view.RoundedTransformation;

/**
 * Created by hanki on 2017/2/22.
 */

public class PublishAdapter extends
        RecyclerView.Adapter<PublishAdapter.PublishViewHolder> {

    private Context mContext;
    private List<AVObject> mList;

    public PublishAdapter(List<AVObject> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public PublishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pub_main, parent, false);

        PublishViewHolder holder = new PublishViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PublishViewHolder holder, final int position) {
        holder.pub_Title.setText((CharSequence) mList
                .get(position).get("pub_title"));

        holder.pub_Content.setText((CharSequence) mList
                .get(position).get("pub_content"));

        holder.pub_Name.setText(mList.get(position).getAVUser("owner") == null
                ? "User"
                : mList.get(position).getAVUser("owner").getUsername());

        Picasso.with(mContext)
                .load(mList.get(position).getAVFile("image") == null
                        ? "www"
                        : mList.get(position).getAVFile("image").getUrl())
                .transform(new RoundedTransformation(9, 0))
                .into(holder.pub_Image);

        holder.pub_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PublishDetail.class);
                intent.putExtra("springObjectId",mList.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class PublishViewHolder extends RecyclerView.ViewHolder {

        private TextView pub_Name;
        private TextView pub_Title;
        private TextView pub_Content;
        private ImageView pub_Image;
        private CardView pub_Item;


        public PublishViewHolder(View itemView) {
            super(itemView);

            pub_Name = (TextView) itemView.findViewById(R.id.tv_item_userName);
            pub_Title = (TextView) itemView.findViewById(R.id.tv_item_title);
            pub_Content = (TextView) itemView.findViewById(R.id.tv_item_content);
            pub_Image = (ImageView) itemView.findViewById(R.id.iv_item_image);
            pub_Item = (CardView) itemView.findViewById(R.id.card_pub_main);

        }
    }
}
