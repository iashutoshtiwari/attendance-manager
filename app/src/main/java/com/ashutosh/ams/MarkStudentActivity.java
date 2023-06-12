package com.ashutosh.ams;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkStudentActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    AdminDBHelper db;
    private RecyclerView recyclerView;
    private ArrayList<Student_Get_Set> studentlist;
    SharedPreferences sharedPreferences,sp;

    String dept,cl,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_student);
        sharedPreferences = getSharedPreferences("Student_data", Context.MODE_PRIVATE);
        dept = sharedPreferences.getString("dept", null);
        cl = sharedPreferences.getString("class", null);
        date = sharedPreferences.getString("date",null);

        db = new AdminDBHelper(this);
        studentlist = new ArrayList<>();
        final StudentMarkAdapter mAdapter = new StudentMarkAdapter(MarkStudentActivity.this, studentlist);
        recyclerView = findViewById(R.id.recycler_mark_student);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        final Cursor cursor = db.getstudentdata(cl, dept);
        studentlist.clear();
        sp=getSharedPreferences("Login_data",Context.MODE_PRIVATE);
        final String eid=sp.getString("eid",null);
        while (cursor.moveToNext()) {
            String fname = cursor.getString(cursor.getColumnIndex("user_fname"));
            String lname = cursor.getString(cursor.getColumnIndex("user_lname"));
            String dept = cursor.getString(cursor.getColumnIndex("user_dept"));
            String cl = cursor.getString(cursor.getColumnIndex("user_class"));
            String uid = cursor.getString(cursor.getColumnIndex("user_id"));

            studentlist.add(new Student_Get_Set(fname, lname, dept, cl, uid,false));

        }

        mAdapter.notifyDataSetChanged();

        System.out.println("=========="+cursor.getCount());
        mAdapter.SetOnItemClickListener(new StudentMarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId()==R.id.stu_mark_present)
                {
                    try{
                        studentlist.get(position).setCheck(!studentlist.get(position).isCheck());
                        if (db.isPresent(date,studentlist.get(position).getEid())){
                            db.delete(date,studentlist.get(position).getEid());
                        }
                        else
                            {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("session_date",date);
                            contentValues.put("session_faculty_id",eid);
                            contentValues.put("student_id",studentlist.get(position).getEid());

                            db.insert_userData("attendance_table",contentValues);
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mark_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == R.id.mark_submit)
        {
            Toast.makeText(getApplicationContext(), "Submit Successfully", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
