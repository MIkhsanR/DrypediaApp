package id.co.tpcc.drypediaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.lang.reflect.Method;

import id.co.tpcc.drypediaapp.api.ApiClient;
import id.co.tpcc.drypediaapp.api.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiActivity extends AppCompatActivity {
    private EditText mPhone, mEmail, mPassword;
    private BootstrapButton btnDaftar;
    private SharedPreferences mSettings;
    private ApiInterface api;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        api = ApiClient.getClient().create(ApiInterface.class);
        mPhone = (EditText) findViewById(R.id.no_phone);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        btnDaftar = (BootstrapButton) findViewById(R.id.btn_daftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.getText().toString().equals("")) {
                    mEmail.setError("email harus diisi");
                    return;
                }
                if (mPhone.getText().toString().equals("")) {
                    mPhone.setError("nomor telepon harus diisi");
                    return;
                }
                if (mPassword.getText().toString().equals("")) {
                    mPassword.setError("password harus diisi");
                    return;
                }
                register(mEmail.getText().toString(), mPhone.getText().toString(), mPassword.getText().toString());
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExpossure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void register(String email, String noTelpon, String password) {
        Call<String> call = api.registration(email, noTelpon, password);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("loading...");
        mProgressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String>
                    response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                String result = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(RegistrasiActivity.this, result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrasiActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(RegistrasiActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }
}

