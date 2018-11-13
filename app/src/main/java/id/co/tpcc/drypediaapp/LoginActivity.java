package id.co.tpcc.drypediaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import id.co.tpcc.drypediaapp.api.ApiClient;
import id.co.tpcc.drypediaapp.api.ApiInterface;
import id.co.tpcc.drypediaapp.model.LoginResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername, mPassword;
    private BootstrapButton btnLogin, btnRegistrasi;
    private ApiInterface api;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        btnLogin = (BootstrapButton) findViewById(R.id.btn_login);
        btnRegistrasi = (BootstrapButton) findViewById(R.id.btn_registrasi);
        api = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String isLogin = settings.getString("pref_islogin", "");
        if (!isLogin.equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if (username.equals("")) {
                    mUsername.setError("username harus diisi");
                    return;
                }
                if (mPassword.equals("")) {
                    mUsername.setError("password harus diisi");
                    return;
                }
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        Call<LoginResult> call = api.login(username, password);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("loading...");
        mProgressDialog.show();
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult>
                    response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                LoginResult result = response.body();
                if (response.isSuccessful()) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("pref_token", result.getToken());
                    editor.putString("pref_islogin", "1");
                    editor.putString("pref_id", result.getId());
                    editor.putString("pref_email", result.getEmail());
                    editor.putString("pref_username", mUsername.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "cek username atau password anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }
}