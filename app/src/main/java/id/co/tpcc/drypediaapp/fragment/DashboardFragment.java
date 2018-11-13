package id.co.tpcc.drypediaapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.co.tpcc.drypediaapp.R;
import id.co.tpcc.drypediaapp.adapter.DashboardAdapter;
import id.co.tpcc.drypediaapp.db.DatabaseHelper;
import id.co.tpcc.drypediaapp.model.TokoResult;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private RecyclerView mListDashboard;
    private List<TokoResult> tokos;
    private DashboardAdapter adapter;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getActivity());
        mListDashboard = (RecyclerView) view.findViewById(R.id.list_dashboard);
        List<TokoResult> tokos = databaseHelper.getAllTokos();
        Log.d("Tokos", tokos.size() + "");
        adapter = new DashboardAdapter(getContext(), tokos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListDashboard.setLayoutManager(layoutManager);
        mListDashboard.setAdapter(adapter);
        return view;
    }

}
