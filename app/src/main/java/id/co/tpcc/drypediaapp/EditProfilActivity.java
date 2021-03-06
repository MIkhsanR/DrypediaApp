package id.co.tpcc.drypediaapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfilActivity extends AppCompatActivity {
    private ImageView imgProfile;
    private EditText username, firstName, lastName, email, nohandphone;
    private Button btnSave;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;
    private Uri file;
    private String imagePath;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgProfile = (ImageView) findViewById(R.id.img_profile);
        username = (EditText) findViewById(R.id.txt_username);
        firstName = (EditText) findViewById(R.id.txt_firstname);
        lastName = (EditText) findViewById(R.id.txt_lastname);
        email = (EditText) findViewById(R.id.txt_email);
        nohandphone = (EditText) findViewById(R.id.txt_no_hp);
        btnSave = (Button) findViewById(R.id.btn_save);
        mSettings = getSharedPreferences("AppDrypedia_Settings", Context.MODE_PRIVATE);
        username.setText(mSettings.getString("username", ""));
        firstName.setText(mSettings.getString("firstName", ""));
        lastName.setText(mSettings.getString("lastName", ""));
        email.setText(mSettings.getString("email", ""));
        nohandphone.setText(mSettings.getString("no handphone", ""));
        imagePath = mSettings.getString("imagePath", "");
        if (!mSettings.getString("imagePath", "").equals("")) {
            imgProfile.setImageURI(Uri.parse(mSettings.getString("imagePath", "-")));
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("username", username.getText().toString());
                editor.putString("firstName", firstName.getText().toString());
                editor.putString("lastName", lastName.getText().toString());
                editor.putString("email", email.getText().toString());
                editor.putString("no handphone", nohandphone.getText().toString());
                editor.putString("imagePath", imagePath);
                editor.apply();
                Intent intent = new Intent(EditProfilActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseDialog() {
        CharSequence menu[] = new CharSequence[]{"Take From Gallery", "Open Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a Picture");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    gallery();
                } else {
                    takePicture();
                }
            }
        });
        builder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                imgProfile.setImageURI(file);
                imagePath = file.toString();
            }
        } else {
            if (resultCode == RESULT_OK) {
                imgProfile.setImageURI(data.getData());
                imagePath = getRealPathFromURI(this, data.getData());
            }
        }
        Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
    }
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        //Toast.makeText(this,file.toString(),Toast.LENGTH_SHORT).show();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }
    private void gallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("EditProfilActivity", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
