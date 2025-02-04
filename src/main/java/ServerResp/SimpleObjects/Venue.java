package ServerResp.SimpleObjects;

import com.google.gson.annotations.SerializedName;

public class Venue extends ServerResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("surface")
    private String surface;

    @SerializedName("image")
    private String image;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public int getCapacity() { return capacity; }
    public String getSurface() { return surface; }
    public String getImage() { return image; }

    @Override
    public void fetchDataFromApi(int id) {
    }
}