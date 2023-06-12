package com.ashutosh.ams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    SharedPreferences login_data;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView =findViewById(R.id.splash_image);
        textView = findViewById(R.id.splash_text);
        login_data = getSharedPreferences("Login_data", Context.MODE_PRIVATE);

        final String aid=login_data.getString("aid",null);
        final String password = login_data.getString("password",null);

        final Intent i = new Intent(this,StudentFacultyLoginActivity.class);
        Thread timer=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (aid==null & password==null) {
                        startActivity(i);
                        finish();
                    } else {
                        final Intent intent = new Intent(getApplicationContext(),AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
