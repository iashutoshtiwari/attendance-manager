package com.ashutosh.ams;

import android.database.Cursor;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentView extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView.LayoutManager layoutManager;
    AdminDBHelper db;
    private RecyclerView recyclerView;
    private ArrayList<Student_Get_Set> user_data;
    StudentAdapter studentAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search by First Name");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_view);
        db= new AdminDBHelper(this);
        user_data = new ArrayList<>();

        studentAdapter = new StudentAdapter(StudentView.this,user_data);
        recyclerView = findViewById(R.id.recycler_data_student);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentAdapter);

        final Cursor cursor = db.get_userdata("student_reg");
        user_data.clear();

        while(cursor.moveToNext())
        {
            String fname=cursor.getString(cursor.getColumnIndex("user_fname"));
            String lname=cursor.getString(cursor.getColumnIndex("user_lname"));
            String dept=cursor.getString(cursor.getColumnIndex("user_dept"));
            String st_class=cursor.getString(cursor.getColumnIndex("user_class"));
            String address=cursor.getString(cursor.getColumnIndex("user_address"));
            String phone=cursor.getString(cursor.getColumnIndex("user_phone"));
            String uid=cursor.getString(cursor.getColumnIndex("user_id"));
            String pass=cursor.getString(cursor.getColumnIndex("user_pass"));
            int id = cursor.getInt(cursor.getColumnIndex("res_id"));

            user_data.add(new Student_Get_Set(fname,lname,dept,st_class,address,phone,uid,pass,id));
        }

        studentAdapter.notifyDataSetChanged();
        studentAdapter.SetOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.st_delete_data){
                    db.DataDelete("student_reg",user_data.get(position).getAdd_id());
                    System.out.println("========="+user_data.get(position).getAdd_id());

                    user_data.remove(position);
                    studentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(), "Showing "+query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Student_Get_Set>newList=new ArrayList<>();
        for (Student_Get_Set cfruit:user_data){
            String name = cfruit.getFname().toLowerCase();
            if (name.contains(newText))
            {
                newList.add(cfruit);
            }
        } studentAdapter.setfilter(newList);
        return true;
    }
}
