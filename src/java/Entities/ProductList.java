/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author C0648301
 */
@ApplicationScoped
public class ProductList {

    private List<Product> ProductList = new ArrayList<>();

    public ProductList() {
        try (Connection connects = getConnection()) {
            String query = "SELECT * FROM products";
            PreparedStatement pstmt = connects.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"));
                ProductList.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public JsonArray toJSON() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Product p : ProductList) {
            json.add(p.toJSON());
        }
        return json.build();
    }

    public Product get(int ProductId) {
        Product result = null;
        for (int i = 0; i < ProductList.size() && result == null; i++) {
            Product p = ProductList.get(i);
            if (p.getProductID() == ProductId) {
                result = p;
            }
        }
        return result;
    }

    public void remove(Product p) throws Exception {
        remove(p.getProductID());
    }

    public void remove(int ProductId) throws Exception {
        int result = doUpdate("DELETE FROM products WHERE productId = ?",
                String.valueOf(ProductId));
        if (result > 0) {
            Product original = get(ProductId);
            ProductList.remove(original);

        } else {
            throw new Exception("Delete Failed");
        }

    }

    public void set(int ProductId, Product Product) throws Exception {
        int result = doUpdate("UPDATE products SET name = ?, description = ?, quantity = ? WHERE productId = ?",
                Product.getName(),
                Product.getDescription(),
                String.valueOf(Product.getQuantity()),
                String.valueOf(ProductId)
        );
        if (result == 1) {
            Product original = get(ProductId);
            original.setName(Product.getName());
            original.setDescription(Product.getDescription());
            original.setQuantity(Product.getQuantity());
        } else {
            throw new Exception("Error with Update");
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost/assignment6";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(jdbc, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numChanges;
    }

    public void add(Product p) throws Exception {
        int result = doUpdate(
                "INSERT into products (productId, name, description, quantity) values (?, ?, ?, ?)",
                String.valueOf(p.getProductID()),
                p.getName(),
                p.getDescription(),
                String.valueOf(p.getQuantity()));
        if (result > 0) {
            ProductList.add(p);
        } else {
            throw new Exception("Error Inserting");
        }
    }

}
