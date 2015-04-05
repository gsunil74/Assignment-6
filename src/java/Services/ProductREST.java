/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Product;
import Entities.ProductList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author C0648301
 */
@Path("/Product")
@RequestScoped
public class ProductREST {

    @Inject
    ProductList ProductList;

    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(ProductList.toJSON()).build();
    }

    @GET
    @Path("{ID}")
    @Produces("application/json")
    public Response getByID(@PathParam("ID") int ID) {
        return Response.ok(ProductList.get(ID).toJSON()).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        return null;
    }

    @PUT
    @Path("{ID}")
    @Consumes("application/json")
    public Response set(@PathParam("ID") int ID, JsonObject json) {
        try {
            Product p = new Product(json);
            ProductList.set(ID, p);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(500).build();

        }
    }

    @DELETE
    @Path("{ID}")
    public Response delete(@PathParam("ID") int ID) {
        try {
            ProductList.remove(ID);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(500).build();
        }
    }
}
