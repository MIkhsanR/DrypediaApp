package id.co.tpcc.drypediaapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import id.co.tpcc.drypediaapp.adapter.ItemAdapter;
import id.co.tpcc.drypediaapp.api.ApiClient;
import id.co.tpcc.drypediaapp.api.ApiInterface;
import id.co.tpcc.drypediaapp.model.ItemResult;
import id.co.tpcc.drypediaapp.model.Toko;
import id.co.tpcc.drypediaapp.model.TokoResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemTokoActivity extends AppCompatActivity {
    private RecyclerView mListItem;
    private List<ItemResult> items;
    private ItemAdapter adapter;
    private ApiInterface api;
    private ProgressDialog mProgressDialog;

    public ItemTokoActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_toko);
        setTitle("Item Laundry");
        Intent intent = getIntent();
        TokoResult toko = (TokoResult) intent.getSerializableExtra("toko");
        mListItem = (RecyclerView) findViewById(R.id.list_toko);
        api = ApiClient.getClient().create(ApiInterface.class);
        getData(toko);
    }

    private void getData(TokoResult toko) {
        Call<List<ItemResult>> call = api.getItems(toko.getId());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("loading...");
        mProgressDialog.show();
        call.enqueue(new Callback<List<ItemResult>>() {
            @Override
            public void onResponse(Call<List<ItemResult>> call, Response<List<ItemResult>> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                items = response.body();
                if (items != null) {
                    adapter = new ItemAdapter(ItemTokoActivity.this, items);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ItemTokoActivity.this);
                    mListItem.setLayoutManager(layoutManager);
                    mListItem.setAdapter(adapter);
                } else {
                    Toast.makeText(ItemTokoActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemResult>> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(ItemTokoActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }
}
