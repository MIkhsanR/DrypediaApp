package id.co.tpcc.drypediaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;


import id.co.tpcc.drypediaapp.api.ApiClient;
import id.co.tpcc.drypediaapp.api.ApiInterface;
import id.co.tpcc.drypediaapp.db.DatabaseHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCartActivity extends AppCompatActivity {

    //private TextView txtEventName, textEventDesc,txtEventLocation,txtEventTime;
    //private ImageView imgEvent;
    //private Button btnCheckout,btnUpload;
    //private ApiInterface api;
    //private ProgressDialog mProgressDialog;
    //SharedPreferences prefs;

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //  super.onCreate(savedInstanceState);
    //  setContentView(R.layout.activity_detail_order);
        // Get singleton instance of database
    //  final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
    //  api = ApiClient.getClient().create(ApiInterface.class);
    //    prefs = PreferenceManager.getDefaultSharedPreferences(DetailCartActivity.this);
    //  setTitle("Detail Order");
    //  Intent intent = getIntent();




    //  btnCheckout.setOnClickListener(new View.OnClickListener() {
    //      @Override
    //      public void onClick(View view) {

    //      }
    //  });
    //  btnUpload.setOnClickListener(new View.OnClickListener() {
    //      @Override
    //      public void onClick(View view) {
                //ambil foto
                //upload

    //      }
    //  });
    //}

    //private void checkout(){
    //  Call<String> call = api.checkout("nomor",
    //          "tglbayar" ,
    //          "userid" ,
    //          "idpenjual" ,
    //          "idbarang",1,100,"token");
    //  mProgressDialog = new ProgressDialog(this);
    //  mProgressDialog.setIndeterminate(true);
    //  mProgressDialog.setMessage("loading...");
    //  mProgressDialog.show();
    //  call.enqueue(new Callback<String>() {
    //      @Override
    //      public void onResponse(Call<String> call, Response<String>
    //              response) {
    //          if (mProgressDialog.isShowing()) {
    //              mProgressDialog.dismiss();
    //          }
    //          String result = response.body();
    //          if (response.isSuccessful()) {
                    //update notaid
    //          } else {
    //              Toast.makeText(DetailCartActivity.this, "cek username atau password anda", Toast.LENGTH_SHORT).show();
    //          }
    //      }

    //      @Override
    //      public void onFailure(Call<String> call, Throwable t) {
    //          Log.e("Retrofit Get", t.toString());
    //          Toast.makeText(DetailCartActivity.this,t.toString(),Toast.LENGTH_LONG).show();
    //          if (mProgressDialog.isShowing()) {
    //              mProgressDialog.dismiss();
    //          }
    //      }
    //  });
    //}


}

