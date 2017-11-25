package com.tbv.akramhussain.thebaiganvines.Misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tbv.akramhussain.thebaiganvines.R;
import com.tbv.akramhussain.thebaiganvines.Web.VideoDisplay;

/**
 * Created by Akram Hussain on 2/4/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles ;
    private String[] desc;
    private String[] imagepath ;
    private String[] url;
    private Activity context;

    public RecyclerAdapter(Activity context, String[] titles, String[] desc, String[] imagepath, String[] url){
        this.context = context;
        this.titles = titles;
        this.desc = desc;
        this.url =url;
        this.imagepath = imagepath;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.imageView);
            itemTitle = (TextView)itemView.findViewById(R.id.tvMovie);
            itemDetail = (TextView)itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent=new Intent(context,VideoDisplay.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("title",titles[position]);
                    bundle.putString("imagepath",imagepath[position]);
                    bundle.putString("desc",desc[position]);
                    bundle.putString("url",url[position]);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(desc[i]);
        Picasso.with(context).load(imagepath[i]).into(viewHolder.itemImage);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
