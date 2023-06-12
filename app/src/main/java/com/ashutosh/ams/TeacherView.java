package com.ashutosh.ams;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherView extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView.LayoutManager layoutManager;
    AdminDBHelper db;
    private RecyclerView recyclerView;
    private ArrayList<Teacher_Get_Set> user_data;
    TeacherAdapter teacherAdapter;

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
        setContentView(R.layout.teacher_view);
        db= new AdminDBHelper(this);
        user_data = new ArrayList<>();

        teacherAdapter = new TeacherAdapter(TeacherView.this,user_data);
        recyclerView = findViewById(R.id.recycler_data);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(teacherAdapter);

        final Cursor cursor = db.get_userdata("teacher_reg");
        user_data.clear();

        while(cursor.moveToNext())
        {
            String fname=cursor.getString(cursor.getColumnIndex("user_fname"));
            String lname=cursor.getString(cursor.getColumnIndex("user_lname"));
            String address=cursor.getString(cursor.getColumnIndex("user_address"));
            String phone=cursor.getString(cursor.getColumnIndex("user_phone"));
            String uid=cursor.getString(cursor.getColumnIndex("user_id"));
            String pass=cursor.getString(cursor.getColumnIndex("user_pass"));
            int id = cursor.getInt(cursor.getColumnIndex("res_id"));

            user_data.add(new Teacher_Get_Set(fname,lname,address,phone,uid,pass,id));
        }

        teacherAdapter.notifyDataSetChanged();
        teacherAdapter.SetOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.delete_data){
                    db.DataDelete("teacher_reg",user_data.get(position).getAdd_id());
                    System.out.println("========="+user_data.get(position).getAdd_id());
                    user_data.remove(position);
                    teacherAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Teacher_Get_Set>newList=new ArrayList<>();
        for (Teacher_Get_Set cfruit:user_data){
            String name = cfruit.getFname().toLowerCase();
            if (name.contains(newText))
            {
                newList.add(cfruit);
            }
        } teacherAdapter.setfilter(newList);
        return true;
    }
}
