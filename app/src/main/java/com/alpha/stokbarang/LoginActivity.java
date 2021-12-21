package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnLogin;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    ConnectivityManager conMgr;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();

        sharedPrefManager = new SharedPrefManager(this);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initComponents(){
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        etPassword = (TextInputEditText ) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Loading
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

                //Prosedur untuk login
                requestLogin();
            }
        });
    }

    private void requestLogin(){

        //Mengambil data dari EditText
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Masukan Email Anda");
            etEmail.requestFocus();
            loading.dismiss();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Masukan Password Anda");
            etPassword.requestFocus();
            loading.dismiss();
            return;
        }

        mApiService.loginRequest(email,password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("respon", "Login Response: " + response.toString());
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Log.d("status", jsonRESULTS.getString("success"));
                                if (jsonRESULTS.getString("success").equals("true")){

                                    // Jika login berhasil maka data nama yang ada di response API\
                                    String massage = jsonRESULTS.getString("message");

                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mContext, massage, Toast.LENGTH_SHORT).show();
                                    String nip = jsonRESULTS.getJSONObject("data").getString("nip");
                                    String nama = jsonRESULTS.getJSONObject("data").getString("nm_pegawai");
                                    String email = jsonRESULTS.getJSONObject("data").getString("email");
                                    String nohp = jsonRESULTS.getJSONObject("data").getString("no_hp");
                                    String posisi = jsonRESULTS.getJSONObject("data").getString("posisi");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIP, nip);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NOHP, nohp);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_POSISI, posisi);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    finish();

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}