package com.ashutosh.ams;

import android.content.ContentValues;
import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AdminSignupActivity extends AppCompatActivity {

    EditText fname,lname,e_mail,phone,u_id,repass;
    TextInputEditText pass;

    Button submit;

    TextInputLayout lfname,llname,lemail,lphone,luid,lrepass,lpass;

    private AdminDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        fname = findViewById(R.id.admin_first_name);
        lname = findViewById(R.id.admin_last_name);
        e_mail = findViewById(R.id.admin_email);
        phone = findViewById(R.id.admin_phone);
        u_id = findViewById(R.id.admin_signup_aid);
        pass = findViewById(R.id.admin_password);
        repass = findViewById(R.id.admin_re_password);
        submit = findViewById(R.id.admin_register_btn);
        lpass = findViewById(R.id.layout_admin_password);
        lfname = findViewById(R.id.layout_first_name);
        llname = findViewById(R.id.layout_admin_last_name);
        lemail = findViewById(R.id.layout_admin_email);
        lphone = findViewById(R.id.layout_admin_phone);
        luid = findViewById(R.id.layout_admin_signup_aid);
        lrepass = findViewById(R.id.layout_admin_re_password);
        db=new AdminDBHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first_name=fname.getText().toString();
                final String last_name=lname.getText().toString();
                final String email=e_mail.getText().toString();
                final String ph_no=phone.getText().toString();
                String uid=u_id.getText().toString();
                final String password=pass.getText().toString();
                final String re_pass=repass.getText().toString();
                ContentValues contentValues=new ContentValues();

                uid=uid.toLowerCase();

                if(first_name.isEmpty())
                    lfname.setError("First Name cannot be Empty");
                else {
                    lfname.setError(null);
                    lfname.clearFocus();
                }

                if (last_name.isEmpty())
                    llname.setError("Last Name cannot be Empty");
                else {
                    llname.setError(null);
                    llname.clearFocus();
                }
                if (email.isEmpty())
                    lemail.setError("Email Required");
                else {
                    lemail.setError(null);
                    lemail.clearFocus();
                    if(!validEmail(email))
                        lemail.setError("Enter a valid email");
                    else {
                        lemail.setError(null);
                        lemail.clearFocus();
                    }
                }

                if(ph_no.isEmpty())
                    lphone.setError("Phone Required");
                else {
                    lphone.setError(null);
                    lphone.clearFocus();
                    if(ph_no.length()<10)
                        lphone.setError("Enter a Valid Phone Number");
                    else {
                        lphone.setError(null);
                        lphone.clearFocus();
                    }
                }

                if (uid.isEmpty())
                    luid.setError("UID Required");
                else
                    luid.setError(null);

                if (password.isEmpty())
                    lpass.setError("Password Required");
                else {
                    lpass.setError(null);
                    lpass.clearFocus();
                    if (!re_pass.equals(password)) {
                        lrepass.setError("Passwords don't match");
                    }
                    else {
                        lrepass.setError(null);
                        lrepass.clearFocus();
                    }
                }

                if (!first_name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !ph_no.isEmpty() && !uid.isEmpty() && !password.isEmpty() && re_pass.equals(password) && validEmail(email))
                {
                    contentValues.put("user_fname",first_name);
                    contentValues.put("user_lname",last_name);
                    contentValues.put("user_email",email);
                    contentValues.put("user_phone",ph_no);
                    contentValues.put("user_id",uid);
                    contentValues.put("user_pass",password);
                    db.insert_userData("registration",contentValues);
                    Toast.makeText(getApplicationContext(), "Details Saved. Login  to Continue", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),AdminLoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validEmail(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
