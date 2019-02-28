package teamup.rivile.com.teamup.Uitls.APIModels;

public class Location {
    private int Id ;
    private int StateId ;
    private String Lon ;
    private String Lat ;

    public Location(int id, int stateId, String lon, String lat) {
        Id = id;
        StateId = stateId;
        Lon = lon;
        Lat = lat;
    }

    public Location() {
    }

    public Location(int stateId, String lon, String lat) {
        StateId = stateId;
        Lon = lon;
        Lat = lat;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }
}
