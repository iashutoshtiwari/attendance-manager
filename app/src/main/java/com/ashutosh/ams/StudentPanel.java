package com.ashutosh.ams;

import android.content.ContentValues;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentPanel extends AppCompatActivity {

    EditText stu_fname,stu_lname,stu_phone,stu_uid,stu_repass,stu_dept,stu_class,stu_address;
    TextInputEditText stu_pass;

    Button stu_submit;

    TextInputLayout stu_lfname,stu_llname,stu_lphone,stu_luid,stu_lrepass,stu_lpass,stu_ldept, stu_lclass;

    private AdminDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        stu_fname = findViewById(R.id.student_first_name);
        stu_lname = findViewById(R.id.student_last_name);
        stu_phone = findViewById(R.id.student_phone);
        stu_uid = findViewById(R.id.student_signup_uid);
        stu_dept = findViewById(R.id.student_dept);
        stu_class = findViewById(R.id.student_class);
        stu_pass = findViewById(R.id.student_password);
        stu_repass = findViewById(R.id.student_re_password);
        stu_submit = findViewById(R.id.admin_student_btn);
        stu_lpass = findViewById(R.id.layout_student_password);
        stu_lfname = findViewById(R.id.layout_student_first_name);
        stu_llname = findViewById(R.id.layout_student_last_name);
        stu_lphone = findViewById(R.id.layout_student_phone);
        stu_luid = findViewById(R.id.layout_student_signup_uid);
        stu_lrepass = findViewById(R.id.layout_student_re_password);
        stu_ldept= findViewById(R.id.layout_student_dept);
        stu_lclass = findViewById(R.id.layout_student_class);
        stu_address = findViewById(R.id.student_address);
        db = new AdminDBHelper(this);

        stu_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first_name=stu_fname.getText().toString();
                final String last_name=stu_lname.getText().toString();
                final String ph_no=stu_phone.getText().toString();
                String uid=stu_uid.getText().toString();
                final String password=stu_pass.getText().toString();
                final String re_pass=stu_repass.getText().toString();
                final String dept=stu_dept.getText().toString();
                final String classroom = stu_class.getText().toString();
                final String address = stu_address.getText().toString();
                ContentValues contentValues=new ContentValues();

                uid=uid.toLowerCase();

                if(first_name.isEmpty())
                    stu_lfname.setError("First Name cannot be Empty");
                else {
                    stu_lfname.setError(null);
                    stu_lfname.clearFocus();
                }

                if (last_name.isEmpty())
                    stu_llname.setError("Last Name cannot be Empty");
                else {
                    stu_llname.setError(null);
                    stu_llname.clearFocus();
                }
                if (dept.isEmpty())
                    stu_ldept.setError("Department Required");
                else {
                    stu_ldept.setError(null);
                    stu_ldept.clearFocus();
                }
                if (address.isEmpty())
                    stu_address.setError("Address Required");
                else {
                    stu_address.setError(null);
                    stu_address.clearFocus();
                }

                if (classroom.isEmpty())
                    stu_lclass.setError("Class Required");
                else {
                    stu_lclass.setError(null);
                    stu_lclass.clearFocus();
                }

                if(ph_no.isEmpty())
                    stu_lphone.setError("Phone Required");
                else {
                    stu_lphone.setError(null);
                    stu_lphone.clearFocus();
                    if(ph_no.length()<10)
                        stu_lphone.setError("Enter a Valid Phone Number");
                    else {
                        stu_lphone.setError(null);
                        stu_lphone.clearFocus();
                    }
                }

                if (uid.isEmpty())
                    stu_luid.setError("UID Required");
                else
                    stu_luid.setError(null);

                if (password.isEmpty())
                    stu_lpass.setError("Password Required");
                else {
                    stu_lpass.setError(null);
                    stu_lpass.clearFocus();
                    if (!re_pass.equals(password)) {
                        stu_lrepass.setError("Passwords don't match");
                    }
                    else {
                        stu_lrepass.setError(null);
                        stu_lrepass.clearFocus();
                    }
                }

                if (!first_name.isEmpty() && !last_name.isEmpty() && !dept.isEmpty() && !classroom.isEmpty() && !ph_no.isEmpty() && !uid.isEmpty() && !password.isEmpty() && re_pass.equals(password) && !address.isEmpty())
                {
                    contentValues.put("user_fname",first_name);
                    contentValues.put("user_lname",last_name);
                    contentValues.put("user_dept",dept);
                    contentValues.put("user_class",classroom);
                    contentValues.put("user_phone",ph_no);
                    contentValues.put("user_id",uid);
                    contentValues.put("user_pass",password);
                    contentValues.put("user_address",address);
                    db.insert_userData("student_reg",contentValues);
                    Toast.makeText(getApplicationContext(), "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
