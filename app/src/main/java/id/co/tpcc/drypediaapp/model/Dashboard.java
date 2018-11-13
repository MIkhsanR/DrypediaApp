package id.co.tpcc.drypediaapp.model;

public class Dashboard {
    long id;
    String tokoname;
    String notes;

    public Dashboard(long id, String tokoname, String notes) {
        this.id = id;
        this.tokoname = tokoname;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokoname() {
        return tokoname;
    }

    public void setTokoname(String tokoname) {
        this.tokoname = tokoname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
