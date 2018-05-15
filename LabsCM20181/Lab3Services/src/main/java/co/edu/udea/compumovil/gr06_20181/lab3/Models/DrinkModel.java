package co.edu.udea.compumovil.gr06_20181.lab3.Models;

public class DrinkModel {
    private String name;
    private double price;
    private byte[] photo;

    public DrinkModel(String name, double price, byte[] photo) {
        this.name = name;
        this.price = price;
        this.photo = photo;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
