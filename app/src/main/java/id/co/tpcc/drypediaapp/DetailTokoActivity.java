package id.co.tpcc.drypediaapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import id.co.tpcc.drypediaapp.db.DatabaseHelper;
import id.co.tpcc.drypediaapp.model.ItemResult;
import id.co.tpcc.drypediaapp.model.LoginResult;
import id.co.tpcc.drypediaapp.model.TokoResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoActivity extends AppCompatActivity {
    private TextView txtTokoName, txtTokoLocation;
    private ImageView imgToko;
    private Button btnConfirm;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        setTitle("Detail Toko");
        Intent intent = getIntent();
        final TokoResult tokos = (TokoResult) intent.getSerializableExtra("Toko");
        txtTokoName = (TextView) findViewById(R.id.txt_toko_name);
        txtTokoLocation = (TextView) findViewById(R.id.txt_toko_location);
        imgToko = (ImageView) findViewById(R.id.img_toko);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        txtTokoName.setText(tokos.getNama());
        txtTokoLocation.setText(tokos.getAlamat());
        Glide
                .with(this)
                .load(tokos.getFoto())
                .into(imgToko);
        Glide.with(this)
                .asBitmap()
                .load(tokos.getFoto())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable com.bumptech.glide.request.transition.Transition
                                                        <? super Bitmap> transition) {
                        saveImage(resource, tokos.getId());
                    }

                });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTokoActivity.this);
                builder.setTitle("Konfirmasi");
                builder.setPositiveButton("Konfirmasi",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tokos.setFoto("JPEG_" + tokos.getId() + ".jpg");
                                databaseHelper.addToko(tokos);
                                Toast.makeText(DetailTokoActivity.this,
                                        "pemesanan telah dikonfirmasi", Toast.LENGTH_SHORT).show();//
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        });
                builder.show();
            }
        });
        mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(tokos.getLatitude()),
                                    Double.parseDouble(tokos.getLongitude())))
                            .title(tokos.getAlamat())
                            .snippet(tokos.getNama()));
                    map.animateCamera(CameraUpdateFactory
                            .newLatLngZoom(new LatLng(Double.parseDouble(tokos.getLatitude()),
                                    Double.parseDouble(tokos.getLongitude())), 15));
                    map.getUiSettings().setZoomControlsEnabled(true);
                    map.setMinZoomPreference(11);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImage(Bitmap image, String fileName) {
        String savedImagePath = null;
        String imageFileName = "JPEG_" + fileName + ".jpg";
        File storageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            galleryAddPic(savedImagePath);
            //Toast.makeText(DetailEventActivity.this, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}
