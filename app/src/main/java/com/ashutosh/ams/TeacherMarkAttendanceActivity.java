package com.ashutosh.ams;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;

public class TeacherMarkAttendanceActivity extends AppCompatActivity
{
    EditText select_date,eid;
    Spinner subject,dept,section;
    Button calendar,submit;
    AdminDBHelper db;
    SharedPreferences sharedPreferences;

    String [] sub = {"RDBMS","Java","DM","COA","Aptitude"};
    String [] array_dept = {"CSE","IT"};
    String [] sp_class = {"CSE-1","CSE-2","CSE-3","CSE-4"};


    private int myear,mmonth,mdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark_attendance);
        db = new AdminDBHelper(this);
        select_date = findViewById(R.id.select_mark_date);
        eid = findViewById(R.id.teacher_mark_eid);
        subject = findViewById(R.id.spinner_select_subject);
        dept = findViewById(R.id.spinner_select_dept);
        section = findViewById(R.id.spinner_select_class);
        calendar = findViewById(R.id.open_calendar);
        submit = findViewById(R.id.button_mark_submit);

        sharedPreferences = getSharedPreferences("Login_data", Context.MODE_PRIVATE);
        final String aid=sharedPreferences.getString("eid",null);
        eid.setText(aid);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mdate = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherMarkAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        select_date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, myear, mmonth, mdate);datePickerDialog.show();
            }
        });

        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sub);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(a1);

        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,array_dept);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(a2);

        ArrayAdapter a3 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sp_class);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(a3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ContentValues contentValues=new ContentValues();
                contentValues.put("session_faculty_id",eid.getText().toString());
                contentValues.put("session_dept",dept.getSelectedItem().toString());
                contentValues.put("session_class",section.getSelectedItem().toString());
                contentValues.put("session_subject",subject.getSelectedItem().toString());
                contentValues.put("session_date",select_date.getText().toString());
                db.insert_userData("attendance_sessiontable",contentValues);

                sharedPreferences = getSharedPreferences("Student_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("dept",dept.getSelectedItem().toString());
                editor.putString("class",section.getSelectedItem().toString());
                editor.putString("date",select_date.getText().toString());
                editor.commit();

                Toast.makeText(TeacherMarkAttendanceActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MarkStudentActivity.class);
                startActivity(i);
                finish();
            }
        });



    }
}
