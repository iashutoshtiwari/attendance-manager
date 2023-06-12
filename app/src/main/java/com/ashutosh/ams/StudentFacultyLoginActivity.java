package com.ashutosh.ams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentFacultyLoginActivity extends AppCompatActivity {

    TextInputEditText uid,password;
    TextInputLayout luid,lpassword;
    Button btn_login;
    TextView admin_login_btn;
    AdminDBHelper db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_faculty_login);
        db =new AdminDBHelper(this);
        uid=findViewById(R.id.login_uid);
        password = findViewById(R.id.login_password);
        luid = findViewById(R.id.layout_login_uid);
        lpassword = findViewById(R.id.layout_login_password);
        admin_login_btn=findViewById(R.id.admin_page);
        btn_login=findViewById(R.id.login);


        admin_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminLoginActivity.class);
                startActivity(i);

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uid.getText().toString();
                String pass = password.getText().toString();
                username=username.toLowerCase();

                if(username.isEmpty())
                    luid.setError("Enter a valid UID/EID");
                else
                    luid.setError(null);

                if (pass.isEmpty())
                    lpassword.setError("Enter a Password");
                else
                    lpassword.setError(null);

                if (!username.isEmpty() && !pass.isEmpty())
                {
                    if (username.charAt(0) == 'e') {
                        Cursor cursor = db.check_login("teacher_reg", username, pass);
                        if (cursor != null)
                        {
                            if (cursor.getCount() > 0)
                            {
                                cursor.moveToNext();
                                Toast.makeText(getApplicationContext(), "Welcome Teacher", Toast.LENGTH_SHORT).show();
                                sharedPreferences = getSharedPreferences("Login_data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("eid", username);
                                editor.commit();
                                Intent i = new Intent(getApplicationContext(), TeacherHomeActivity.class);
                                startActivity(i);
                            }

                            else
                                Toast.makeText(getApplicationContext(), "Teacher data doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Cursor cursor=db.check_login("student_reg",username ,pass);

                        if(cursor!=null)
                        {
                            if(cursor.getCount() > 0) {
                                cursor.moveToNext();
                                Toast.makeText(getApplicationContext(), "Welcome Student", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), StudentHomeActivity.class);
                                startActivity(i);
                            }
                            else

                                Toast.makeText(getApplicationContext(),"Student data doesn't exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

//
            }
        });
    }
}