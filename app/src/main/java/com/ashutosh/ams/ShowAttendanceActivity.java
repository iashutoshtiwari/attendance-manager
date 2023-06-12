package com.ashutosh.ams;

import android.app.DatePickerDialog;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowAttendanceActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    AdminDBHelper db;
    private RecyclerView recyclerView;
    private ArrayList<Student_Get_Set> user_data;
    ShowStudentAdapter showStudentAdapter;
    Button calendar,ok;
    private int myear,mmonth,mdate;
    EditText select_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_attendance);
        db=new AdminDBHelper(this);
        user_data = new ArrayList<>();
        showStudentAdapter=new ShowStudentAdapter(ShowAttendanceActivity.this,user_data);
        recyclerView = findViewById(R.id.recycler_show_attendance);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(showStudentAdapter);

        calendar=findViewById(R.id.select_view_date_calendar);
        select_date=findViewById(R.id.select_view_date);
        ok=findViewById(R.id.select_ok);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mdate = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShowAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        select_date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, myear, mmonth, mdate);datePickerDialog.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_date=select_date.getText().toString();

                Cursor cursor = db.showattendance(s_date);
                user_data.clear();

                while(cursor.moveToNext())
                {
                    String uid=cursor.getString(cursor.getColumnIndex("student_id"));
                    uid=uid.toUpperCase();
                    String dept=cursor.getString(cursor.getColumnIndex("session_dept"));
                    String st_class=cursor.getString(cursor.getColumnIndex("session_class"));
                    String subject=cursor.getString(cursor.getColumnIndex("session_subject"));
                    String date=cursor.getString(cursor.getColumnIndex("session_date"));
                    String faculty_id=cursor.getString(cursor.getColumnIndex("session_faculty_id"));

                    user_data.add(new Student_Get_Set(dept,st_class, uid,faculty_id, subject, date) );
                }
                showStudentAdapter.notifyDataSetChanged();
            }
        });

    }
}
