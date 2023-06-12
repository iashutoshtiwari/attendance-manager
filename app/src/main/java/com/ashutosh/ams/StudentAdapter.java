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

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    Activity activity;
    StudentAdapter.OnItemClickListener onItemClickListener;
    private ArrayList<Student_Get_Set> c_list;

    public StudentAdapter(Activity activity, ArrayList<Student_Get_Set> c_list) {
        this.activity = activity;
        this.c_list = c_list;
    }

    @Override
    public int getItemViewType(int position){ return VIEW_TYPE_ITEM;}

    public void SetOnItemClickListener(final StudentAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(activity).inflate(R.layout.activity_student_view,parent,false);

            return new StudentAdapter.ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentAdapter.ViewHolder)
        {
            final StudentAdapter.ViewHolder viewHolder = (StudentAdapter.ViewHolder) holder;
            viewHolder.t1.setText(c_list.get(position).getFname());
            viewHolder.t2.setText(c_list.get(position).getLname());
            viewHolder.t3.setText(c_list.get(position).getAddress());
            viewHolder.t4.setText(c_list.get(position).getMobile());
            viewHolder.t5.setText(c_list.get(position).getEid());
            viewHolder.t6.setText(c_list.get(position).getPass());
            viewHolder.t7.setText(c_list.get(position).getDept());
            viewHolder.t8.setText(c_list.get(position).getSt_class());
        }

    }

    public void setfilter(ArrayList<Student_Get_Set> newList){
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
        TextView t1,t2,t3,t4,t5,t6,t7,t8;
        Button button_delete;
        public ViewHolder(View view) {
            super(view);
            t1=view.findViewById(R.id.st_fname);
            t2=view.findViewById(R.id.st_lname);
            t3=view.findViewById(R.id.st_address);
            t4=view.findViewById(R.id.st_phone);
            t5=view.findViewById(R.id.st_uid);
            t6=view.findViewById(R.id.st_pass);
            t7=view.findViewById(R.id.st_dept);
            t8=view.findViewById(R.id.st_class);
            itemView.setOnClickListener(this);
            button_delete = view.findViewById(R.id.st_delete_data);
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
