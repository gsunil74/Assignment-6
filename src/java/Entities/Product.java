/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author C0648301
 */
public class Product {

    private int productId;
    private String name;
    private String description;
    private int quantity;

    public Product() {
    }

    public Product(int ProductID, String Name, String Description, int Quantity) {
        this.productId = ProductID;
        this.name = Name;
        this.description = Description;
        this.quantity = Quantity;
    }

    public Product(JsonObject json) {
        productId = json.getInt("productId");
        name = json.getString("name");
        description = json.getString("description");
        quantity = json.getInt("quantity");

    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("productId", productId)
                .add("name", name)
                .add("description", description)
                .add("quantity", quantity)
                .build();

    }

    public int getProductID() {
        return productId;
    }

    public void setProductID(int ProductID) {
        this.productId = ProductID;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int Quantity) {
        this.quantity = Quantity;
    }

}
