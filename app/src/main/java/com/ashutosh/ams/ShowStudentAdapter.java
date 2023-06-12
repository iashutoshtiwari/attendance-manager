package com.ashutosh.ams;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    Activity activity;
    ShowStudentAdapter.OnItemClickListener onItemClickListener;
    private ArrayList<Student_Get_Set> c_list;

    public ShowStudentAdapter(Activity activity, ArrayList<Student_Get_Set> c_list) {
        this.activity = activity;
        this.c_list = c_list;
    }

    @Override
    public int getItemViewType(int position){ return VIEW_TYPE_ITEM;}

    public void SetOnItemClickListener(final ShowStudentAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(activity).inflate(R.layout.activity_show_attendance,parent,false);

            return new ShowStudentAdapter.ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShowStudentAdapter.ViewHolder)
        {
            final ShowStudentAdapter.ViewHolder viewHolder = (ShowStudentAdapter.ViewHolder) holder;
            viewHolder.t1.setText(c_list.get(position).getEid());
            viewHolder.t2.setText(c_list.get(position).getDept());
            viewHolder.t3.setText(c_list.get(position).getSt_class());
            viewHolder.t4.setText(c_list.get(position).getDate());
            viewHolder.t5.setText(c_list.get(position).getSubject());
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
        TextView t1,t2,t3,t4,t5;
        public ViewHolder(View view) {
            super(view);
            t1=view.findViewById(R.id.show_uid);
            t2=view.findViewById(R.id.show_dept);
            t3=view.findViewById(R.id.show_class);
            t4=view.findViewById(R.id.show_date);
            t5=view.findViewById(R.id.show_subject);
            itemView.setOnClickListener(this);
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
