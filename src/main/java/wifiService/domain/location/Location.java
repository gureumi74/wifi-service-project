package wifiService.domain.location;

public class Location {
    private Integer id;
    private double LAT;
    private double LNT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNT() {
        return LNT;
    }

    public void setLNT(double LNT) {
        this.LNT = LNT;
    }
}
