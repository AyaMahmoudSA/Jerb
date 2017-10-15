package com.av.jerb.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.av.jerb.Data.ToDo;
import com.av.jerb.R;

import java.util.ArrayList;

/**
 * Created by Maiada on 10/16/2017.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private ArrayList<ToDo> getToDoList;
    public ToDoAdapter(ArrayList<ToDo> getTDL){
        getToDoList=getTDL;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_todos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(getToDoList.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return getToDoList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) (itemView).findViewById(R.id.title_todo);

        }
    }
}
