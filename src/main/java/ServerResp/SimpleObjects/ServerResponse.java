package ServerResp.SimpleObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public abstract class ServerResponse implements Serializable {
    @SerializedName("id")
    public int id;

    protected final String KEY = "3a8e79e75eef3edc965de9344d41dd01";


    public abstract void fetchDataFromApi(int id);

    ;
}
