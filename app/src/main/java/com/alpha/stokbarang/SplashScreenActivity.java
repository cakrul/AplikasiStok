package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alpha.stokbarang.utils.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    private int waktu_loading=2000;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPrefManager = new SharedPrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                navigateToHomeScreen();

            }
        },waktu_loading);
    }

    void  navigateToHomeScreen(){
        if(sharedPrefManager.getSPSudahLogin()){
            // Navigate to Main Activity
            Intent home=new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(home);
        }else{
            // Navigate to login Activity
            Intent login=new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(login);
        }
        finish();
    }
}