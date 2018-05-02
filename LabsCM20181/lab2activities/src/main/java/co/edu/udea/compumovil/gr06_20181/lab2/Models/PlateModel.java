package co.edu.udea.compumovil.gr06_20181.lab2.Models;

public class PlateModel {
    private byte[] photo;
    private String name;
    private String timetable;
    private String kind;
    private String preparationTime;
    private String ingredients;
    private double price;

    public PlateModel(byte[] photo, String name, String timetable, String kind,
                       String preparationTime, String ingredients, double price) {
        this.photo = photo;
        this.name = name;
        this.timetable = timetable;
        this.kind = kind;
        this.preparationTime = preparationTime;
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

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
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
