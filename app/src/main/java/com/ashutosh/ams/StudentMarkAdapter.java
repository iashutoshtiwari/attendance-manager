package com.ashutosh.ams;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentMarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0;
    Activity activity;
    private ArrayList<Student_Get_Set> student_list;
    OnItemClickListener onItemClickListener;

    public StudentMarkAdapter(Activity activity, ArrayList<Student_Get_Set> student_list) {
        this.activity = activity;
        this.student_list = student_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==VIEW_TYPE_ITEM){
            View view= LayoutInflater.from(activity).inflate(R.layout.activity_mark_student,parent,false);
            return new ViewHolder(view);
        }
        return null;
    }

    public void SetOnItemClickListener(final StudentMarkAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentMarkAdapter.ViewHolder){
            final StudentMarkAdapter.ViewHolder student_ViewHolder=(StudentMarkAdapter.ViewHolder)holder;
            student_ViewHolder.fname.setText(student_list.get(position).getFname());
            student_ViewHolder.lname.setText(student_list.get(position).getLname());
            student_ViewHolder.dept.setText(student_list.get(position).getDept());
            student_ViewHolder.cl.setText(student_list.get(position).getSt_class());
            student_ViewHolder.uid.setText(student_list.get(position).getEid());
            student_ViewHolder.checkBox.setChecked(student_list.get(position).isCheck());

        }

    }

    @Override
    public int getItemCount() {
        return student_list==null?0:student_list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fname, lname, dept, cl, uid;
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            fname = view.findViewById(R.id.stu_fname);
            lname = view.findViewById(R.id.stu_lname);
            dept = view.findViewById(R.id.stu_dept);
            cl = view.findViewById(R.id.stu_class);
            uid = view.findViewById(R.id.stu_uid);
            checkBox = view.findViewById(R.id.stu_mark_present);
            checkBox.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener !=null){
                onItemClickListener.onItemClick(v,getAdapterPosition());

            }

        }
    }

}
