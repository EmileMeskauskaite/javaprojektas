package com.kursinis.prif4kursinis.webControllers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Cart;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.webControllers.serializers.LocalDateGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ProductGsonSerializer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CartWeb {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHib customHib = new CustomHib(entityManagerFactory);

    //Useris pagal id, GET su PathVariable
    @RequestMapping(value = "/product/getAllCarts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllProducts(){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Cart> cartlist = customHib.getAllRecords(Cart.class);

        return gson.toJson(cartlist);

    }
    //get cart by user
    @RequestMapping(value = "/product/getAllCartsByUser/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllCartsByUser(@PathVariable("id") int id){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Cart> cartlist = customHib.getAllRecords(Cart.class);

        return gson.toJson(cartlist);

    }

    @RequestMapping(value = "/product/getAllCartsByProduct/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllCartsByProduct(@PathVariable("id") int id){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Cart> cartlist = customHib.getAllRecords(Cart.class);

        return gson.toJson(cartlist);

    }
    //delete cart by id
    @RequestMapping(value = "/product/deleteCart/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteCart(@PathVariable(name = "id") int id) {
        System.out.println("Deleting cart with ID: " + id);
        customHib.delete(Cart.class, id);
        return "Cart deleted";
    }
    //create cart
    @RequestMapping(value = "/product/createCart", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createCart(@RequestBody String data) {
        System.out.println("Creating cart");
        Gson parser = new Gson();
        Product product = parser.fromJson(data, Product.class);

        Cart cart = new Cart();
        cart.setItemsInCart((List<Product>) product);

        customHib.create(cart);
        return "Cart created";
    }
//update cart`
    @RequestMapping(value = "/product/updateCart", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateCart(@RequestBody String data) {
        System.out.println("Updating cart");
        Gson parser = new Gson();
        Product product = parser.fromJson(data, Product.class);

        Cart cart = new Cart();
        cart.setItemsInCart((List<Product>) product);

        customHib.update(cart);
        return "Cart updated";
    }

}
