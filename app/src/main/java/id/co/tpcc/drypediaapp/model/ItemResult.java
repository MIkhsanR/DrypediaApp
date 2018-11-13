
package id.co.tpcc.drypediaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_jenis")
    @Expose
    private String idJenis;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("namajenis")
    @Expose
    private String namajenis;
    @SerializedName("harga")
    @Expose
    private String harga;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(String idJenis) {
        this.idJenis = idJenis;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamajenis() {
        return namajenis;
    }

    public void setNamajenis(String namajenis) {
        this.namajenis = namajenis;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

}
