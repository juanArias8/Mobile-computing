package co.edu.udea.compumovil.gr06_20181.lab2.Models;

public class DrinkModel {
    private byte[] photo;
    private String name;
    private String ingredients;
    private double price;

    public DrinkModel(byte[] photo, String name, String ingredients, double price) {
        this.photo = photo;
        this.name = name;
        this.ingredients = ingredients;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
