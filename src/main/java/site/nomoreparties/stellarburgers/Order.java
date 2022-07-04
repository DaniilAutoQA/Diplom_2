package site.nomoreparties.stellarburgers;

import java.util.List;

public class Order {

    private List<IngredientsJson> ingredients;
    private String _id;
    private Owner owner;
    private String status;
    private String name;
    private int number;
    private int price;
    private String createdAt;
    private String updatedAt;

    public List<IngredientsJson> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsJson> ingredients) {
        this.ingredients = ingredients;
    }

    public Order() {
    }

    public Order(List<IngredientsJson> ingredients, String _id, Owner owner, String status, String name, int number, int price, String createdAt, String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.owner = owner;
        this.status = status;
        this.name = name;
        this.number = number;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
