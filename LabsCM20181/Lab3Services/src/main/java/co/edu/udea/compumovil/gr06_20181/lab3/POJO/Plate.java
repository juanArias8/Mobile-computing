package co.edu.udea.compumovil.gr06_20181.lab3.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plate {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("preparation_time")
    @Expose
    private String preparationTime;
    @SerializedName("photo")
    @Expose
    private String photo;

    public Plate(String name, String kind, String price, String preparationTime, String photo) {
        this.name = name;
        this.kind = kind;
        this.price = price;
        this.preparationTime = preparationTime;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}