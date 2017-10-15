package com.av.jerb.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.jerb.Data.Constants_URL;
import com.av.jerb.Data.Tips;
import com.av.jerb.MainActivity;
import com.av.jerb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Maiada on 10/16/2017.
 */

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    private ArrayList<Tips> getTipsList;
    public TipsAdapter(ArrayList<Tips> getTL){
        getTipsList=getTL;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_tips,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.number.setText(getTipsList.get(position).getId());
        holder.title.setText(getTipsList.get(position).getTitle());
        Picasso.with(MainActivity.context).load(Constants_URL.IMG_URL+getTipsList.get(position).getImage()).into(holder.image);

    }


    @Override
    public int getItemCount() {
        return getTipsList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView number,title;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            number = (TextView) (itemView).findViewById(R.id.number_tips);
            title = (TextView) (itemView).findViewById(R.id.title_tips);
            image=(ImageView) (itemView).findViewById(R.id.img_tips);

        }
    }
}
