package com.ashutosh.ams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;

public class TeacherHomeActivity extends AppCompatActivity {

    Button show,mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        show=findViewById(R.id.show_attendance);
        mark=findViewById(R.id.mark_attendance);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ShowAttendanceActivity.class);
                startActivity(i);

            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TeacherMarkAttendanceActivity.class);
                startActivity(i);
            }
        });
    }
}
