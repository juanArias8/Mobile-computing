package co.edu.udea.compumovil.gr06_20181.lab3.Models;

public class PlateModel {
    private String name;
    private String kind;
    private String preparationTime;
    private double price;
    private byte[] photo;

    public PlateModel(String name, String kind, String preparationTime, double price, byte[] photo) {
        this.photo = photo;
        this.name = name;
        this.kind = kind;
        this.preparationTime = preparationTime;
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
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

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
