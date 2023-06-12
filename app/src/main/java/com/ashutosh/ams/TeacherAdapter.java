package com.ashutosh.ams;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    Activity activity;
    OnItemClickListener onItemClickListener;
    private ArrayList<Teacher_Get_Set>c_list;

    public TeacherAdapter(Activity activity, ArrayList<Teacher_Get_Set> c_list) {
        this.activity = activity;
        this.c_list = c_list;
    }

    @Override
    public int getItemViewType(int position){ return VIEW_TYPE_ITEM;}

    public void SetOnItemClickListener(final TeacherAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(activity).inflate(R.layout.activity_teacher_view,parent,false);

           return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TeacherAdapter.ViewHolder)
        {
            final TeacherAdapter.ViewHolder viewHolder = (TeacherAdapter.ViewHolder) holder;
            viewHolder.t1.setText(c_list.get(position).getFname());
            viewHolder.t2.setText(c_list.get(position).getLname());
            viewHolder.t3.setText(c_list.get(position).getAddress());
            viewHolder.t4.setText(c_list.get(position).getMobile());
            viewHolder.t5.setText(c_list.get(position).getEid());
            viewHolder.t6.setText(c_list.get(position).getPass());
        }

    }

    public void setfilter(ArrayList<Teacher_Get_Set> newList){
        c_list=new ArrayList<>();
        c_list.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
         void onItemClick(View view,int position);
    }

    @Override
    public int getItemCount() {
        return c_list == null?0 : c_list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t1,t2,t3,t4,t5,t6;
        Button button_delete;
        public ViewHolder(View view) {
            super(view);
            t1=view.findViewById(R.id.user_fname);
            t2=view.findViewById(R.id.user_lname);
            t3=view.findViewById(R.id.user_address);
            t4=view.findViewById(R.id.user_phone);
            t5=view.findViewById(R.id.user_uid);
            t6=view.findViewById(R.id.user_pass);

            itemView.setOnClickListener(this);
            button_delete = view.findViewById(R.id.delete_data);
            button_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener !=null)
            {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
