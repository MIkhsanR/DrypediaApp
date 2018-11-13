package id.co.tpcc.drypediaapp.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import id.co.tpcc.drypediaapp.R;
import id.co.tpcc.drypediaapp.adapter.TokoAdapter;
import id.co.tpcc.drypediaapp.api.ApiClient;
import id.co.tpcc.drypediaapp.api.ApiInterface;
import id.co.tpcc.drypediaapp.model.Toko;
import id.co.tpcc.drypediaapp.model.TokoResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mListToko;
    private List<TokoResult> tokos;
    private TokoAdapter adapter;
    private ApiInterface api;
    private ProgressDialog mProgressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mListToko = (RecyclerView) view.findViewById(R.id.list_toko);
        api = ApiClient.getClient().create(ApiInterface.class);
        getData();
        return view;
    }

    private void getData() {
        Call<List<TokoResult>> call = api.getTokos();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("loading...");
        mProgressDialog.show();
        call.enqueue(new Callback<List<TokoResult>>() {
            @Override
            public void onResponse(Call<List<TokoResult>> call, Response<List<TokoResult>> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                tokos = response.body();
                if (tokos != null) {
                    adapter = new TokoAdapter(getContext(), tokos);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mListToko.setLayoutManager(layoutManager);
                    mListToko.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TokoResult>> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

}
