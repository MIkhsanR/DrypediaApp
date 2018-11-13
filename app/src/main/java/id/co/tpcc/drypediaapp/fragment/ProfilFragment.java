package id.co.tpcc.drypediaapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.tpcc.drypediaapp.EditProfilActivity;
import id.co.tpcc.drypediaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    private Button btnProfil;
    private TextView username, name, email, nohandphone;
    private ImageView imgProfile;
    SharedPreferences mSettings;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        btnProfil = (Button) view.findViewById(R.id.btn_change_profile);
        imgProfile = (ImageView) view.findViewById(R.id.img_profile);
        username = (TextView) view.findViewById(R.id.txt_username);
        name = (TextView) view.findViewById(R.id.txt_name);
        email = (TextView) view.findViewById(R.id.txt_email);
        nohandphone = (TextView) view.findViewById(R.id.txt_no_hp);
        mSettings = getActivity().getSharedPreferences("AppDrypedia_Settings", Context.MODE_PRIVATE);
        username.setText(mSettings.getString("username", "-"));
        name.setText(mSettings.getString("firstName", "-") + " " + mSettings.getString("lastName", "-"));
        email.setText(mSettings.getString("email", "-"));
        nohandphone.setText(mSettings.getString("no handphone", "-"));
        if (mSettings.getString("imagePath", null) != null) {
            imgProfile.setImageURI(Uri.parse(mSettings.getString("imagePath", "-")));
        }
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfilActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
