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

public class TeacherPanel extends AppCompatActivity {

    EditText teacher_fname, teacher_lname, teacher_phone, teacher_uid, teacher_repass, teacher_address;
    TextInputEditText teacher_pass;

    Button teacher_submit;

    TextInputLayout teacher_lfname, teacher_llname, teacher_lphone, teacher_luid, teacher_lrepass, teacher_lpass;

    private AdminDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_panel);

        teacher_fname = findViewById(R.id.teacher_first_name);
        teacher_lname = findViewById(R.id.teacher_last_name);
        teacher_phone = findViewById(R.id.teacher_phone);
        teacher_uid = findViewById(R.id.teacher_signup_uid);
        teacher_pass = findViewById(R.id.teacher_password);
        teacher_repass = findViewById(R.id.teacher_re_password);
        teacher_submit = findViewById(R.id.admin_teacher_btn);
        teacher_lpass = findViewById(R.id.layout_teacher_password);
        teacher_lfname = findViewById(R.id.layout_teacher_first_name);
        teacher_llname = findViewById(R.id.layout_teacher_last_name);
        teacher_lphone = findViewById(R.id.layout_teacher_phone);
        teacher_luid = findViewById(R.id.layout_teacher_signup_uid);
        teacher_lrepass = findViewById(R.id.layout_teacher_re_password);
        teacher_address = findViewById(R.id.teacher_address);
        db = new AdminDBHelper(this);

        teacher_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String first_name = teacher_fname.getText().toString();
                String last_name = teacher_lname.getText().toString();
                String ph_no = teacher_phone.getText().toString();
                String uid = teacher_uid.getText().toString();
                String password = teacher_pass.getText().toString();
                String re_pass = teacher_repass.getText().toString();
                String address = teacher_address.getText().toString();
                ContentValues contentValues = new ContentValues();

                uid = uid.toLowerCase();

                if (first_name.isEmpty()) {
                    teacher_lfname.setError("First Name cannot be Empty");
                }
                else
                    {
                    teacher_lfname.setError(null);
                    teacher_lfname.clearFocus();
                }

                if (last_name.isEmpty()) {
                    teacher_llname.setError("Last Name cannot be Empty");
                }
                else {
                    teacher_llname.setError(null);
                    teacher_llname.clearFocus();
                }

                if (address.isEmpty()) {
                    teacher_address.setError("Address Required");
                }
                else {
                    teacher_address.setError(null);
                }

                if (ph_no.isEmpty()) {
                    teacher_lphone.setError("Phone Required");
                }
                else {
                    teacher_lphone.setError(null);
                    teacher_lphone.clearFocus();
                }
                    if (ph_no.length() < 10)
                        teacher_lphone.setError("Enter a Valid Phone Number");
                    else {
                        teacher_lphone.setError(null);
                        teacher_lphone.clearFocus();
                    }

                if (uid.isEmpty())
                    teacher_luid.setError("UID Required");
                else
                    teacher_luid.setError(null);

                if (password.isEmpty())
                    teacher_lpass.setError("Password Required");
                else {
                    teacher_lpass.setError(null);
                    teacher_lpass.clearFocus();
                    if (!re_pass.equals(password)) {
                        teacher_lrepass.setError("Passwords don't match");
                    } else {
                        teacher_lrepass.setError(null);
                        teacher_lrepass.clearFocus();
                    }
                }

                if (!first_name.isEmpty() && !last_name.isEmpty() && !ph_no.isEmpty() && !uid.isEmpty() && !password.isEmpty() && re_pass.equals(password) && !address.isEmpty()) {
                    contentValues.put("user_fname", first_name);
                    contentValues.put("user_lname", last_name);
                    contentValues.put("user_phone", ph_no);
                    contentValues.put("user_id", uid);
                    contentValues.put("user_pass", password);
                    contentValues.put("user_address", address);
                    db.insert_userData("teacher_reg", contentValues);
                    Toast.makeText(getApplicationContext(), "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}