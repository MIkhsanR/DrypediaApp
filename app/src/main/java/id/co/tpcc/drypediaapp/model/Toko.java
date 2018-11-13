package id.co.tpcc.drypediaapp.model;

public class Toko {
    private long id;
    private String tokoName;
    private String tokoPlace;

    public Toko() {
    }

    public Toko(long id, String tokoName, String tokoPlace) {
        this.id = id;
        this.tokoName = tokoName;
        this.tokoPlace = tokoPlace;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokoName() {
        return tokoName;
    }

    public void setTokoName(String tokoName) {
        this.tokoName = tokoName;
    }

    public String getTokoPlace() {
        return tokoPlace;
    }

    public void setTokoPlace(String tokoPlace) {
        this.tokoPlace = tokoPlace;
    }
}
