package id.co.tpcc.drypediaapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.co.tpcc.drypediaapp.model.TokoResult;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    // Database Info
    private static final String DATABASE_NAME = "tokoDb";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TOKOS = "tokos";

    // Post Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_TOKO_ID = "tokoid";
    private static final String KEY_TOKO_NAME = "tokoname";
    private static final String KEY_TOKO_ALAMAT = "tokoalamat";
    private static final String KEY_TOKO_NAMA_KECAMATAN = "tokonamakecamatan";
    private static final String KEY_TOKO_ID_KECAMATAN = "tokoidkecamatan";
    private static final String KEY_TOKO_LATITUDE = "tokolatitude";
    private static final String KEY_TOKO_LONGITUDE = "tokolongitude";
    private static final String KEY_TOKO_TELPON = "tokotelpon";
    private static final String KEY_TOKO_FOTO = "tokofoto";

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TOKOS_TABLE = "CREATE TABLE " + TABLE_TOKOS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TOKO_ID + " TEXT," +
                KEY_TOKO_NAME + " TEXT," +
                KEY_TOKO_ALAMAT + " TEXT," +
                KEY_TOKO_NAMA_KECAMATAN + " TEXT," +
                KEY_TOKO_ID_KECAMATAN + " TEXT," +
                KEY_TOKO_LATITUDE + " TEXT," +
                KEY_TOKO_LONGITUDE + " TEXT," +
                KEY_TOKO_TELPON + " TEXT," +
                KEY_TOKO_FOTO + " TEXT" +
                ")";

        db.execSQL(CREATE_TOKOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOKOS);
            onCreate(db);
        }
    }

    public void addToko(TokoResult toko) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TOKO_ID, toko.getId());
            values.put(KEY_TOKO_NAME, toko.getNama());
            values.put(KEY_TOKO_ALAMAT, toko.getAlamat());
            values.put(KEY_TOKO_NAMA_KECAMATAN, toko.getNamakecamatan());
            values.put(KEY_TOKO_ID_KECAMATAN, toko.getIdKecamatan());
            values.put(KEY_TOKO_LATITUDE, toko.getLatitude());
            values.put(KEY_TOKO_LONGITUDE, toko.getLongitude());
            values.put(KEY_TOKO_TELPON, toko.getTelpon());
            values.put(KEY_TOKO_FOTO, toko.getFoto());
            db.delete(TABLE_TOKOS, KEY_TOKO_ID + " = ?", new String[]{toko.getId()});
            db.insertOrThrow(TABLE_TOKOS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<TokoResult> getAllTokos() {
        List<TokoResult> tokos = new ArrayList<>();
        String TOKOS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_TOKOS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TOKOS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TokoResult newToko = new TokoResult();
                    newToko.setId(cursor.getString(cursor.getColumnIndex(KEY_TOKO_ID)));
                    newToko.setNama(cursor.getString(cursor.getColumnIndex(KEY_TOKO_NAME)));
                    newToko.setAlamat(cursor.getString(cursor.getColumnIndex(KEY_TOKO_ALAMAT)));
                    newToko.setNamakecamatan(cursor.getString(cursor.getColumnIndex(KEY_TOKO_NAMA_KECAMATAN)));
                    newToko.setIdKecamatan(cursor.getString(cursor.getColumnIndex(KEY_TOKO_ID_KECAMATAN)));
                    newToko.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_TOKO_LATITUDE)));
                    newToko.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_TOKO_LONGITUDE)));
                    newToko.setTelpon(cursor.getString(cursor.getColumnIndex(KEY_TOKO_TELPON)));
                    newToko.setTelpon(cursor.getString(cursor.getColumnIndex(KEY_TOKO_FOTO)));
                    tokos.add(newToko);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return tokos;
    }

}