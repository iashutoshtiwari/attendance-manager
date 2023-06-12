package com.ashutosh.ams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    TextInputEditText u_id;
    TextInputEditText pass;
    Button login,signup;
    TextInputLayout login_uid, login_password;
    AdminDBHelper db;
    CheckBox rememberme;
    SharedPreferences login_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        db =new AdminDBHelper(this);
        u_id=findViewById(R.id.admin_login_aid);
        pass=findViewById(R.id.admin_login_password);
        login=findViewById(R.id.admin_login);
        signup=findViewById(R.id.admin_signup);
        login_uid = findViewById(R.id.layout_admin_login_aid);
        login_password = findViewById(R.id.layout_admin_login_password);
        rememberme = findViewById(R.id.remember_me);

        signup.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminLoginActivity.this);
            View view = getLayoutInflater().inflate(R.layout.layout_alert_admin_signup,null);
            final TextInputEditText editText = view.findViewById(R.id.master_password);
            Button button = view.findViewById(R.id.master_password_submit);
            builder.setTitle("Enter Master Password");
            builder.setCancelable(true);
            builder.setView(view);
            final AlertDialog dialog=builder.create();
            dialog.show();
            button.setOnClickListener(v1 -> {
                String password = editText.getText().toString();
                if (password.equals("Ashu123")) {
                    dialog.dismiss();
                Intent intent = new Intent(AdminLoginActivity.this, AdminSignupActivity.class);
                startActivity(intent);
            } else {
            Toast.makeText(AdminLoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
            });
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_id = u_id.getText().toString();
                String login_pass = pass.getText().toString();

                login_id=login_id.toLowerCase();

                if(login_id.isEmpty())
                    login_uid.setError("Enter a valid Admin-ID");
                else
                    login_uid.setError(null);

                if (login_pass.isEmpty())
                    login_password.setError("Enter a Password");
                else
                    login_password.setError(null);

                if (!login_id.isEmpty() && !login_pass.isEmpty())
                {
                    Cursor cursor = db.check_login("registration", login_id, login_pass);
                        if (cursor != null)
                        {
                            if (cursor.getCount() > 0)
                            {
                                cursor.moveToNext();
                                if (rememberme.isChecked()) {
                                    login_data = getSharedPreferences("Login_data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = login_data.edit();
                                    editor.putString("aid", login_id);
                                    editor.putString("password", login_pass);
                                    editor.commit();
                                }
                                Toast.makeText(AdminLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                startActivity(i);

                            }
                            else
                                {
                                Toast.makeText(getApplicationContext(), "User Data doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
        });
    }
}