package com.av.jerb.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.jerb.Data.Constants_URL;
import com.av.jerb.Data.Plans;
import com.av.jerb.Data.Tips;
import com.av.jerb.MainActivity;
import com.av.jerb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maiada on 10/17/2017.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {

    private List<Plans> getPlanList;

    public PlanAdapter(List<Plans> getPL){
        getPlanList=getPL;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_plan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.planName.setText("Plan "+getPlanList.get(position).getPlanName());
        holder.location.setText(getPlanList.get(position).getLocation());
        holder.numberOfMember.setText(getPlanList.get(position).getMemberNumber()+"+ Family & Friends");
        holder.budget.setText("Planned Cost"+getPlanList.get(position).getBudget()+"L.E");


    }


    @Override
    public int getItemCount() {
        return getPlanList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView planName,location,numberOfMember,budget;
        public MyViewHolder(View itemView) {
            super(itemView);
            planName        = (TextView) (itemView).findViewById(R.id.title_plan);
            location        = (TextView) (itemView).findViewById(R.id.plan_location);
            numberOfMember  =  (TextView) (itemView).findViewById(R.id.family_friends_count);
            budget          =  (TextView) (itemView).findViewById(R.id.budgt_plan);

        }
    }
}
