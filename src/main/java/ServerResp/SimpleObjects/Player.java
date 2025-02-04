package ServerResp.SimpleObjects;

import com.google.gson.annotations.SerializedName;
public class Player extends ServerResponse{

    @SerializedName("name")
    String name;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("age")
    int age;

    @SerializedName("birth")
    Player.Birth birth;

    @SerializedName("nationality")
    String nationality;

    @SerializedName("height")
    String height;

    @SerializedName("weight")
    String weight;

    @SerializedName("number")
    int number;

    @SerializedName("position")
    String position;

    @SerializedName("photo")
    String photo;

    @Override
    public void fetchDataFromApi(int id) {

    }


    static class Birth {
        @SerializedName("date")
        String date;

        @SerializedName("place")
        String place;

        @SerializedName("country")
        String country;

        @Override
        public String toString() {
            return "Birth{" +
                    "date='" + date + '\'' +
                    ", place='" + place + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                ", nationality='" + nationality + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", number=" + number +
                ", position='" + position + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public Player.Birth getBirth() {
        return birth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public int getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public String getPhoto() {
        return photo;
    }
}

