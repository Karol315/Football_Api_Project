package ServerResp.SimpleObjects;

import ServerResp.SimpleObjects.ServerResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class League extends ServerResponse {
    @Override
    public void fetchDataFromApi(int id) {

    }


    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("logo")
    private String logo;

    @SerializedName("flag")
    private String flag;

    @SerializedName("season")
    private int season;

    // Gettery
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getLogo() { return logo; }
    public String getFlag() { return flag; }
    public int getSeason() { return season; }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", logo='" + logo + '\'' +
                ", flag='" + flag + '\'' +
                ", season=" + season +
                '}';
    }
}
